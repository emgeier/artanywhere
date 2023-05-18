package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.CreateWishlistRequest;
import com.nashss.se.artanywhere.activity.results.CreateWishlistResult;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.models.WishlistModel;
import com.nashss.se.artanywhere.converters.ModelConverter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CreateWishlistActivity {
    private final WishlistDao wishlistDao;
    @Inject
    public CreateWishlistActivity(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }
    public CreateWishlistResult handleRequest(final CreateWishlistRequest request) {


        List<String> wishlistTags = null;
        if (request.getTags() != null) {
            wishlistTags = new ArrayList<>(request.getTags());
        }
        Wishlist newWishlist = new Wishlist();

        newWishlist.setListName(request.getListName());
        newWishlist.setEmail(request.getEmail());

        newWishlist.setTags(wishlistTags);
        newWishlist.setExhibitions(new ArrayList<>());

        wishlistDao.saveWishlist(newWishlist);

        WishlistModel model = new ModelConverter().toWishlistModel(newWishlist);
        return CreateWishlistResult.builder()
                .withWishlistModel(model)
                .build();
    }
}
