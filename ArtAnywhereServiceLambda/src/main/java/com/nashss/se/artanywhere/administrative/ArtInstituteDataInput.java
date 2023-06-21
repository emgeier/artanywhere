package com.nashss.se.artanywhere.administrative;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import com.google.gson.Gson;

import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.DynamoDbClientProvider;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class ArtInstituteDataInput {

    private final DynamoDbClientProvider dynamoDbClientProvider;
    @Inject
    public ArtInstituteDataInput(DynamoDbClientProvider dynamoDbClientProvider) {

        this.dynamoDbClientProvider = dynamoDbClientProvider;
    }

    public static void main(String[] args) throws IOException {
        String internetAddress = String.format("https://api.artic.edu/api/v1/exhibitions?fields=title,short_description,image_url,aic_start_at,aic_end_at,artwork_titles,artist_ids&page=6");

        String tableName = "exhibitions"; // Replace with your DynamoDB table name

        Logger log = LogManager.getLogger();

        Gson gson = new Gson();
        // Make API request and get the JSON string
        String jsonString = makeApiRequest(internetAddress);

        // Deserialize the JSON string into an array of objects
        AIExhibition[] objects = gson.fromJson(jsonString, AIExhibition[].class);

        for (AIExhibition object : objects) {
            if (object.getTitle() == null || object.getTitle().isEmpty()){continue;}

            // Convert the object to DynamoDB JSON format
            Map<String, AttributeValue> dynamoDbJson = new HashMap<>();

            dynamoDbJson.put("cityCountry", new AttributeValue().withS("Chicago, USA"));
            dynamoDbJson.put("exhibitionName", new AttributeValue().withS(object.getTitle()));
            dynamoDbJson.put("institution", new AttributeValue().withS("Art Institute of Chicago"));
            dynamoDbJson.put("address", new AttributeValue().withS("111 S Michigan Ave, Chicago, IL 60603"));
            List<String> movements = new ArrayList<>();
            //Description null check and data cleanup
            if(object.getDescription() != null ) {
                String description = object.getDescription().replace("<p>", "")
                            .replace("</p>", "");
                dynamoDbJson.put("description", new AttributeValue().withS(description));
                //Media search, put into attribute values list

                Set<String>media = findMedia(object.getTitle());
                media.addAll(findMedia(description));
                if (!media.isEmpty()) {
                    dynamoDbJson.put("media", new AttributeValue().withL(convertToAttributeValueList(media)));
                }
                //Movement search, put into attribute values list
                    //if in the title, it should be the primary one
                movements = findMovements(object.getTitle());
                movements.addAll(findMovements(description));
                if(!movements.isEmpty()) {
                    String movement = movements.get(0);
                    dynamoDbJson.put("movement", new AttributeValue().withS(movement));
                }
            }
            //Dates converted and loop discontinued if the exhibition dates aren't current.
            DateConverter dateConverter = new DateConverter();
            String startDate = object.getStartDate();
            if (startDate != null) {
                int timeIndex = startDate.indexOf('T');
                startDate = startDate.substring(0, timeIndex);
                dynamoDbJson.put("startDate", new AttributeValue().withS(startDate));
            }
            String endDate = object.getEndDate();

            if(endDate != null) {
                int timeIndexEnd = endDate.indexOf('T');
                endDate = endDate.substring(0, timeIndexEnd);
                //Only add current or up-and-coming events, no historical data.
                if (dateConverter.unconvert(endDate).isBefore(LocalDate.now())) {
                    continue;
                }

                dynamoDbJson.put("endDate", new AttributeValue().withS(endDate));
            }
                //Artists ids from AI are used to retrieve the artists' names to add to attributes list.
            if(object.getArtists() != null) {
                Set<String> artistNames = findArtists(object.getArtists(), object.getTitle(),
                        movements, object.getImageUrl() );

                dynamoDbJson.put("artists", new AttributeValue().withL(convertToAttributeValueList(artistNames)));
            }
            List<String> art = object.getArt();
            if (art !=null && !art.isEmpty()) {
                List<AttributeValue> artAttributes = new ArrayList<>();
                for(String piece: art) {
                    artAttributes.add(new AttributeValue().withS(piece));
                }
                dynamoDbJson.put("art", new AttributeValue().withL(artAttributes));
            }

            dynamoDbJson.put("imageUrl", new AttributeValue().withS(object.getImageUrl()));
            dynamoDbJson.put("imageAttribution", new AttributeValue().withS("Courtesy of the Art Institute of Chicago"));

            PutItemRequest putItemRequest = new PutItemRequest()
                    .withItem(dynamoDbJson)
                    .withTableName(tableName);
            try {
                DynamoDbClientProvider.getDynamoDBClient().putItem(putItemRequest);

            } catch (RuntimeException ex) {
                    log.error(ex.getMessage() + " PutItemRequest item {} is cause.", putItemRequest.getItem());
            }
        }
    }

    private static String makeApiRequest(String internetAddress) throws IOException {
            Logger log = LogManager.getLogger();
            HttpClient clientTest = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(internetAddress))
                    .GET()
                    .timeout(Duration.ofMinutes(1))
                    .build();
            CompletableFuture<String> testString = clientTest.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
            String inputJsonString = "";
            try {

                inputJsonString = testString.get();

                int dataIndex = inputJsonString.indexOf("data");
                int endDataArrayIndex = inputJsonString.indexOf("license_text");
                endDataArrayIndex = endDataArrayIndex-10;
                inputJsonString = inputJsonString.substring(dataIndex+6, endDataArrayIndex);
                return inputJsonString;

            } catch (InterruptedException e)  {
                log.warn("InterruptedException thrown.");
            } catch ( ExecutionException ex) {
                log.warn("ExecutionException thrown {}, {}, {}.", ex.getCause(), ex.getMessage(), ex.getStackTrace());
            }

            return inputJsonString;

    }
    public static Set<String> findMedia(String input) {
            Set<String> media = new HashSet<>();
            if(input.contains("paint")) {
                media.add("PAINTING");
            }
            if(input.contains("photo")) {
                media.add("PHOTOGRAPHY");
            }
            if(input.contains("architect")) {
                media.add("ARCHITECTURE");
            }

            if(input.contains("film") || input.contains("movie")) {
                media.add("FILM");
            }
            if(input.contains("installation")) {
                media.add("INSTALLATION");
            }
            if(StringUtils.contains(input, "digital")) {
                media.add("DIGITAL");
            }
            if(StringUtils.containsIgnoreCase(input, "drawings")) {
                media.add("DRAWING");
            }
            if(StringUtils.containsIgnoreCase(input, "textiles") ||
                    StringUtils.containsIgnoreCase(input, "tapestry") ||
                    StringUtils.containsIgnoreCase(input, "quilt") ||
                    StringUtils.containsIgnoreCase(input, "fashion designer")) {
                media.add("TEXTILES");
            }
            if(input.contains("sculptor") || input.contains("sculpture") || input.contains("statue")) {
                media.add("SCULPTURE");
            }
            if(input.contains("lithograph")) {
                media.add("LITHOGRAPH");
            }
            return media;
        }
    public static List<String> findMovements(String input) {
        List<String> movements = new ArrayList<>();

        if(input.contains("Expressionis")) {
            movements.add("EXPRESSIONISM");
        }
        if(input.contains("Impressionis") || (input.contains("Monet"))) {
            movements.add("IMPRESSIONISM");
        }
        if(input.contains("Modernis")) {
            movements.add("MODERNISM");
        }
        if(StringUtils.containsIgnoreCase(input, "Neoclassical")) {
            movements.add("NEOCLASSICAL");
        }
        if(StringUtils.containsIgnoreCase(input, "medieval") || input.contains("Middle Ages") ||
                input.contains("Dark Ages")) {
            movements.add("MEDIEVAL");
        }
        if(input.contains("Renaissance") || input.contains("Caravaggio")) {
            movements.add("RENAISSANCE");
        }
        if(input.contains("Cubis")) {
            movements.add("CUBISM");
        }
        if(input.contains("Futuris")) {
            movements.add("FUTURISM");
        }
        if(StringUtils.containsIgnoreCase(input, "Surrealis")) {
            movements.add("SURREALISM");
        }
        if(StringUtils.containsIgnoreCase(input, "Realis")) {
            movements.add("REALISM");
        }
        return movements;
    }
    private static Set<String> findArtists(List<String> artistIds, String exhibitionName,
                                               List<String> exhibitionMovements, String imageUrl) throws IOException {
        String internetAddress = "https://api.artic.edu/api/v1/artists/";
        Set<String> artistNames = new HashSet<>();
        Gson gson = new Gson();
        for (String id: artistIds) {
            String internetAddressId = internetAddress + id;
            String jsonString = makeApiRequest(internetAddressId);
            AIArtist artist = gson.fromJson(jsonString, AIArtist.class);
            if (artist.getTitle() != null) {
                if (artist.getBirthYear() != null) {
                    //for demo purposes, in a business setting one would import
                    // additional image data from an alternate source, there are many with open apis.
                    if (artistIds.size() < 2 && imageUrl != null) {
                        putInArtistTable(artist, exhibitionName, exhibitionMovements, imageUrl);
                    }
                }
                artistNames.add(artist.getTitle());
            }
        }
        return artistNames;
    }
    public static List<AttributeValue> convertToAttributeValueList(Set<String> attributes) {
        List<AttributeValue> attributeValueList = new ArrayList<>();
        for (String attribute : attributes) {
            attributeValueList.add(new AttributeValue().withS(attribute));
        }
        return attributeValueList;
    }
    private static PutItemResult putInArtistTable(AIArtist artist, String exhibitionName,
                                                      List<String> exhibitionMovements, String imageUrl) {
        String tableName = "artists";
        Map<String, AttributeValue> dynamoDbJson = new HashMap<>();
//Name and birthyear
        dynamoDbJson.put("artistName", new AttributeValue().withS(artist.getTitle()));
        dynamoDbJson.put("birthYear", new AttributeValue().withN(String.valueOf(artist.getBirthYear())));
//Movement/birthyearCheck
        Set<String> movements = new HashSet<>(exhibitionMovements);
        if (artist.getBirthYear() > 1945) {
            movements.add("CONTEMPORARY");
        }
//Exhibitions
        String exhibitionKeys = exhibitionName + "::: Chicago, USA";
        List<AttributeValue> exhibitionAttributeValues = new ArrayList<>();
        exhibitionAttributeValues.add(new AttributeValue().withS(exhibitionKeys));
        dynamoDbJson.put("exhibitions", new AttributeValue().withL(exhibitionAttributeValues));

//Description
        if (artist.getDescription() != null) {
            String description = fixDescription(artist.getDescription());
            dynamoDbJson.put("description", new AttributeValue().withS(description));
//Media
            Set<String> media = findMedia(description);
            if (!media.isEmpty()) {
                List<AttributeValue> mediaAttributes = new ArrayList<>();
                for (String m : media) {
                    mediaAttributes.add(new AttributeValue().withS(m));
                }
                dynamoDbJson.put("media", new AttributeValue().withL(mediaAttributes));
            }
//Tags
            Set<String> tags = findTags(description);
            if (!tags.isEmpty()) {
                dynamoDbJson.put("tags", new AttributeValue().withL(convertToAttributeValueList(tags)));
            }
//Movements
            Set<String> descriptionMovements = new HashSet<>(findMovements(description));
            movements.addAll(descriptionMovements);
        }
        if (!movements.isEmpty()) {
            dynamoDbJson.put("movements", new AttributeValue().withL(convertToAttributeValueList(movements)));
        }

//Image
        if (imageUrl != null) {
            dynamoDbJson.put("imageUrl", new AttributeValue().withS(imageUrl));
            dynamoDbJson.put("imageAttribution", new AttributeValue().withS("Courtesy of the Art Institute of Chicago"));
        }
        PutItemRequest putItemRequest = new PutItemRequest()
                .withItem(dynamoDbJson)
                // .withConditionExpression("attribute_not_exists(artistName)")
                .withTableName(tableName);
        return DynamoDbClientProvider.getDynamoDBClient().putItem(putItemRequest);
    }
    public static Set<String> findTags(String input) {
        Set<String> list = new HashSet<>();
        if(StringUtils.containsIgnoreCase(input, "abstract")) {
            list.add("abstract");
        }
        if(StringUtils.containsIgnoreCase(input, "Japan")) {
            list.add("Japanese");
        }
        if(StringUtils.containsIgnoreCase(input, "prints")) {
            list.add("prints");
        }
        if(StringUtils.containsIgnoreCase(input, "calligraphy")) {
            list.add("calligraphy");
        }
        if(StringUtils.containsIgnoreCase(input, "Baroque") ||
                StringUtils.containsIgnoreCase(input, "Caravaggio") ) {
            list.add("Baroque");
        }
        if(StringUtils.containsIgnoreCase(input, "architect")) {
            list.add("architecture");
            }
        if(StringUtils.containsIgnoreCase(input, "ikebana")|| StringUtils.containsIgnoreCase(input,
                    "flower arrang")) {
            list.add("ikebana");
        }
        return list;
    }
    public static String fixDescription(String description) {
        String descriptionFixed = description.replace("&nbsp;", "");
        String htmlDecodePattern = "<[^>]*>";
        return descriptionFixed.replaceAll(htmlDecodePattern, "");
    }

    private static class AIArtist {
        private String title;
        private String description;
        private Integer birth_date;
        private List<String> exhibitions;
        public String getTitle() {
                return title;
            }
        public String getDescription() {
                return description;
            }
        public Integer getBirthYear() {
                return birth_date;
            }
        public List<String> getExhibitions() {
                return exhibitions;
            }
    }
     private static class AIExhibition {
        private String title;
        private String short_description;
        private String image_url;
        private String aic_start_at;
        private String aic_end_at;
        private List<String> artwork_titles;
        private List<String> artist_ids;
        public String getEndDate() {
                return aic_end_at;
            }
        public String getStartDate() {
                return aic_start_at;
            }

        public List<String> getArt() {
                return artwork_titles;
            }

        public String getTitle() {
                return title;
            }

        public String getDescription() {
                return short_description;
            }
        public String getImageUrl() {
                return image_url;
            }
        public List<String> getArtists() {
                return artist_ids;
            }

    }
}
