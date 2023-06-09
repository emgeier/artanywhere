package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddExhibitionToWishlistRequestTest {

    private AddExhibitionToWishlistRequest request = new AddExhibitionToWishlistRequest(
            "email", "listName", "city","exhibitionName" );


    @BeforeEach
    void setUp() {
    }

    @Test
    void getExhibitionName_validString_returnsString() {
        assertEquals("exhibitionName", request.getExhibitionName());
    }

    @Test
    void getCityCountry_validString_returnsString() {
        assertEquals("city", request.getCityCountry());
    }

    @Test
    void getListName_validString_returnsString() {
        assertEquals("listName", request.getListName());
    }

    @Test
    void getEmail_validString_returnsString() {
        assertEquals("email", request.getEmail());
    }

    @Test
    void testToString_validString_returnsString() {
        assertEquals("AddExhibitionToWishlistRequest{email='email', listName='listName', " +
                "cityCountry='city', exhibitionName='exhibitionName'}", request.toString());
    }

    @Test
    void builder_returnsSBuilder() {
        assertEquals(AddExhibitionToWishlistRequest.builder().getClass(), AddExhibitionToWishlistRequest.Builder.class);
    }
    @Test
    void withEmail_validString_returnsBuilderWithEmail() {
        AddExhibitionToWishlistRequest request1 = AddExhibitionToWishlistRequest.builder().withEmail("email").build();
        assertEquals("email", request1.getEmail());
    }
    @Test
    void withListName_validString_returnsBuilderWithListName() {
        AddExhibitionToWishlistRequest request1 = AddExhibitionToWishlistRequest.builder().withListName("list").build();
        assertEquals("list", request1.getListName());
    }
    @Test
    void withCityCountry_validString_returnsBuilderWithEmail() {

        AddExhibitionToWishlistRequest request1 = AddExhibitionToWishlistRequest.builder().withCityCountry("London").build();
        assertEquals("London", request1.getCityCountry());
    }
    @Test
    void withExhibitonName_validString_returnsBuilderWithEmail() {

        AddExhibitionToWishlistRequest request1 = AddExhibitionToWishlistRequest.builder()
                .withExhibitionName("London New Wave")
                .build();
        assertEquals("London New Wave", request1.getExhibitionName());
    }
}