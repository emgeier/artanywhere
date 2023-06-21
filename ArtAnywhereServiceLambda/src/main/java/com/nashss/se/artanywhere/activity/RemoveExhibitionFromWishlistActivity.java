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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RemoveExhibitionFromWishlistActivity {
    private final ExhibitionDao exhibitionDao;
    private final WishlistDao wishlistDao;
    private final Logger log = LogManager.getLogger();
    @Inject
    public RemoveExhibitionFromWishlistActivity(ExhibitionDao exhibitionDao, WishlistDao wishlistDao) {
        this.exhibitionDao = exhibitionDao;
        this.wishlistDao = wishlistDao;
    }
    public RemoveExhibitionFromWishlistResult handleRequest(RemoveExhibitionFromWishlistRequest request) {
        Wishlist wishlist;

        try {

            wishlist = wishlistDao.getWishlist(request.getEmail(), request.getListName());
            log.info("RemoveExhibitionFromWishlistRequest for {}", request.getExhibitionName());

        } catch (WishlistNotFoundException ex) {
            log.error("RemoveExhibitionFromWishlistRequest list not found for {}", request.getListName());
            throw new WishlistNotFoundException(ex.getMessage(), ex.getCause());
        }

        if (wishlist.getExhibitions().contains(request.getExhibitionName()+"*"+request.getCityCountry())) {
            List<String> exhibitions = wishlist.getExhibitions();
            exhibitions.remove(request.getExhibitionName()+"*"+request.getCityCountry());

            wishlist.setExhibitions(exhibitions);
            wishlistDao.saveWishlist(wishlist);

        } else {
            throw new ExhibitionNotFoundException(String.format("Exhibition %s not found in wishlist %s",
                    request.getExhibitionName(), request.getListName()));
        }

        return RemoveExhibitionFromWishlistResult.builder()
                .withWishlistModel(new ModelConverter().toWishlistModel(wishlist)).build();
    }
}
