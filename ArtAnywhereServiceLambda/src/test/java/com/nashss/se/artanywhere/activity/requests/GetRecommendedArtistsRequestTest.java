package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetRecommendedArtistsRequestTest {
    GetRecommendedArtistsRequest request = new GetRecommendedArtistsRequest("Edgar Degas");

    @Test
    void getArtistName() {
        assertEquals("Edgar Degas", request.getArtistName());
    }

    @Test
    void builder() {
        GetRecommendedArtistsRequest request1 = GetRecommendedArtistsRequest.builder()
                .withArtistName("Camille Claudel").build();
        assertEquals("Camille Claudel", request1.getArtistName());
    }
}