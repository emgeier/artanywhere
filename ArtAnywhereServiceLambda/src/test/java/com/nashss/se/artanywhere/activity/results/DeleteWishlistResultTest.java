package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.models.WishlistModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteWishlistResultTest {

    Wishlist testList = new Wishlist();
    WishlistModel modelTest = new ModelConverter().toWishlistModel(testList);


        @Test
    void builder_validInput_buildsResult() {
         DeleteWishlistResult result = DeleteWishlistResult.builder()
                    .withWishlistModel(modelTest)
                    .build();
            assertEquals(modelTest, result.getWishlistModel());
    }


}