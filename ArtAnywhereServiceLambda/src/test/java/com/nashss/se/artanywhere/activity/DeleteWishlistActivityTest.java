package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.DeleteWishlistRequest;
import com.nashss.se.artanywhere.activity.results.DeleteWishlistResult;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DeleteWishlistActivityTest {
    @Mock
    WishlistDao dao;
    @InjectMocks
    DeleteWishlistActivity activity;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }


    @Test
    void handleRequest_validInput_returnsWishlistModel() {
        DeleteWishlistRequest request = new DeleteWishlistRequest("email", "listName");
        Wishlist wishlist = new Wishlist();
        wishlist.setEmail("email");
        wishlist.setListName("listName");
        when(dao.deleteWishlist("email", "listName")).thenReturn(wishlist);
        DeleteWishlistResult result = activity.handleRequest(request);

        assertTrue(result.getWishlistModel().getEmail().equals("email"));
    }
}