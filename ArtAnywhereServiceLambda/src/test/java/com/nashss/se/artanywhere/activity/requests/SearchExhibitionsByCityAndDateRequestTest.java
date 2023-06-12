package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchExhibitionsByCityAndDateRequestTest {

    SearchExhibitionsByCityAndDateRequest request = new SearchExhibitionsByCityAndDateRequest("20010101",
            "20251231", "Buenos Aires");

    @Test
    void getStartDate() {
        assertEquals("20010101", request.getStartDate());
    }

    @Test
    void getEndDate() {
        assertEquals("20251231", request.getEndDate());
    }

    @Test
    void getCityCountry() {
        assertEquals("Buenos Aires", request.getCityCountry());
    }

    @Test
    void builder() {
        SearchExhibitionsByCityAndDateRequest request1 = SearchExhibitionsByCityAndDateRequest.builder()
                .withCityCountry("Paris")
                .withEndDate("19990909")
                .withStartDate("19990101").build();

        assertEquals("Paris", request1.getCityCountry());
        assertEquals("19990909", request1.getEndDate());
        assertEquals("19990101", request1.getStartDate());

    }
}