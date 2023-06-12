package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchExhibitionsByMediumRequestTest {

    SearchExhibitionsByMediumRequest request = new SearchExhibitionsByMediumRequest("SCULPTURE");

    @Test
    void getMedium() {
        assertEquals("SCULPTURE", request.getMedium());
    }

    @Test
    void testToString() {
        assertEquals("SearchExhibitionsByMediumRequest{medium='SCULPTURE'}", request.toString());
    }

    @Test
    void builder() {
        SearchExhibitionsByMediumRequest request1 = SearchExhibitionsByMediumRequest.builder()
                .withMedium("FILM").build();
        assertEquals("FILM", request1.getMedium());
    }
}