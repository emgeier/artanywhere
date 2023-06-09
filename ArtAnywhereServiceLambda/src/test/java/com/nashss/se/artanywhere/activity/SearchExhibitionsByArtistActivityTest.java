package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByArtistRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByArtistResult;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SearchExhibitionsByArtistActivityTest {
    @Mock
    private ExhibitionDao exhibitionDao;
    @InjectMocks
    private SearchExhibitionsByArtistActivity activity;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validInput_returnsExhibitionsList() {
        //GIVEN
        SearchExhibitionsByArtistRequest request = new SearchExhibitionsByArtistRequest("Kandinsky");
        Exhibition exhibition1 = new Exhibition();
        exhibition1.setExhibitionName("name");
        exhibition1.setMovement(Exhibition.MOVEMENT.IMPRESSIONISM);
        Exhibition exhibition2 = new Exhibition();
        exhibition2.setMovement(Exhibition.MOVEMENT.CUBISM);
        List<Exhibition> exhibitionList = new ArrayList<>();
        exhibitionList.add(exhibition1);
        exhibitionList.add(exhibition2);
        when(exhibitionDao.searchExhibitionsByArtist("Kandinsky")).thenReturn(exhibitionList);
        //WHEN
        SearchExhibitionsByArtistResult result = activity.handleRequest(request);
        //THEN
        assertEquals(result.getExhibitions().get(0).getMovement(), Exhibition.MOVEMENT.IMPRESSIONISM);
    }
}