package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityRequest;
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

class SearchExhibitionsByCityActivityTest {
    @Mock
    ExhibitionDao exhibitionDao;
    @InjectMocks
    SearchExhibitionsByCityActivity activity;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_cityWithExhibitionsInDatabase_returnsExhibitionsList() {
        //GIVEN
        SearchExhibitionsByCityRequest request = new SearchExhibitionsByCityRequest("Madrid, Spain");
        List<Exhibition> testExhibitions = new ArrayList<>();
        Exhibition exhibition = new Exhibition();
        exhibition.setCityCountry("Madrid, Spain");
        Exhibition exhibition1 = new Exhibition();
        exhibition1.setCityCountry("Madrid, Spain");
        testExhibitions.add(exhibition);
        testExhibitions.add(exhibition1);
        when(exhibitionDao.searchExhibitionsByCity("Madrid, Spain")).thenReturn(testExhibitions);
        //WHEN
        SearchExhibitionsByCityResult result = activity.handleRequest(request);
        //THEN
        assertTrue(result.getExhibitions().get(0).getCityCountry().equals(exhibition1.getCityCountry()));
    }
    @Test
    void handleRequest_cityNotInDatabase_throwsException() {
        //GIVEN
        SearchExhibitionsByCityRequest request = new SearchExhibitionsByCityRequest("Madrid, Spain");
        when(exhibitionDao.searchExhibitionsByCity("Madrid, Spain"))
                .thenThrow(ExhibitionNotFoundException.class);
        //WHEN/THEN
        assertThrows(ExhibitionNotFoundException.class, ()-> {
            activity.handleRequest(request);
        });


    }
}