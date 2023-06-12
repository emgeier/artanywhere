//package com.nashss.se.artanywhere.activity;
//
//import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityAndDateRequest;
//import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityAndDateResult;
//import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
//import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
//import com.nashss.se.artanywhere.models.ExhibitionModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.openMocks;
//
//class SearchExhibitionsByCityAndDateActivityTest {
//    @Mock
//    private ExhibitionDao exhibitionDao;
//    @InjectMocks
//    private SearchExhibitionsByCityAndDateActivity activity;
//    List<Exhibition> testExhibitions;
//    Exhibition exhibition1;
//
//
//    @BeforeEach
//    void setUp() {
//        openMocks(this);
//        testExhibitions = new ArrayList<>();
//        Exhibition exhibition = new Exhibition();
//        exhibition.setCityCountry("Madrid, Spain");
//        exhibition1 = new Exhibition();
//        exhibition1.setCityCountry("Madrid, Spain");
//        testExhibitions.add(exhibition);
//        testExhibitions.add(exhibition1);
//    }
//
//    @Test
//    void handleRequest() {
//        SearchExhibitionsByCityAndDateRequest request = SearchExhibitionsByCityAndDateRequest
//                .builder().withCityCountry("Madrid")
//                .withStartDate(String.valueOf(LocalDate.now()))
//                .withEndDate(String.valueOf(LocalDate.now()))
//                .build();
//        when(exhibitionDao.searchExhibitionsByCityAndDate("Madrid", LocalDate.now(),LocalDate.now()))
//                .thenReturn(testExhibitions);
//        SearchExhibitionsByCityAndDateResult result = activity.handleRequest(request);
//        assertTrue(result.getExhibitions().get(1).getCityCountry().equals(exhibition1.getCityCountry()));
//
//    }
//}