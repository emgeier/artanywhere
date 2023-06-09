package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteWishlistRequestTest {

    DeleteWishlistRequest request = new DeleteWishlistRequest("email", "list");

    @Test
    void getEmail() {
        assertEquals("email", request.getEmail());
    }

    @Test
    void getListName() {
        assertEquals("list", request.getListName());
    }

    @Test
    void testToString() {
        assertEquals("DeleteWishlistRequest{email='email', listName='list'}", request.toString());
    }

    @Test
    void builder() {
        DeleteWishlistRequest request1 = DeleteWishlistRequest.builder()
                                            .withListName("list1")
                                            .withEmail("email1")
                                            .build();
        assertEquals("email1", request1.getEmail());
        assertEquals("list1", request1.getListName());

    }
}