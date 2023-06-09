package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetWishlistRequestTest {

    @Test
    void builder_withEmailAndListName_returnsValidRequest() {
    GetWishlistRequest request = GetWishlistRequest.builder()
            .withEmail("email")
            .withListName("list").build();
    assertEquals("email", request.getEmail());
    assertEquals("list", request.getListName());
    }
}