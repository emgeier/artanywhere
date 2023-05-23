package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
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

}
