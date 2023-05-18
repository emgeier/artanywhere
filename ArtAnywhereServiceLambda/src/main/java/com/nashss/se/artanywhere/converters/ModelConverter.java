package com.nashss.se.artanywhere.converters;

import com.nashss.se.artanywhere.models.WishlistModel;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {
    public WishlistModel toWishlistModel(Wishlist wishlist) {
        List<String> tags = null;
        if (wishlist.getTags() != null) {
            tags = new ArrayList<>(wishlist.getTags());
        }

        return WishlistModel.builder()
                .withEmail(wishlist.getEmail())
                .withListName(wishlist.getListName())
                .withTags(wishlist.getTags())
                //.withActivities(itinerary.getActivities())
                .build();
    }
}
