package com.nashss.se.artanywhere.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ArtInstituteOfChicagoApiCaller {


    /**
     * Initiates and manages the main "game loop"
     */
    public void run() {
//        NonplayerCharacter character = this.host;
//        while (character != null) {
//            character = character.interact(input, output);
//            output.println("");
//        }
//        output.println("Exiting...");
        System.out.println("*****");
    }

    public static void main(String[] args) {

        String internetAddress = String.format("https://api.artic.edu/api/v1/exhibitions?fields=title,short_description,image_url,aic_start_at,aic_end_at,artwork_titles,artist_ids");

        HttpClient clientTest = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(internetAddress))
                .GET()
                .timeout(Duration.ofMinutes(1))
                .build();
        CompletableFuture<String> testString = clientTest.sendAsync(request,
                HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);

        try {
            System.out.println(testString.get());
            String inputJsonString = testString.get();

            int dataIndex = inputJsonString.indexOf("data");
            inputJsonString = inputJsonString.substring(dataIndex+5);
            System.out.println(inputJsonString);

        } catch (InterruptedException e)  {
            throw new RuntimeException(e);
        } catch ( ExecutionException ex) {
            System.out.println(ex.getCause());
        }

System.out.println(testString);

//        App app = new App(input, output, host, subordinates);
        ArtInstituteOfChicagoApiCaller caller = new ArtInstituteOfChicagoApiCaller();
        caller.run();
    }

}
