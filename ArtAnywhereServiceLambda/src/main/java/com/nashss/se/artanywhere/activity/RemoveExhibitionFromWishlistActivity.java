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
System.out.println("activity");
        try {

            wishlist = wishlistDao.getWishlist(request.getEmail(), request.getListName());
System.out.println(wishlist);

        } catch (WishlistNotFoundException ex) {

            throw new WishlistNotFoundException(ex.getMessage(), ex.getCause());
        }

//        Exhibition exhibitionToRemove;
//
//        try {
//            System.out.println(request.getCityCountry() + "+" + request.getExhibitionName());
//
//            exhibitionToRemove = exhibitionDao.getExhibition(request.getCityCountry(), request.getExhibitionName());
//            System.out.println(exhibitionToRemove);
//        } catch (ExhibitionNotFoundException ex) {
//
//            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
//        }

        //just java object list of exhibitions
//        List<Exhibition> exhibitionsList = Optional.ofNullable(wishlist.getExhibitionsList())
//                .orElseThrow(ExhibitionNotFoundException::new);
        if(wishlist.getExhibitions().contains(request.getExhibitionName()+"*"+request.getCityCountry())) {
            List<String> exhibitions = wishlist.getExhibitions();
            exhibitions.remove(request.getExhibitionName()+"*"+request.getCityCountry());
System.out.println(exhibitions);
            wishlist.setExhibitions(exhibitions);
            wishlistDao.saveWishlist(wishlist);
System.out.println("save wishlist");
        } else {
            throw new ExhibitionNotFoundException(String.format("Exhibition %s not found in wishlist %s",
                    request.getExhibitionName(), request.getListName()));
        }

        return RemoveExhibitionFromWishlistResult.builder()
                .withWishlistModel(new ModelConverter().toWishlistModel(wishlist)).build();
    }
}
