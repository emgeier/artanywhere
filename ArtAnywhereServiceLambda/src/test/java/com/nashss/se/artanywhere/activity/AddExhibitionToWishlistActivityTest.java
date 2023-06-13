package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.AddExhibitionToWishlistRequest;

import com.nashss.se.artanywhere.activity.results.AddExhibitionToWishlistResult;

import com.nashss.se.artanywhere.converters.DateConverter;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddExhibitionToWishlistActivityTest {
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
        test.setExhibitionName(expectedExhibitionName);
        test.setCityCountry(expectedCityCountry);
        LocalDate expectedDate = LocalDate.of(2022, 11, 01);
        test.setStartDate(expectedDate);
        DateConverter dateConverter = new DateConverter();

        System.out.println("unconverter "+dateConverter.unconvert("2022-10-11"));

        when(exhibitionDao.getExhibition(expectedCityCountry, expectedExhibitionName)).thenReturn(test);
        //WHEN
        AddExhibitionToWishlistResult result = activity.handleRequest(request);
        //THEN
        assertTrue(!result.getExhibitions().isEmpty());

        assertTrue(result.getExhibitions().get(0).contains(expectedCityCountry));
        assertTrue(result.getExhibitions().get(0).contains(expectedExhibitionName));

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
        test.setExhibitionName(expectedExhibitionName);
        test.setCityCountry(expectedCityCountry);
        when(wishlistDao.getWishlist(expectedEmail, expectedName)).thenThrow(WishlistNotFoundException.class);
        when(exhibitionDao.getExhibition(expectedCityCountry, expectedExhibitionName)).thenReturn(test);
        assertThrows(WishlistNotFoundException.class, () -> {
            activity.handleRequest(request);
        });
    }

}
