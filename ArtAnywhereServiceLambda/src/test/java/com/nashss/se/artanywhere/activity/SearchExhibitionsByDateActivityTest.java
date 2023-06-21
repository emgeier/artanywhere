package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByDateRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByDateResult;
import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SearchExhibitionsByDateActivityTest {
    @Mock
    private ExhibitionDao exhibitionDao;
    @InjectMocks
    private SearchExhibitionsByDateActivity activity;
    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void handleRequest_properlyFormattedDatesRequested_convertedProperlyforDaoMethod() {
        String start = "01-01-2000";
        String end = "01-01-2001";
        SearchExhibitionsByDateRequest request = SearchExhibitionsByDateRequest
                .builder()
                .withEndDate(end)
                .withStartDate(start)
                .build();
        DateConverter converter = new DateConverter();
        LocalDate startDate = converter.convertFromNumberString(start);
        LocalDate endDate = converter.convertFromNumberString(end);

        SearchExhibitionsByDateResult result = activity.handleRequest(request);

        verify(exhibitionDao).searchExhibitionsByDate(startDate, endDate);
    }
    @Test
    void handleRequest_daoThrowsException_throwsException() {
        String start = "01-01-2000";
        String end = "01-01-2001";
        SearchExhibitionsByDateRequest request = SearchExhibitionsByDateRequest
                .builder()
                .withEndDate(end)
                .withStartDate(start)
                .build();
        DateConverter converter = new DateConverter();
        LocalDate startDate = converter.convertFromNumberString(start);
        LocalDate endDate = converter.convertFromNumberString(end);
        when(exhibitionDao.searchExhibitionsByDate(startDate, endDate)).thenThrow(ExhibitionNotFoundException.class);
        assertThrows(ExhibitionNotFoundException.class, () ->activity.handleRequest(request) );

        verify(exhibitionDao).searchExhibitionsByDate(startDate, endDate);
    }
}