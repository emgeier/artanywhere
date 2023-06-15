package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.activity.requests.RemoveExhibitionFromWishlistRequest;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.models.WishlistModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveExhibitionFromWishlistResultTest {

    Wishlist testList = new Wishlist();
    WishlistModel modelTest = new ModelConverter().toWishlistModel(testList);

    @Test
    void builder_validInput_buildsResult() {
        RemoveExhibitionFromWishlistResult result = RemoveExhibitionFromWishlistResult.builder()
                .withWishlistModel(modelTest)
                .build();
        assertEquals(modelTest, result.getWishlistModel());
    }
    @Test
    void toString_validResult_returnsResultStringWithAttribute() {
        RemoveExhibitionFromWishlistResult result = RemoveExhibitionFromWishlistResult.builder()
                .withWishlistModel(modelTest)
                .build();
        assertTrue(result.toString().contains("wishlist"));
    }
}