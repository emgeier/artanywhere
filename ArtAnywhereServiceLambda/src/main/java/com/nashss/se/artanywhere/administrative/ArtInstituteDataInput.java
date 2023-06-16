package com.nashss.se.artanywhere.administrative;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import com.google.gson.Gson;

import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.DynamoDbClientProvider;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


import javax.inject.Inject;


public class ArtInstituteDataInput {

    private final DynamoDBMapper dynamoDBMapper;
    private final DynamoDbClientProvider dynamoDbClientProvider;
    @Inject
    public ArtInstituteDataInput(DynamoDBMapper dynamoDBMapper, DynamoDbClientProvider dynamoDbClientProvider) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.dynamoDbClientProvider = dynamoDbClientProvider;
    }

    public static void main(String[] args) throws IOException {
        String internetAddress = String.format("https://api.artic.edu/api/v1/exhibitions?fields=title,short_description,image_url,aic_start_at,aic_end_at,artwork_titles,artist_ids&page=1");

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
                //Description null check and data cleanup
                if(object.getDescription() != null ) {
                    String description = object.getDescription().replace("<p>", "")
                            .replace("</p>", "");
                    dynamoDbJson.put("description", new AttributeValue().withS(description));
                //Media search, put into attribute values list
                    List<String>media = findMedia(object.getTitle());
                    media.addAll(findMedia(description));
                    if (!media.isEmpty()) {
                        List<AttributeValue> mediaAttributes = convertToAttributeValueList(media);
                        dynamoDbJson.put("media", new AttributeValue().withL(mediaAttributes));
                    }
                //Movement search, put into attribute values list
                    //if in the title, it should be the primary one
                    List<String> movements = findMovements(object.getTitle());
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
//                    if (dateConverter.unconvert(endDate).isBefore(LocalDate.now())) {
//                        continue;
//                    }
                    System.out.println(endDate);
                    dynamoDbJson.put("endDate", new AttributeValue().withS(endDate));
                }
                //Artists ids from AI are used to retrieve the artists' names to add to attributes list.
                if(object.getArtists() != null) {
                    List<String> artistNames = findArtists(object.getArtists(), object.getTitle());
                    List<AttributeValue> artistAttributes = convertToAttributeValueList(artistNames);
//                    List<AttributeValue> artistAttributes = new ArrayList<>();
//                    for (String artist: artistNames) {
//                        artistAttributes.add(new AttributeValue().withS(artist));
//                    }
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

                // Create a PutItemRequest to save the DynamoDB JSON to the table
                PutItemRequest putItemRequest = new PutItemRequest()
                        .withItem(dynamoDbJson)
                        .withTableName(tableName);
                try {
                    DynamoDbClientProvider.getDynamoDBClient().putItem(putItemRequest);

                } catch (RuntimeException ex) {
                    log.error(ex.getMessage() + " PutItemRequest {} is cause.", putItemRequest);

                    System.out.println("error " + ex.getMessage() + "cause: " + putItemRequest.getItem().toString());
                }


            }
     }

        private static String makeApiRequest(String internetAddress) throws IOException {

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
                System.out.println("Interrupted Exception thrown");
            } catch ( ExecutionException ex) {
                System.out.println("Execution Exception thrown");
            }

            return inputJsonString;

    }
        public static List<String> findMedia(String input) {
            List<String> media = new ArrayList<>();
            if(input.contains("paint")) {
                media.add("PAINTING");
            }
            if(input.contains("photo")) {
                media.add("PHOTOGRAPHY");
            }
            if(input.contains("film") || input.contains("movie")) {
                media.add("FILM");
            }
            if(input.contains("installation")) {
                media.add("INSTALLATION");
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
        if(input.contains("Expressionism")) {
            movements.add("EXPRESSIONISM");
        }
        if(input.contains("Impressionism") || (input.contains("Monet"))) {
            movements.add("IMPRESSIONISM");
        }
        if(input.contains("Modernism")) {
            movements.add("MODERNISM");
        }
        if(input.contains("Neoclassical")) {
            movements.add("NEOCLASSICAL");
        }
        if(StringUtils.containsIgnoreCase(input, "medieval") || input.contains("Middle Ages") ||
                input.contains("Dark Ages")) {
            movements.add("MEDIEVAL");
        }
        if(input.contains("Renaissance") || input.contains("Caravaggio")) {
            movements.add("RENAISSANCE");
        }
        if(input.contains("Cubism")) {
            movements.add("CUBISM");
        }
        if(input.contains("Futurism")) {
            movements.add("FUTURISM");
        }
        if(input.contains("Surrealism")) {
            movements.add("SURREALISM");
        }
        return movements;

    }
        private static List<String> findArtists(List<String> artistIds, String exhibitionName) throws IOException {
            String internetAddress = "https://api.artic.edu/api/v1/artists/";
            List<String> artistNames = new ArrayList<>();


            Gson gson = new Gson();

            for (String id: artistIds) {
                String internetAddressId = internetAddress + id;
                String jsonString = makeApiRequest(internetAddressId);
                AIArtist artist = gson.fromJson(jsonString, AIArtist.class);
                System.out.println(artist.getTitle());
                if (artist.getTitle() != null) {
                    if (artist.getBirthYear() != null) {
                        putInArtistTable(artist, exhibitionName);
                    }
                    artistNames.add(artist.getTitle());
                }
            }

            return artistNames;
        }
        public static List<AttributeValue> convertToAttributeValueList(List<String> attributes) {
            List<AttributeValue> attributeValueList = new ArrayList<>();
            for (String attribute : attributes) {
                attributeValueList.add(new AttributeValue().withS(attribute));
            }
            return attributeValueList;
        }
        private static PutItemResult putInArtistTable(AIArtist artist, String exhibitionName) {
            String tableName = "artists";
            Map<String, AttributeValue> dynamoDbJson = new HashMap<>();
//Name and birthyear
            dynamoDbJson.put("artistName", new AttributeValue().withS(artist.getTitle()));
            dynamoDbJson.put("birthYear", new AttributeValue().withN(String.valueOf(artist.getBirthYear())));

//Exhibitions
            String exhibitionKeys = exhibitionName +"::: Chicago, USA";
            List<AttributeValue> exhibitionAttributeValues = new ArrayList<>();
            exhibitionAttributeValues.add(new  AttributeValue().withS(exhibitionKeys));
            dynamoDbJson.put("exhibitions", new AttributeValue().withL(exhibitionAttributeValues));
            //dynamoDbJson.put("exhibitions", new AttributeValue(exhibitionKeys)); if I start checking first to see if it is just an added exhibition.
//Description
            if(artist.getDescription() != null ) {

                String description = fixDescription(artist.getDescription());
                dynamoDbJson.put("description", new AttributeValue().withS(description));
//Media
                List<String>media = findMedia(description);
                if (!media.isEmpty()) {
                    List<AttributeValue> mediaAttributes = new ArrayList<>();
                    for(String m: media) {
                        mediaAttributes.add(new AttributeValue().withS(m));
                    }
                    dynamoDbJson.put("media", new AttributeValue().withL(mediaAttributes));
                }
//Tags
                List<String> tags = findTags(description);
                if (!tags.isEmpty()) {
                    List<AttributeValue> mediaAttributes = convertToAttributeValueList(tags);
                    dynamoDbJson.put("media", new AttributeValue().withL(mediaAttributes));
                }
//Movement
                List<String> movements = findMovements(description);
                if(!movements.isEmpty()) {
                    List<AttributeValue> movementAttributes = new ArrayList<>();
                    for(String m: movements) {
                        movementAttributes.add(new AttributeValue().withS(m));
                    }
                    dynamoDbJson.put("movements", new AttributeValue().withL(movementAttributes));
                }
            }




            PutItemRequest putItemRequest = new PutItemRequest()
                    .withItem(dynamoDbJson)
                   // .withConditionExpression("attribute_not_exists(artistName)")
                    .withTableName(tableName);
            PutItemResult response = DynamoDbClientProvider.getDynamoDBClient().putItem(putItemRequest);
            System.out.println("Saved DynamoDB JSON: " + dynamoDbJson);
            return response;
        }
        public static List<String> findTags(String input) {
            List<String> list = new ArrayList<>();
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
            return list;

        }
        public static String fixDescription(String description) {
            String descriptionFixed = description.replace("<p>", "")
                    .replace("</p>", "");
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
