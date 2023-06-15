package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchExhibitionsByCityRequestTest {
    SearchExhibitionsByCityRequest request = new SearchExhibitionsByCityRequest("Nashville, USA");

    @Test
    void getCityCountry() {
        assertEquals("Nashville, USA", request.getCityCountry());
    }

    @Test
    void testToString() {
        assertEquals("SearchExhibitionsByCityRequest{cityCountry='Nashville, USA'}", request.toString());
    }

    @Test
    void builder() {
        SearchExhibitionsByCityRequest request1 = SearchExhibitionsByCityRequest.builder()
                .withCityCountry("London").build();
        assertEquals("London", request1.getCityCountry());
    }
}