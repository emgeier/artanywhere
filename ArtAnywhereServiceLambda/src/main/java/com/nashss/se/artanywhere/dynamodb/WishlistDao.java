package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
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
        Wishlist wishlist = dynamoDbMapper.load(Wishlist.class, email, listName);

        if (wishlist == null) {
            throw new WishlistNotFoundException(String.format("Wishlist named %s associated with email, %s," +
                    " not found in database.", listName, email));
        }
        return wishlist;
    }
    public Wishlist deleteWishlist(String email, String listname) {

            Wishlist wishlist = new Wishlist();
            wishlist.setEmail(email);
            wishlist.setListName(listname);
            dynamoDbMapper.delete(wishlist);
            return wishlist;

    }
}
