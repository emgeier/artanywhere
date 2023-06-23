package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.CreateWishlistRequest;
import com.nashss.se.artanywhere.activity.results.CreateWishlistResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.InvalidAttributeValueException;
import com.nashss.se.artanywhere.models.WishlistModel;
import com.nashss.se.artanywhere.utils.InputValidationUtil;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateWishlistActivity {
    private final Logger log = LogManager.getLogger();
    private final WishlistDao wishlistDao;
    @Inject
    public CreateWishlistActivity(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }


    public CreateWishlistResult handleRequest(final CreateWishlistRequest request) {
        log.info("Received CreateWishlistRequest {}", request);
        if (!InputValidationUtil.noDangerousCharacters(request.getListName())) {
            log.error("Invalid user input for wishlist name.");
            throw new InvalidAttributeValueException("Wishlist name [" + request.getListName() +
                    "] contains illegal characters");
        }

        Wishlist newWishlist = new Wishlist();

        newWishlist.setListName(request.getListName());
        newWishlist.setEmail(request.getEmail());
        //null check unnecessary
        if (request.getDescription() != null) {
            if(!InputValidationUtil.noDangerousCharacters(request.getDescription())) {
                log.error("Invalid user input for description.");
                throw new InvalidAttributeValueException("Description contains illegal characters.");
            }
            newWishlist.setDescription(request.getDescription());
        }
        wishlistDao.saveWishlist(newWishlist);

        WishlistModel model = new ModelConverter().toWishlistModel(newWishlist);
        return CreateWishlistResult.builder()
                .withWishlistModel(model)
                .build();
    }
}
