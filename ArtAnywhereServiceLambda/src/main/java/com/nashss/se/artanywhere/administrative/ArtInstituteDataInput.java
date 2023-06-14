package com.nashss.se.artanywhere.administrative;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.IDynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.google.gson.Gson;

import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.DynamoDbClientProvider;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
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
    public Exhibition saveToExhibitionTable(Exhibition exhibition) {

        dynamoDBMapper.save(exhibition);
        return exhibition;
    }

    public static void main(String[] args) throws IOException {
            String apiUrl = "https://api.artic.edu/api/v1/exhibitions\\?fields\\=title,short_description,image_url,aic_start_at,aic_end_at,artwork_titles,artist_ids\\page\\=2\n";
            String tableName = "exhibitions"; // Replace with your DynamoDB table name

            // Create a Gson instance
            Gson gson = new Gson();

            // Make API request and get the JSON string
            String jsonString = makeApiRequest(apiUrl);
            // Create a DynamoDbClient
           //AmazonDynamoDB dynamoDbClient = DynamoDbClientProvider.getDynamoDBClient();

            // Deserialize the JSON string into an array of objects
            AIExhibition[] objects = gson.fromJson(jsonString, AIExhibition[].class);

            for (AIExhibition object : objects) {
                //field conversions for cleaner table data


                // Convert the object to DynamoDB JSON format
                if (object.getTitle() == null || object.getTitle().isEmpty()){continue;}
                Map<String, AttributeValue> dynamoDbJson = new HashMap<>();
                dynamoDbJson.put("cityCountry", new AttributeValue().withS("Chicago, USA"));
                dynamoDbJson.put("exhibitionName", new AttributeValue().withS(object.getTitle()));
                dynamoDbJson.put("institution", new AttributeValue().withS("Art Institute of Chicago"));
                dynamoDbJson.put("address", new AttributeValue().withS("111 S Michigan Ave, Chicago, IL 60603"));


                if(object.getDescription() != null ) {
                    String description = object.getDescription().replace("<p>", "")
                            .replace("</p>", "");

                    System.out.println(description);
                    dynamoDbJson.put("description", new AttributeValue().withS(description));
                }


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
                    if (dateConverter.unconvert(endDate).isBefore(LocalDate.now())) {
                        continue;
                    }
                    System.out.println(endDate);
                    dynamoDbJson.put("endDate", new AttributeValue().withS(endDate));
                }


               // dynamoDbJson.put("art", new AttributeValue().withL(new AttributeValue(object.getArt())));
                dynamoDbJson.put("imageUrl", new AttributeValue().withS(object.getImageUrl()));
                dynamoDbJson.put("imageAttribution", new AttributeValue().withS("Courtesy of Art Institute of Chicago"));
                // Add more attributes as needed
                Map<String, AttributeValue> valueMap = new HashMap<>();
                valueMap.put(":yesterday", new AttributeValue().withS("2023-06-13"));
                // Create a PutItemRequest to save the DynamoDB JSON to the table
                PutItemRequest putItemRequest = new PutItemRequest()
                        .withItem(dynamoDbJson)
                       /// .withExpressionAttributeValues(valueMap)
                      //  .withConditionExpression("attribute_not_exists(exhibitionName)")
                       // .withConditionExpression("endDate > :yesterday")
                        .withTableName(tableName);
                //in the future do not leave in this condition expression-- some exhibitions travel, so must specify both parts of key
                Exhibition exhibition = new Exhibition();
                exhibition.setExhibitionName(object.getTitle());
                exhibition.setCityCountry("Chicago, USA");
                exhibition.setDescription(object.getDescription());

                //saveToExhibitionTable(exhibition);
                // Execute the PutItem request
                PutItemResult response = DynamoDbClientProvider.getDynamoDBClient().putItem(putItemRequest);;

                System.out.println("Saved DynamoDB JSON: " + dynamoDbJson);
               // System.out.println("PutItemResponse: " + response);
            }

         //   dynamoDbClient.close();
     }

        private static String makeApiRequest(String apiUrl) throws IOException {
            String internetAddress = String.format("https://api.artic.edu/api/v1/exhibitions?fields=title,short_description,image_url,aic_start_at,aic_end_at,artwork_titles,artist_ids&page=3");

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
                System.out.println(testString.get());
                inputJsonString = testString.get();

                int dataIndex = inputJsonString.indexOf("data");
                int endDataArrayIndex = inputJsonString.indexOf("license_text");
                endDataArrayIndex = endDataArrayIndex-10;
                inputJsonString = inputJsonString.substring(dataIndex+6, endDataArrayIndex);
                System.out.println(inputJsonString);
                return inputJsonString;

            } catch (InterruptedException e)  {
                throw new RuntimeException(e);
            } catch ( ExecutionException ex) {
                System.out.println(ex.getCause());
            }

            System.out.println(testString);
            return inputJsonString;

    }

        // Define your object class based on the JSON structure
        private static class AIExhibition {
            private String title;
            private String short_description;
            private String image_url;
            private String aic_start_at;
            private String aic_end_at;
            private List<String> artwork_titles;



            public String getEndDate() {
                return aic_end_at;
            }

            public String getStartDate() {
                return aic_start_at;
            }

            public List<String> getArt() {
                return artwork_titles;
            }
// Add more fields as needed

            public String getTitle() {
                return title;
            }

            public String getDescription() {
                return short_description;
            }
            public String getImageUrl() {
                return image_url;
            }

            // Add getter/setter methods for additional fields
        }


}
