package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetRecommendedExhibitionsRequest;
import com.nashss.se.artanywhere.activity.results.GetRecommendedExhibitionsResult;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GetRecommendedExhibitionsActivityTest {
    @Mock
    ExhibitionDao exhibitionDao;

    @InjectMocks GetRecommendedExhibitionsActivity activity;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_exhibitionsFound_returnsExhibitionsInResult() {
        //GIVEN
        List<Exhibition> exhibitionList = new ArrayList<>();
        GetRecommendedExhibitionsRequest request = GetRecommendedExhibitionsRequest.builder()
                .withCityCountry("Paris, France").withExhibitionName("Camille Claudel").build();
        when(exhibitionDao.getRecommendedExhibitions("Paris, France", "Camille Claudel")).thenReturn(exhibitionList);

        //WHEN
        GetRecommendedExhibitionsResult result = activity.handleRequest(request);

        //THEN
        assertEquals(exhibitionList, result.getExhibitions());
    }
    void handleRequest_exhibitionsNotFound_throwsExhibitionNotFound() {
        //GIVEN
        List<Exhibition> exhibitionList = new ArrayList<>();
        GetRecommendedExhibitionsRequest request = GetRecommendedExhibitionsRequest.builder()
                .withCityCountry("Paris, France").withExhibitionName("Camille Claudel").build();
        when(exhibitionDao.getRecommendedExhibitions("Paris, France", "Camille Claudel")).thenReturn(exhibitionList);

        //WHEN
        GetRecommendedExhibitionsResult result = activity.handleRequest(request);

        //THEN
        assertEquals(exhibitionList, result.getExhibitions());
    }
}