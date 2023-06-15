package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveExhibitionFromWishlistRequestTest {

    private RemoveExhibitionFromWishlistRequest request = new RemoveExhibitionFromWishlistRequest("email",
            "listName","city", "exhibitionName" );

    @Test
    void getEmail() {
        assertEquals("email", request.getEmail());
    }

    @Test
    void getListName() {
        assertEquals("listName", request.getListName());
    }

    @Test
    void getCityCountry() {
        assertEquals("city", request.getCityCountry());
    }

    @Test
    void getExhibitionName() {
        assertEquals("exhibitionName", request.getExhibitionName());
    }

    @Test
    void builder() {
        RemoveExhibitionFromWishlistRequest request1 = RemoveExhibitionFromWishlistRequest.builder()
                .withExhibitionName("exhibit1")
                .withListName("list1")
                .withEmail("email@1")
                .withCityCountry("Singapore")
                .build();
        assertEquals("exhibit1", request1.getExhibitionName());
        assertEquals("list1", request1.getListName());
        assertEquals("email@1", request1.getEmail());
        assertEquals("Singapore", request1.getCityCountry());
    }
}