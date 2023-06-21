package com.nashss.se.artanywhere.activity;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.nashss.se.artanywhere.activity.requests.AddExhibitionToWishlistRequest;
import com.nashss.se.artanywhere.activity.results.AddExhibitionToWishlistResult;

import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import com.nashss.se.artanywhere.exceptions.WishlistNotFoundException;
import com.nashss.se.artanywhere.metrics.MetricsConstants;
import com.nashss.se.artanywhere.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

public class AddExhibitionToWishlistActivity {
    private final Logger log = LogManager.getLogger();

    private final ExhibitionDao exhibitionDao;
    private final WishlistDao wishlistDao;

    /*
    *Implementation of the AddExhibitionToWishlistActivity for the ArtAnywhereService's AddExhibitionToWishlist
    * API to add an Exhibition to a list of exhibitions the user plans to attend.
    * @param ExhibitionDao to access the Exhibitions table
    * @param WishlistDao to access the Wishlists table
    * */
    @Inject
    public AddExhibitionToWishlistActivity(ExhibitionDao exhibitionDao, WishlistDao wishlistDao) {
        this.exhibitionDao = exhibitionDao;
        this.wishlistDao = wishlistDao;
    }
    /*
    * Method handles the request by combining the DynamoDB keys of an Exhibition, the name and city, and adding to
    * a list of strings to the wishlist and persisting the updated wishlist.
    * If the wishlist requested is not in the wishlists table, this throws a WishlistNotFoundException.
    * If the exhibition requested in not in the exhibitions table, this throws an ExhibitionNotFoundException.
    * @param addExhibitionToWishlistRequest request object containing the keys to an Exhibition object and a Wishlist object.
    * @param addExhibitionToWishlistResult result object containing the WishlistModel of the updated wishlist.
    * */
    public AddExhibitionToWishlistResult handleRequest(AddExhibitionToWishlistRequest request) {
        log.info("Activity received Request {} with attributes: {}, {} , {} and {}", request,
                request.getExhibitionName(), request.getCityCountry(), request.getListName(), request.getEmail());
        Wishlist wishlist;
        try {
            wishlist = wishlistDao.getWishlist(request.getEmail(), request.getListName());
        } catch (WishlistNotFoundException ex) {
            log.error("Wishlist not found");
            throw new WishlistNotFoundException(ex.getMessage(), ex.getCause());
        }
        Exhibition exhibitionToAdd;
        try {
            exhibitionToAdd = exhibitionDao.getExhibition(request.getCityCountry(), request.getExhibitionName());

        } catch (ExhibitionNotFoundException ex) {
            log.error("Exhibition {} not found.", request.getExhibitionName());

            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }
        /*
        For list of Exhibition java objects for administrative use only
         */
        List<Exhibition> exhibitionsList = Optional.ofNullable(wishlist.getExhibitionsList()).orElse(new ArrayList<>());

        List<String> exhibitions = Optional.ofNullable(wishlist.getExhibitions()).orElse(new ArrayList<>());

        exhibitionsList.add(exhibitionToAdd);

        /*
        For list of Exhibition keys for DynamodDB table
         */
        exhibitions.add(request.getExhibitionName()+"*"+request.getCityCountry());

        wishlist.setExhibitions(exhibitions);

        wishlistDao.saveWishlist(wishlist);

        return AddExhibitionToWishlistResult.builder()
                .withExhibitions(exhibitions)
                .build();
    }
}
