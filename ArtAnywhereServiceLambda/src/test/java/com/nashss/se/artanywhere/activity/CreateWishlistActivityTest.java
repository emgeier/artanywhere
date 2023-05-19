package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.CreateWishlistRequest;
import com.nashss.se.artanywhere.activity.results.CreateWishlistResult;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

}
