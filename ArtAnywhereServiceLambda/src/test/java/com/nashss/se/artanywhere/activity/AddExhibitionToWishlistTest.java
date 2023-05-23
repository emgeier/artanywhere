package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.AddExhibitionToWishlistRequest;

import com.nashss.se.artanywhere.activity.results.AddExhibitionToWishlistResult;

import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import com.nashss.se.artanywhere.exceptions.WishlistNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddExhibitionToWishlistTest {
    @Mock
    private ExhibitionDao exhibitionDao;
    @Mock
    private WishlistDao wishlistDao;
    @InjectMocks
    AddExhibitionToWishlistActivity activity;

    @BeforeEach
    void setup() {
        openMocks(this);
    }
    @Test
    public void handleRequest_validInput_returnsValidResult() {
        //GIVEN
        String expectedName = "name";
        String expectedEmail = "email";
        String expectedExhibitionName = "exName";
        String expectedCityCountry = "Madrid";
        AddExhibitionToWishlistRequest request = AddExhibitionToWishlistRequest.builder()
                .withListName(expectedName)
                .withExhibitionName(expectedExhibitionName)
                .withCityCountry(expectedCityCountry)
                .withEmail(expectedEmail)
                .build();

        Wishlist testWishlist = new Wishlist();
        testWishlist.setListName(request.getListName());
        testWishlist.setEmail(request.getEmail());
        when(wishlistDao.getWishlist(expectedEmail, expectedName)).thenReturn(testWishlist);

        Exhibition test = new Exhibition();
        test.setName(expectedExhibitionName);
        test.setCityCountry(expectedCityCountry);

        when(exhibitionDao.getExhibition(expectedCityCountry, expectedExhibitionName)).thenReturn(test);
        //WHEN
        AddExhibitionToWishlistResult result = activity.handleRequest(request);
        //THEN
        assertTrue(!result.getExhibitions().isEmpty());
        assertEquals(result.getExhibitions().get(0).getCityCountry(), expectedCityCountry);
        assertEquals(result.getExhibitions().get(0).getName(), expectedExhibitionName);

    }
    @Test
    public void handleRequest_exhibitionNotInDatabase_throwsException() {
        String expectedName = "name";
        String expectedEmail = "email";
        String expectedExhibitionName = "exName";
        String expectedCityCountry = "Madrid";
        AddExhibitionToWishlistRequest request = AddExhibitionToWishlistRequest.builder()
                .withListName(expectedName)
                .withExhibitionName(expectedExhibitionName)
                .withCityCountry(expectedCityCountry)
                .withEmail(expectedEmail)
                .build();

        Wishlist testWishlist = new Wishlist();
        testWishlist.setListName(request.getListName());
        testWishlist.setEmail(request.getEmail());
        when(wishlistDao.getWishlist(expectedEmail, expectedName)).thenReturn(testWishlist);
        when(exhibitionDao.getExhibition(expectedCityCountry, expectedExhibitionName)).thenThrow(ExhibitionNotFoundException.class);
        assertThrows(ExhibitionNotFoundException.class, () -> {
            activity.handleRequest(request);
        });
    }
    @Test
    public void handleRequest_wishlistNotInDatabase_throwsException() {
        String expectedName = "name";
        String expectedEmail = "email";
        String expectedExhibitionName = "exName";
        String expectedCityCountry = "Madrid";
        AddExhibitionToWishlistRequest request = AddExhibitionToWishlistRequest.builder()
                .withListName(expectedName)
                .withExhibitionName(expectedExhibitionName)
                .withCityCountry(expectedCityCountry)
                .withEmail(expectedEmail)
                .build();

        Exhibition test = new Exhibition();
        test.setName(expectedExhibitionName);
        test.setCityCountry(expectedCityCountry);
        when(wishlistDao.getWishlist(expectedEmail, expectedName)).thenThrow(WishlistNotFoundException.class);
        when(exhibitionDao.getExhibition(expectedCityCountry, expectedExhibitionName)).thenReturn(test);
        assertThrows(WishlistNotFoundException.class, () -> {
            activity.handleRequest(request);
        });
    }

}
