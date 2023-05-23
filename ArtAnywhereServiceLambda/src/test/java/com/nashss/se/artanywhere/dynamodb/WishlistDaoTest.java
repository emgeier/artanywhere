package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.WishlistNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


public class WishlistDaoTest {

    @Mock
    DynamoDBMapper mapper;

    @InjectMocks
    WishlistDao wishlistDao;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    public void saveWishlist_callsMapperWithWishlist() {
        Wishlist testWishlist = new Wishlist();

        Wishlist result = wishlistDao.saveWishlist(testWishlist);

        verify(mapper).save(testWishlist);
        assertEquals(testWishlist, result);
    }
    @Test
    public void getWishlist_callsMapperWithKey() {
        Wishlist test = new Wishlist();
        test.setListName("listNameTest");
        test.setEmail("emailTest");
        when(mapper.load(Wishlist.class, "emailTest", "listNameTest")).thenReturn(test);
        Wishlist result = wishlistDao.getWishlist("listNameTest", "emailTest");
        verify(mapper).load(Wishlist.class, "emailTest", "listNameTest");
        assertEquals(test.getEmail(), result.getEmail());
    }
    @Test
    public void getWishlist_noWishlistInDatabase_throwsException() {
//        Wishlist test = new Wishlist();
//        test.setListName("listNameTest");
//        test.setEmail("emailTest");
//        when(mapper.load(Wishlist.class, "emailTest", "listNameTest")).thenReturn(null);
//        Wishlist result =
//        verify(mapper).load(Wishlist.class, "emailTest", "listNameTest");
        assertThrows(WishlistNotFoundException.class, () -> {wishlistDao.getWishlist("listNameTest", "emailTest");});
    }

}
