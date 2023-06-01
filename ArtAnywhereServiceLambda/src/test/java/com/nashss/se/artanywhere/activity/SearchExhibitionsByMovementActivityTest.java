package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByMovementRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByMovementResult;
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

class SearchExhibitionsByMovementActivityTest {
    @Mock
    ExhibitionDao dao;
    @InjectMocks
    SearchExhibitionsByMovementActivity activity;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validInput_returnsList() {
        Exhibition x1 = new Exhibition();
        x1.setMovement(Exhibition.MOVEMENT.CUBISM);
        Exhibition x2 = new Exhibition();
        x2.setMovement(Exhibition.MOVEMENT.CUBISM);
        List<Exhibition> exhibitionList = new ArrayList<>();
        exhibitionList.add(x1);
        exhibitionList.add(x2);
        SearchExhibitionsByMovementRequest request = new SearchExhibitionsByMovementRequest("CUBISM");
        when(dao.searchExhibitionsByMovement(Exhibition.MOVEMENT.CUBISM)).thenReturn(exhibitionList);
        SearchExhibitionsByMovementResult result = activity.handleRequest(request);
        System.out.println(result);
        System.out.println(request);
        assertEquals(result.getExhibitions().get(0).getMovement(), Exhibition.MOVEMENT.CUBISM);
    }
    @Test
    void handleRequest_invalidInput_returnsList() {

        SearchExhibitionsByMovementRequest request = new SearchExhibitionsByMovementRequest("CUBISM");
        when(dao.searchExhibitionsByMovement(Exhibition.MOVEMENT.CUBISM)).thenThrow(ExhibitionNotFoundException.class);

        assertThrows(ExhibitionNotFoundException.class, ()-> {activity.handleRequest(request);});
    }
}