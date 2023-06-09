package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByMediumRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByMediumResult;
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

class SearchExhibitionsByMediumActivityTest {
    @Mock
    private ExhibitionDao exhibitionDao;
    @InjectMocks
    private SearchExhibitionsByMediumActivity activity;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validMediumInput_returnsListOfExhibitionModelsWithMedium() {
        //GIVEN
        List<Exhibition> exhibitionList = new ArrayList<>();
        Exhibition exhibition = new Exhibition();
        List<Exhibition.MEDIUM> media = new ArrayList<>();
        media.add(Exhibition.MEDIUM.CERAMICS);
        exhibition.setMedia(media);
        exhibitionList.add(exhibition);
        SearchExhibitionsByMediumRequest request = new SearchExhibitionsByMediumRequest("CERAMICS");
        when(exhibitionDao.searchExhibitionsByMedium(Exhibition.MEDIUM.CERAMICS)).thenReturn(exhibitionList);
        //WHEN
        SearchExhibitionsByMediumResult result = activity.handleRequest(request);
        //THEN
        assertEquals(result.getExhibitions().get(0).getMedia().get(0), (Exhibition.MEDIUM.CERAMICS));
    }
    @Test
    void handleRequest_validMediumInputNoExhibition_throwsException() {
        //GIVEN
        List<Exhibition> exhibitionList = new ArrayList<>();
        Exhibition exhibition = new Exhibition();
        List<Exhibition.MEDIUM> media = new ArrayList<>();
        media.add(Exhibition.MEDIUM.CERAMICS);
        exhibition.setMedia(media);
        exhibitionList.add(exhibition);
        SearchExhibitionsByMediumRequest request = new SearchExhibitionsByMediumRequest("CERAMICS");
        when(exhibitionDao.searchExhibitionsByMedium(Exhibition.MEDIUM.CERAMICS))
                .thenThrow(ExhibitionNotFoundException.class);
        //WHEN/THEN

        assertThrows(ExhibitionNotFoundException.class, ()-> activity.handleRequest(request));
    }
}