package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.CreateWishlistRequest;
import com.nashss.se.artanywhere.activity.results.CreateWishlistResult;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;

import com.nashss.se.artanywhere.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateWishlistActivityTest {
    @Mock
    private WishlistDao wishlistDao;
    @InjectMocks
    private  CreateWishlistActivity createWishlistActivity;
    @BeforeEach
    void setup() {
        openMocks(this);
    }
    @Test
    public void handleRequest_validInput_returnsWishlist() {
        //GIVEN
        String expectedName = "name";
        String expectedEmail = "email";

        CreateWishlistRequest request = CreateWishlistRequest.builder()
            .withEmail(expectedEmail)
            .withListName(expectedName)
            .build();
        Wishlist testWishlist = new Wishlist();

        testWishlist.setListName(request.getListName());
        testWishlist.setEmail(request.getEmail());
        when(wishlistDao.saveWishlist(any())).thenReturn(testWishlist);
        //WHEN
        CreateWishlistResult result = createWishlistActivity.handleRequest(request);
        //THEN
        assertTrue(result.getWishlistModel().getEmail().contains(request.getEmail()));
        assertEquals(result.getWishlistModel().getListName(), expectedName);
    }
    @Test
    public void handleRequest_validDescription_returnsWishlistWithDescription() {
        //GIVEN
        String expectedName = "name";
        String expectedEmail = "email";
        String expected = "test";

        CreateWishlistRequest request = CreateWishlistRequest.builder()
                .withEmail(expectedEmail)
                .withListName(expectedName)
                .withDescription(expected)
                .build();
        Wishlist testWishlist = new Wishlist();

        testWishlist.setListName(request.getListName());
        testWishlist.setEmail(request.getEmail());
        when(wishlistDao.saveWishlist(any())).thenReturn(testWishlist);
        //WHEN
        CreateWishlistResult result = createWishlistActivity.handleRequest(request);
        //THEN
        assertTrue(result.getWishlistModel().getEmail().contains(request.getEmail()));
        assertEquals(result.getWishlistModel().getListName(), expectedName);
        assertTrue(result.getWishlistModel().getDescription().equals(expected));
    }
    @Test
    public void handleRequest_invalidDescription_throwsException() {
        //GIVEN
        String expectedName = "name";
        String expectedEmail = "email";
        String expectedTags = "test\\\\?";

        CreateWishlistRequest request = CreateWishlistRequest.builder()
                .withEmail(expectedEmail)
                .withListName(expectedName)
                .withDescription(expectedTags)
                .build();
        Wishlist testWishlist = new Wishlist();

        testWishlist.setListName(request.getListName());
        testWishlist.setEmail(request.getEmail());
        when(wishlistDao.saveWishlist(any())).thenReturn(testWishlist);

        //WHEN/THEN
        assertThrows(InvalidAttributeValueException.class, ()->{createWishlistActivity.handleRequest(request);});

    }
    @Test
    public void handleRequest_invalidName_throwsException() {
        //GIVEN
        String expectedName = "name\\\\";
        String expectedEmail = "email";
        String expectedTags = "test?";

        CreateWishlistRequest request = CreateWishlistRequest.builder()
                .withEmail(expectedEmail)
                .withListName(expectedName)
                .withDescription(expectedTags)
                .build();
        Wishlist testWishlist = new Wishlist();

        testWishlist.setListName(request.getListName());
        testWishlist.setEmail(request.getEmail());
        when(wishlistDao.saveWishlist(any())).thenReturn(testWishlist);

        //WHEN/THEN
        assertThrows(InvalidAttributeValueException.class, ()->{createWishlistActivity.handleRequest(request);});

    }

}
