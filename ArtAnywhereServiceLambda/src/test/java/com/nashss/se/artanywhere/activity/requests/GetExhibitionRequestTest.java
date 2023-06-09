package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetExhibitionRequestTest {
    GetExhibitionRequest request = new GetExhibitionRequest("Paris", "Eiffel Engineering");

    @Test
    void testToString() {
        assertEquals("GetExhibitionRequest{cityCountry='Paris', exhibitionName='Eiffel Engineering'}",request.toString());
    }

    @Test
    void builder() {
        GetExhibitionRequest request1 = GetExhibitionRequest.builder()
                .withCityCountry("Berlin")
                .withExhibitionName("Wall Deconstructed")
                .build();
        assertEquals("Wall Deconstructed", request1.getExhibitionName());
        assertEquals("Berlin", request1.getCityCountry());
    }
}