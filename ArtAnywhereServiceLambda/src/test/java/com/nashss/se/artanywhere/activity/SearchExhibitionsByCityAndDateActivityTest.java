package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityAndDateRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityAndDateResult;
import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SearchExhibitionsByCityAndDateActivityTest {
    @Mock
    private ExhibitionDao exhibitionDao;
    @InjectMocks
    private SearchExhibitionsByCityAndDateActivity activity;
    List<Exhibition> testExhibitions;



    @BeforeEach
    void setUp() {
        openMocks(this);
        testExhibitions = new ArrayList<>();
        Exhibition exhibition = new Exhibition();
        exhibition.setCityCountry("Madrid, Spain");
        Exhibition exhibition1 = new Exhibition();
        exhibition1.setCityCountry("Madrid, Spain");
        testExhibitions.add(exhibition1);

    }

    @Test
    void handleRequest() {
        //GIVEN
        SearchExhibitionsByCityAndDateRequest request = SearchExhibitionsByCityAndDateRequest
                .builder().withCityCountry("Madrid, Spain")
                .withStartDate("06-07-2020")
                .withEndDate("06-07-2020")
                .build();

        DateConverter converter = new DateConverter();

        LocalDate date = converter.convertFromNumberString("06-07-2020");
        when(exhibitionDao.searchExhibitionsByCityAndDate("Madrid, Spain", date, date)).thenReturn(testExhibitions);
        //WHEN
        SearchExhibitionsByCityAndDateResult result = activity.handleRequest(request);
        //THEN
        assertTrue(result.getExhibitions().get(0).getCityCountry().equals("Madrid, Spain"));
        verify(exhibitionDao).searchExhibitionsByCityAndDate(any(), any(), any());
    }
}