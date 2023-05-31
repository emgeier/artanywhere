package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetWishlistRequest;
import com.nashss.se.artanywhere.activity.results.GetWishlistResult;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.WishlistNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GetWishlistActivityTest {
    @Mock
    private WishlistDao wishlistDao;
    @InjectMocks
    private  GetWishlistActivity activity;
    @BeforeEach
    void setup() {
        openMocks(this);
    }
    @Test
    public void handleRequest_validInput_returnsWishlist() {
        String expectedName = "name";
        String expectedEmail = "email";

        GetWishlistRequest request = GetWishlistRequest.builder()
                .withEmail(expectedEmail)
                .withListName(expectedName)
                .build();
        Wishlist testWishlist = new Wishlist();

        testWishlist.setListName(request.getListName());
        testWishlist.setEmail(request.getEmail());
        testWishlist.setDescription("great");
        List<String> exhibitions = new ArrayList<>();
        exhibitions.add("filmfestival");
        testWishlist.setExhibitions(exhibitions);
        when(wishlistDao.getWishlist(request.getEmail(), request.getListName())).thenReturn(testWishlist);
        //WHEN
        GetWishlistResult result = activity.handleRequest(request);
        //THEN
        assertTrue(result.getWishlist().getEmail().contains(request.getEmail()));
        assertEquals(result.getWishlist().getListName(), expectedName);
        assertEquals(result.getWishlist().getDescription(), "great");
        assertEquals(result.getWishlist().getExhibitions().size(), 1);
    }
    @Test
    public void handleRequest_invalidName_throwsException() {
        //GIVEN
        String expectedName = "name";
        String expectedEmail = "email";

        GetWishlistRequest request = GetWishlistRequest.builder()
                .withEmail(expectedEmail)
                .withListName(expectedName)
                .build();

        when(wishlistDao.getWishlist(request.getEmail(), request.getListName())).thenThrow(WishlistNotFoundException.class);

        //WHEN/THEN
        assertThrows(WishlistNotFoundException.class, ()->{activity.handleRequest(request);});

    }

}