package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchExhibitionsByDateRequestTest {
    SearchExhibitionsByDateRequest request = new SearchExhibitionsByDateRequest("20241010", "20251010");

    @Test
    void getStartDate() {
        assertEquals("20241010", request.getStartDate());
    }

    @Test
    void getEndDate() {
        assertEquals("20251010", request.getEndDate());
    }

    @Test
    void testToString() {
        assertEquals("SearchExhibitionsByDateRequest{startDate='20241010', endDate='20251010'}", request.toString());
    }

    @Test
    void builder() {
        SearchExhibitionsByDateRequest request1 = SearchExhibitionsByDateRequest.builder()
                .withStartDate("20230101")
                .withEndDate("20231201")
                .build();
        assertEquals("20230101", request1.getStartDate());
        assertEquals("20231201", request1.getEndDate());
    }
}