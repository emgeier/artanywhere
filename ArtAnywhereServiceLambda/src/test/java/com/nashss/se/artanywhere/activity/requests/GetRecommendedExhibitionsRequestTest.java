package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetRecommendedExhibitionsRequestTest {
    GetRecommendedExhibitionsRequest request = new
            GetRecommendedExhibitionsRequest("Paris", "Eiffel Engineering");

    @Test
    void testToString() {
        assertEquals("GetRecommendedExhibitionsRequest{cityCountry='Paris', exhibitionName='Eiffel Engineering'}",request.toString());
    }

    @Test
    void builder() {
        GetRecommendedExhibitionsRequest request1 = GetRecommendedExhibitionsRequest.builder()
                .withCityCountry("Berlin")
                .withExhibitionName("Wall Deconstructed")
                .build();
        assertEquals("Wall Deconstructed", request1.getExhibitionName());
        assertEquals("Berlin", request1.getCityCountry());
    }
}