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
    @Inject
    public AddExhibitionToWishlistActivity(ExhibitionDao exhibitionDao, WishlistDao wishlistDao) {

        this.exhibitionDao = exhibitionDao;
        this.wishlistDao = wishlistDao;
    }
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
           // metricsPublisher.addMetric(MetricsConstants.ADD_EXHIBITION_EXHIBITION_NOT_FOUND_COUNT, 0.0,
            //        StandardUnit.Count);
        } catch (ExhibitionNotFoundException ex) {
            log.error("Exhibition {} not found.", request.getExhibitionName());
           // metricsPublisher.addMetric(MetricsConstants.ADD_EXHIBITION_EXHIBITION_NOT_FOUND_COUNT, 1.0,
            //        StandardUnit.Count);
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }
        //just java object list of exhibitions
        List<Exhibition> exhibitionsList = Optional.ofNullable(wishlist.getExhibitionsList()).orElse(new ArrayList<>());

        List<String> exhibitions = Optional.ofNullable(wishlist.getExhibitions()).orElse(new ArrayList<>());

        exhibitionsList.add(exhibitionToAdd);
        //dynamodb list of exhibitions
        exhibitions.add(request.getExhibitionName()+"*"+request.getCityCountry());

        wishlist.setExhibitions(exhibitions);

        wishlistDao.saveWishlist(wishlist);

        return AddExhibitionToWishlistResult.builder()
                .withExhibitions(exhibitions)
                .build();
    }
}
