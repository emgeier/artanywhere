package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetExhibitionRequest;
import com.nashss.se.artanywhere.activity.requests.GetWishlistRequest;
import com.nashss.se.artanywhere.activity.results.GetExhibitionResult;
import com.nashss.se.artanywhere.activity.results.GetWishlistResult;
import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import static org.junit.jupiter.api.Assertions.*;

class GetExhibitionActivityTest {

    @Mock
    private ExhibitionDao exhibitionDao;
    @InjectMocks
    private GetExhibitionActivity activity;
    @BeforeEach
    void setup() {
        openMocks(this);
    }
    @Test
    public void handleRequest_validInput_returnsExhibit90n() {
        String expectedName = "name";
        String expectedCity = "email";

        GetExhibitionRequest request = GetExhibitionRequest.builder()
                .withCityCountry(expectedCity)
                .withExhibitionName(expectedName)
                .build();
        Exhibition testExhibition = new Exhibition();

        testExhibition.setExhibitionName(request.getExhibitionName());
        testExhibition.setCityCountry(request.getCityCountry());
        testExhibition.setDescription("great");
        List<String> artists = new ArrayList<>();
        artists.add("joe");
        testExhibition.setArtists(artists);
        testExhibition.setAddress("555 Jones St. Paris, TN");
        LocalDate date = LocalDate.now();
        testExhibition.setEndDate(date);
        testExhibition.setStartDate(date);
        testExhibition.setMovement(Exhibition.MOVEMENT.IMPRESSIONISM);
        List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
        testMedia.add(Exhibition.MEDIUM.CERAMICS);
        testExhibition.setMedia(testMedia);

        when(exhibitionDao.getExhibition(request.getCityCountry(), request.getExhibitionName())).thenReturn(testExhibition);
        //WHEN
        GetExhibitionResult result = activity.handleRequest(request);
        //THEN
        assertTrue(result.getExhibition().getCityCountry().contains(request.getCityCountry()));
        assertEquals(result.getExhibition().getExhibitionName(), expectedName);
        assertEquals(result.getExhibition().getDescription(), "great");
        assertEquals(result.getExhibition().getArtists().size(), 1);

    }
}