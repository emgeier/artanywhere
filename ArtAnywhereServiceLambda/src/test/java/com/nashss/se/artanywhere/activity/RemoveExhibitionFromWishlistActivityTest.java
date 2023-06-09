package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.RemoveExhibitionFromWishlistRequest;
import com.nashss.se.artanywhere.activity.results.RemoveExhibitionFromWishlistResult;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.WishlistDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class RemoveExhibitionFromWishlistActivityTest {
    @Mock
    ExhibitionDao exhibitionDao;
    @Mock
    WishlistDao wishlistDao;
    @InjectMocks
    RemoveExhibitionFromWishlistActivity activity;
    RemoveExhibitionFromWishlistRequest request;

    Exhibition exhibition;
    Wishlist testWishlist;
    List<String> exhibitionList;

    @BeforeEach
    void setUp() {
        openMocks(this);
        request = RemoveExhibitionFromWishlistRequest.builder()
                .withCityCountry("city")
                .withEmail("email")
                .withListName("list")
                .withExhibitionName("XName")
                .build();
        exhibition = new Exhibition();
        exhibition.setExhibitionName("XName");
        exhibition.setCityCountry("city");
        Exhibition exhibitionB = new Exhibition();
        exhibitionB.setCityCountry("London");
        exhibitionB.setExhibitionName("Degas");
        testWishlist = new Wishlist();
        exhibitionList = new ArrayList<>();
        exhibitionList.add(exhibition.getExhibitionName()+"*"+exhibition.getCityCountry());
        exhibitionList.add(exhibitionB.getExhibitionName()+"*"+exhibitionB.getCityCountry());
        testWishlist.setExhibitions(exhibitionList);
    }

    @Test
    void handleRequest_validRequest_returnsResult() {
        //GIVEN
        when(wishlistDao.getWishlist(request.getEmail(), request.getListName()))
                .thenReturn(testWishlist);
        when(exhibitionDao.getExhibition(request.getCityCountry(), request.getExhibitionName()))
                .thenReturn(exhibition);
        //WHEN
        RemoveExhibitionFromWishlistResult result = activity.handleRequest(request);
        //THEN
        assertFalse(result.getWishlistModel().getExhibitions().contains(exhibition.getExhibitionName()));
    }
}