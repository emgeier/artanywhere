package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.AddExhibitionToWishlistRequest;
import com.nashss.se.artanywhere.activity.results.AddExhibitionToWishlistResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import com.nashss.se.artanywhere.exceptions.WishlistNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

public class AddExhibitionToWishlistActivity {

    private final ExhibitionDao exhibitionDao;
    private final WishlistDao wishlistDao;
    @Inject
    public AddExhibitionToWishlistActivity(ExhibitionDao exhibitionDao, WishlistDao wishlistDao) {
        this.exhibitionDao = exhibitionDao;
        this.wishlistDao = wishlistDao;
    }
    public AddExhibitionToWishlistResult handleRequest(AddExhibitionToWishlistRequest request) {
        Wishlist wishlist;

        try {
            wishlist = wishlistDao.getWishlist(request.getEmail(), request.getListName());
        } catch (WishlistNotFoundException ex) {

            throw new WishlistNotFoundException(ex.getMessage(), ex.getCause());
        }
        Exhibition exhibitionToAdd;

        try {
            exhibitionToAdd = exhibitionDao.getExhibition(request.getCityCountry(), request.getExhibitionName());
        } catch (ExhibitionNotFoundException ex) {
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }

        List<Exhibition> exhibitions = Optional.ofNullable(wishlist.getExhibitions()).orElse(new ArrayList<>());
        System.out.println("just after Optional");
        System.out.println(exhibitionToAdd);
        exhibitions.add(exhibitionToAdd);
        System.out.println(exhibitions.get(0));
        wishlist.setExhibitions(exhibitions);
        System.out.println(wishlist);
        wishlistDao.saveWishlist(wishlist);
        System.out.println("saved wishlist");
        return AddExhibitionToWishlistResult.builder()
                .withExhibitions(new ModelConverter().toExhibitionModelList(exhibitions))
                .build();
    }
}
