package com.nashss.se.artanywhere.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

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
    }

    public static void main(String[] args) {

        String internetAddress = String.format("https://api.artic.edu/api/v1/exhibitions\\?fields\\=title,short_description,image_url,aic_start_at,aic_end_at,artwork_titles,artist_ids\n");

        HttpClient clientTest = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(internetAddress))
                .timeout(Duration.ofMinutes(1))
                .build();
        CompletableFuture<String> testString = clientTest.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);


//        App app = new App(input, output, host, subordinates);
        ArtInstituteOfChicagoApiCaller caller = new ArtInstituteOfChicagoApiCaller();
        caller.run();
    }

}
