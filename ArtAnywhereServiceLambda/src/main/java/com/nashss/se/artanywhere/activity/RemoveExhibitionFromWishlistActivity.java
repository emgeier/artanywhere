package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.RemoveExhibitionFromWishlistRequest;
import com.nashss.se.artanywhere.activity.results.RemoveExhibitionFromWishlistResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import com.nashss.se.artanywhere.exceptions.WishlistNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RemoveExhibitionFromWishlistActivity {
    private final ExhibitionDao exhibitionDao;
    private final WishlistDao wishlistDao;
    @Inject
    public RemoveExhibitionFromWishlistActivity(ExhibitionDao exhibitionDao, WishlistDao wishlistDao) {
        this.exhibitionDao = exhibitionDao;
        this.wishlistDao = wishlistDao;
    }
    public RemoveExhibitionFromWishlistResult handleRequest(RemoveExhibitionFromWishlistRequest request) {
        Wishlist wishlist;

        try {
            wishlist = wishlistDao.getWishlist(request.getEmail(), request.getListName());

        } catch (WishlistNotFoundException ex) {

            throw new WishlistNotFoundException(ex.getMessage(), ex.getCause());
        }

        Exhibition exhibitionToRemove;

        try {System.out.println(request.getCityCountry() + "+" + request.getExhibitionName());

            exhibitionToRemove = exhibitionDao.getExhibition(request.getCityCountry(), request.getExhibitionName());
        } catch (ExhibitionNotFoundException ex) {

            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }

        //just java object list of exhibitions
        List<Exhibition> exhibitionsList = Optional.ofNullable(wishlist.getExhibitionsList())
                .orElseThrow(ExhibitionNotFoundException::new);

        List<String> exhibitions = Optional.ofNullable(wishlist.getExhibitions()).orElse(new ArrayList<>());
        exhibitionsList.remove(exhibitionToRemove);
        //dynamodb list of exhibitions
        exhibitions.remove(request.getExhibitionName()+"*"+request.getCityCountry());
        System.out.println(exhibitions.get(0));
        wishlist.setExhibitions(exhibitions);
        System.out.println(wishlist);
        wishlistDao.saveWishlist(wishlist);
        System.out.println("saved wishlist");
        return RemoveExhibitionFromWishlistResult.builder()
                .withWishlistModel(new ModelConverter.t).build();
    }
}
