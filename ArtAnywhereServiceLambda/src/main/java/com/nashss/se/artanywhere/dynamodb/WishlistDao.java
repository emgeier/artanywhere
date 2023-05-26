package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.WishlistNotFoundException;


import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WishlistDao {
    private final DynamoDBMapper dynamoDbMapper;
    @Inject
    public WishlistDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    public Wishlist saveWishlist(Wishlist wishlist) {
        this.dynamoDbMapper.save(wishlist);
        return wishlist;
    }
    public Wishlist getWishlist(String email, String listName) {

        return Optional.ofNullable(dynamoDbMapper.load(Wishlist.class, email, listName))
                .orElseThrow(WishlistNotFoundException::new);
    }
}
