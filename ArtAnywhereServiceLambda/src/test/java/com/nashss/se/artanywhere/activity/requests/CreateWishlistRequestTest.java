package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateWishlistRequestTest {

    CreateWishlistRequest request = new CreateWishlistRequest("listName", "email", "great!");

    @Test
    void getListName() {
        assertEquals("listName", request.getListName());
    }

    @Test
    void getEmail() {
        assertEquals("email", request.getEmail());
    }

    @Test
    void getDescription() {
        assertEquals("great!", request.getDescription());
    }

    @Test
    void testToString() {
        assertEquals("CreateWishlistRequest{listName='listName', email='email', description=great!}",
                request.toString());
    }

    @Test
    void builder() {
        assertEquals(CreateWishlistRequest.Builder.class, CreateWishlistRequest.Builder.class);
    }
    @Test
    void withAttribute_Builder_returnsRequestWithEmail() {
        CreateWishlistRequest request1 = CreateWishlistRequest.builder()
                .withEmail("email")
                .withListName("list")
                .withDescription("great!!")
                .build();
        assertEquals("email", request1.getEmail());
        assertEquals("list", request1.getListName());
        assertEquals("great!!", request1.getDescription());
    }
}