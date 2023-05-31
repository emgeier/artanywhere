package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.DeleteWishlistRequest;
import com.nashss.se.artanywhere.activity.results.DeleteWishlistResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteWishlistActivity {
    private final Logger log = LogManager.getLogger();
    private final WishlistDao wishlistDao;

    @Inject
    public DeleteWishlistActivity(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }
    public DeleteWishlistResult handleRequest(DeleteWishlistRequest request) {

        Wishlist wishlist = wishlistDao.deleteWishlist(request.getEmail(), request.getListName());

        return DeleteWishlistResult.builder()
                .withWishlistModel(new ModelConverter().toWishlistModel(wishlist))
                .build();
    }
}
