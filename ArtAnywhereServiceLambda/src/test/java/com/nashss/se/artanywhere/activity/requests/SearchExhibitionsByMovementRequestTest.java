package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchExhibitionsByMovementRequestTest {
    SearchExhibitionsByMovementRequest request = new SearchExhibitionsByMovementRequest("EXPRESSIONISM");

    @Test
    void getMovement() {
        assertEquals("EXPRESSIONISM", request.getMovement());
    }

    @Test
    void testToString() {
        assertEquals("SearchExhibitionsByMovementRequest{movement='EXPRESSIONISM'}", request.toString());
    }

    @Test
    void builder() {
        SearchExhibitionsByMovementRequest request1 = SearchExhibitionsByMovementRequest.builder()
                .withMovement("IMPRESSIONISM").build();
        assertEquals("IMPRESSIONISM", request1.getMovement());
    }
}