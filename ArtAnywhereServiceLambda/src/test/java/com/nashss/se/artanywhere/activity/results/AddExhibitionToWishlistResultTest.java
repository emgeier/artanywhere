package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddExhibitionToWishlistResultTest {
    List<String> exhibitionsTest = new ArrayList<>();
    AddExhibitionToWishlistResult result;

    @BeforeEach
    void setup() {
        String exhibit1 = "exhibit1";
        String exhibit2 = "exhibit2";
        exhibitionsTest.add(exhibit2);
        exhibitionsTest.add(exhibit1);
        result = new AddExhibitionToWishlistResult(exhibitionsTest);
    }
    @Test
    void getExhibitions_returnsExhibitions() {
        assertEquals(exhibitionsTest, result.getExhibitions());
    }

    @Test
    void toString_validResult_returnsResultStringWithAttribute() {
        assertEquals("AddExhibitionToWishlistResult{exhibitions=[exhibit2, exhibit1]}", result.toString());
    }

    @Test
    void builder_validInput_buildsResult() {
        AddExhibitionToWishlistResult result1 = AddExhibitionToWishlistResult.builder()
                .withExhibitions(exhibitionsTest).build();
        assertEquals(exhibitionsTest, result1.getExhibitions());
    }
}