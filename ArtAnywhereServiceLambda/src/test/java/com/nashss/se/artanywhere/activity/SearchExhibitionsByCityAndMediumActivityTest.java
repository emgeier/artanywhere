package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityAndMediumRequest;
import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityAndMediumResult;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityResult;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SearchExhibitionsByCityAndMediumActivityTest {

    @Mock
    private ExhibitionDao exhibitionDao;
    @InjectMocks
    private SearchExhibitionsByCityAndMediumActivity activity;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_happyCase_returnsResultWithRequestAttributes() {
        //GIVEN
        SearchExhibitionsByCityAndMediumRequest request = new SearchExhibitionsByCityAndMediumRequest("Madrid, Spain", "CERAMICS");
        List<Exhibition> testExhibitions = new ArrayList<>();
        Exhibition exhibition = new Exhibition();
        exhibition.setCityCountry("Madrid, Spain");
        List<Exhibition.MEDIUM> media = new ArrayList<>();
        media.add(Exhibition.MEDIUM.CERAMICS);
        exhibition.setMedia(media);

        Exhibition exhibition1 = new Exhibition();
        exhibition1.setCityCountry("Madrid, Spain");
        exhibition1.setMedia(media);
        testExhibitions.add(exhibition);
        testExhibitions.add(exhibition1);
        when(exhibitionDao.searchExhibitionsByCityAndMedium("Madrid, Spain", Exhibition.MEDIUM.CERAMICS))
                .thenReturn(testExhibitions);
        //WHEN
        SearchExhibitionsByCityAndMediumResult result = activity.handleRequest(request);
        //THEN
        assertTrue(result.getExhibitions().get(0).getCityCountry().equals(exhibition1.getCityCountry()));
        assertTrue(result.getExhibitions().get(0).getMedia().equals(exhibition1.getMedia()));
    }
    @Test
    void handleRequest_daoThrowsExhibitionNotFoundException_propagatesException() {
        //GIVEN
        SearchExhibitionsByCityAndMediumRequest request = new SearchExhibitionsByCityAndMediumRequest("Madrid, Spain", "CERAMICS");
        List<Exhibition> testExhibitions = new ArrayList<>();
        Exhibition exhibition = new Exhibition();
        exhibition.setCityCountry("Madrid, Spain");
        List<Exhibition.MEDIUM> media = new ArrayList<>();
        media.add(Exhibition.MEDIUM.CERAMICS);
        exhibition.setMedia(media);

        Exhibition exhibition1 = new Exhibition();
        exhibition1.setCityCountry("Madrid, Spain");
        exhibition1.setMedia(media);
        testExhibitions.add(exhibition);
        testExhibitions.add(exhibition1);
        when(exhibitionDao.searchExhibitionsByCityAndMedium("Madrid, Spain", Exhibition.MEDIUM.CERAMICS))
                .thenThrow(ExhibitionNotFoundException.class);
        //WHEN
        assertThrows((ExhibitionNotFoundException.class), () ->  activity.handleRequest(request));

    }
}