package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchExhibitionsByArtistRequestTest {
    private SearchExhibitionsByArtistRequest request = new SearchExhibitionsByArtistRequest("Henri Matisse");

    @Test
    void getArtistName() {
        assertEquals("Henri Matisse", request.getArtistName());
    }

    @Test
    void builder() {
        SearchExhibitionsByArtistRequest request1 = SearchExhibitionsByArtistRequest.builder()
                .withArtistName("Pablo Picasso").build();

        assertEquals("Pablo Picasso", request1.getArtistName());
    }
}