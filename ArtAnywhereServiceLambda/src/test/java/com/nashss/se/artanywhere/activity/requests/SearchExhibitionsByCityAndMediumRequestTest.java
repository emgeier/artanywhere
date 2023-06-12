package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchExhibitionsByCityAndMediumRequestTest {
    SearchExhibitionsByCityAndMediumRequest request = new SearchExhibitionsByCityAndMediumRequest(
            "London", "PAINTING");

    @Test
    void getCityCountry() {
        assertEquals("London", request.getCityCountry());
    }

    @Test
    void getMedium() {
        assertEquals("PAINTING", request.getMedium());
    }

    @Test
    void builder() {
        SearchExhibitionsByCityAndMediumRequest request1 = SearchExhibitionsByCityAndMediumRequest.builder()
                .withCityCountry("Paris")
                .withMedium("FILM").build();
        assertEquals("Paris", request1.getCityCountry());
        assertEquals("FILM", request1.getMedium());
    }

}