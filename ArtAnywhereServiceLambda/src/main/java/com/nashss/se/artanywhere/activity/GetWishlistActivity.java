package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetWishlistRequest;
import com.nashss.se.artanywhere.activity.results.GetWishlistResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.WishlistNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetWishlistActivity {
    private final Logger log = LogManager.getLogger();
    private final WishlistDao wishlistDao;
    @Inject
    public GetWishlistActivity(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    public GetWishlistResult handleRequest(GetWishlistRequest request) {
        Wishlist wishlist;
        System.out.println("Activity begun");
        log.info("Received GetWishlistRequest {}", request);
        try {
            wishlist = wishlistDao.getWishlist(request.getEmail(), request.getListName());
            System.out.println(wishlist);
        } catch (WishlistNotFoundException ex) {
            System.out.println("Wishlist not found");
            log.error("Wishlist requested is not found in database.");
            throw new WishlistNotFoundException(ex.getMessage(), ex.getCause());
        }
        System.out.println(new ModelConverter().toWishlistModel( wishlist));
        return GetWishlistResult.builder()
                .withWishlistModel(new ModelConverter().toWishlistModel(wishlist))
                .build();
    }
}
