package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;

import com.nashss.se.artanywhere.metrics.MetricsPublisher;
import com.nashss.se.artanywhere.models.ExhibitionModel;
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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ExhibitionDaoTest {
    @Mock
    private MetricsPublisher metricsPublisher;
    @Mock
    DynamoDBMapper dynamoDBMapper;
    @InjectMocks
    ExhibitionDao dao;
    @BeforeEach
    void setup() {
        openMocks(this);
    }
    @Test
    public void getExhibition_callsMapperWithKey() {
        Exhibition test = new Exhibition();
        test.setCityCountry("Madrid");
        test.setExhibitionName("Picasso Rules");
        List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
        testMedia.add(Exhibition.MEDIUM.CERAMICS);
        testMedia.add(Exhibition.MEDIUM.FILM);
        test.setMedia(testMedia);
        test.setAddress("555 Jones St. Paris, TN");
        List<String> artists = new ArrayList<>();
        artists.add("joe");
        artists.add("phil");
        test.setArtists(artists);
        LocalDate date = LocalDate.now();
        test.setEndDate(date);
        test.setStartDate(date);
        test.setMovement(Exhibition.MOVEMENT.IMPRESSIONISM);
        testMedia.add(Exhibition.MEDIUM.PAINTING);
        test.setMedia(testMedia);
        test.setArt(new ArrayList<>(artists));

        when(dynamoDBMapper.load(Exhibition.class, "Madrid", "Picasso Rules")).thenReturn(test);
        Exhibition result = dao.getExhibition("Madrid", "Picasso Rules");
        verify(dynamoDBMapper).load(Exhibition.class, "Madrid", "Picasso Rules");
        assertEquals(test.getCityCountry(), result.getCityCountry());
        assertEquals(result.getMedia().get(0), Exhibition.MEDIUM.CERAMICS);
        assertEquals(result.getMovement(), Exhibition.MOVEMENT.IMPRESSIONISM);
        assertEquals(result.getArtists(), artists);
        assertEquals(result.getArt(), artists);
        assertEquals(result.getStartDate(), date);
        assertEquals(result.getEndDate(), date);
        assertEquals(result.getExhibitionName(), "Picasso Rules");
    }
    @Test
    public void getExhibition_allAttributesNullExceptKey_callsMapperWithKey() {
        Exhibition test = new Exhibition();
        test.setCityCountry("Madrid");
        test.setExhibitionName("Picasso Rules");

        when(dynamoDBMapper.load(Exhibition.class, "Madrid", "Picasso Rules")).thenReturn(test);
        Exhibition result = dao.getExhibition("Madrid", "Picasso Rules");
        verify(dynamoDBMapper).load(Exhibition.class, "Madrid", "Picasso Rules");
        assertEquals(test.getCityCountry(), result.getCityCountry());
    }
    @Test
    public void getExhibition_notInDatabase_throwsException() {
        assertThrows(ExhibitionNotFoundException.class, () -> {
            dao.getExhibition("test", "test");
        });
    }
    @Test
    public void getExhibition_nullKey_throwsException() {
        assertThrows(ExhibitionNotFoundException.class, () -> {
            dao.getExhibition(null, "test");
        });
    }
    @Test
    public void getRecommendedExhibitions_callsMapperExpectedNumberOfTimesAndWays() {
        //GIVEN
        Exhibition test = new Exhibition();
        test.setCityCountry("Madrid");
        test.setExhibitionName("Picasso Rules");
        List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
        testMedia.add(Exhibition.MEDIUM.CERAMICS);
        testMedia.add(Exhibition.MEDIUM.FILM);
        test.setMedia(testMedia);
        test.setAddress("555 Jones St. Paris, TN");
        List<String> artists = new ArrayList<>();
        artists.add("joe");
        artists.add("phil");
        test.setArtists(artists);
        LocalDate date = LocalDate.now();
        test.setEndDate(date);
        test.setStartDate(date);
        test.setMovement(Exhibition.MOVEMENT.IMPRESSIONISM);
        testMedia.add(Exhibition.MEDIUM.PAINTING);
        test.setMedia(testMedia);
        test.setArt(new ArrayList<>(artists));
        List<Exhibition> exhibitions = new ArrayList<>();
        exhibitions.add(test);

        when(dynamoDBMapper.query(any(), any())).thenReturn(null);
        when(dynamoDBMapper.load(Exhibition.class, "Madrid", "Picasso Rules")).thenReturn(test);
        //WHEN
        List<Exhibition> result = dao.getRecommendedExhibitions("Madrid", "Picasso Rules");
        //THEN
        verify(dynamoDBMapper).load(Exhibition.class, "Madrid", "Picasso Rules");
        verify(dynamoDBMapper, times(5)).query(any(), any());
    }
    @Test
    public void getRecommendedExhibitions_nullMovement_callsMapperExpectedNumberOfTimesAndWays() {
        //GIVEN
        Exhibition test = new Exhibition();
        test.setCityCountry("Madrid");
        test.setExhibitionName("Picasso Rules");
        List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
        testMedia.add(Exhibition.MEDIUM.CERAMICS);
        testMedia.add(Exhibition.MEDIUM.FILM);
        test.setMedia(testMedia);
        test.setAddress("555 Jones St. Paris, TN");
        List<String> artists = new ArrayList<>();
        artists.add("joe");
        artists.add("phil");
        test.setArtists(artists);
        LocalDate date = LocalDate.now();
        test.setEndDate(date);
        test.setStartDate(date);

        testMedia.add(Exhibition.MEDIUM.PAINTING);
        test.setMedia(testMedia);
        test.setArt(new ArrayList<>(artists));
        List<Exhibition> exhibitions = new ArrayList<>();
        exhibitions.add(test);

        when(dynamoDBMapper.query(any(), any())).thenReturn(null);
        when(dynamoDBMapper.load(Exhibition.class, "Madrid", "Picasso Rules")).thenReturn(test);
        //WHEN
        List<Exhibition> result = dao.getRecommendedExhibitions("Madrid", "Picasso Rules");
        //THEN
        verify(dynamoDBMapper).load(Exhibition.class, "Madrid", "Picasso Rules");
        verify(dynamoDBMapper, times(4)).query(any(), any());
    }
    @Test
    public void getRecommendedExhibitions_nullMedia_callsMapperExpectedNumberOfTimesAndWays() {
        //GIVEN
        Exhibition test = new Exhibition();
        test.setCityCountry("Madrid");
        test.setExhibitionName("Picasso Rules");

        test.setAddress("555 Jones St. Paris, TN");
        List<String> artists = new ArrayList<>();
        artists.add("joe");
        artists.add("phil");
        test.setArtists(artists);
        LocalDate date = LocalDate.now();
        test.setEndDate(date);
        test.setStartDate(date);
        test.setMovement(Exhibition.MOVEMENT.IMPRESSIONISM);
        test.setArt(new ArrayList<>(artists));
        List<Exhibition> exhibitions = new ArrayList<>();
        exhibitions.add(test);

        when(dynamoDBMapper.query(any(), any())).thenReturn(null);
        when(dynamoDBMapper.load(Exhibition.class, "Madrid", "Picasso Rules")).thenReturn(test);
        //WHEN
        List<Exhibition> result = dao.getRecommendedExhibitions("Madrid", "Picasso Rules");
        //THEN
        verify(dynamoDBMapper).load(Exhibition.class, "Madrid", "Picasso Rules");
        verify(dynamoDBMapper, times(2)).query(any(), any());
    }
    @Test
    public void getRecommendedExhibitions_nullDates_callsMapperExpectedNumberOfTimesAndWays() {
        //GIVEN
        Exhibition test = new Exhibition();
        test.setCityCountry("Madrid");
        test.setExhibitionName("Picasso Rules");
        List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
        testMedia.add(Exhibition.MEDIUM.CERAMICS);
        testMedia.add(Exhibition.MEDIUM.FILM);
        test.setMedia(testMedia);
        test.setAddress("555 Jones St. Paris, TN");
        List<String> artists = new ArrayList<>();
        artists.add("joe");
        artists.add("phil");
        test.setArtists(artists);
        test.setMovement(Exhibition.MOVEMENT.IMPRESSIONISM);
        testMedia.add(Exhibition.MEDIUM.PAINTING);
        test.setMedia(testMedia);
        test.setArt(new ArrayList<>(artists));
        List<Exhibition> exhibitions = new ArrayList<>();
        exhibitions.add(test);

        when(dynamoDBMapper.query(any(), any())).thenReturn(null);
        when(dynamoDBMapper.load(Exhibition.class, "Madrid", "Picasso Rules")).thenReturn(test);
        //WHEN
        List<Exhibition> result = dao.getRecommendedExhibitions("Madrid", "Picasso Rules");
        //THEN
        verify(dynamoDBMapper).load(Exhibition.class, "Madrid", "Picasso Rules");
        verify(dynamoDBMapper, times(4)).query(any(), any());
    }
    @Test
    public void getRecommendedExhibitions_inputExhibitionNotInDatabase_throwsException() {
        //GIVEN

        when(dynamoDBMapper.load(Exhibition.class, "Madrid", "Picasso Rules")).thenReturn(null);
        //WHEN/THEN
        assertThrows(ExhibitionNotFoundException.class, () -> {
            dao.getRecommendedExhibitions("Madrid", "Picasso Rules");
        });

    }
    @Test
    public void getRecommendedExhibitions_similarExhibitionNotInDatabase_returnsEmptyList() {
        //GIVEN
        Exhibition test = new Exhibition();
        test.setCityCountry("Madrid");
        test.setExhibitionName("Picasso Rules");
        List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
        testMedia.add(Exhibition.MEDIUM.CERAMICS);
        testMedia.add(Exhibition.MEDIUM.FILM);
        test.setMedia(testMedia);
        test.setAddress("555 Jones St. Paris, TN");
        List<String> artists = new ArrayList<>();
        artists.add("joe");
        artists.add("phil");
        test.setArtists(artists);
        LocalDate date = LocalDate.now();
        test.setEndDate(date);
        test.setStartDate(date);
        test.setMovement(Exhibition.MOVEMENT.IMPRESSIONISM);
        testMedia.add(Exhibition.MEDIUM.PAINTING);
        test.setMedia(testMedia);
        test.setArt(new ArrayList<>(artists));
        List<Exhibition> exhibitions = new ArrayList<>();
        exhibitions.add(test);

        when(dynamoDBMapper.query(any(), any())).thenReturn(null);
        when(dynamoDBMapper.load(Exhibition.class, "Madrid", "Picasso Rules")).thenReturn(test);
        //WHEN
        List<Exhibition> exhibitionList = dao.getRecommendedExhibitions("Madrid", "Picasso Rules");
        //THEN
        assertTrue(exhibitionList.isEmpty());
        assertNotNull(exhibitionList);
    }
    @Test
    void searchExhibitionsByCity_exhibitionsListEmpty_throwsException_callsMapper() {

        when(dynamoDBMapper.query(any(), any())).thenReturn(null);

        assertThrows(ExhibitionNotFoundException.class, () -> dao.searchExhibitionsByCity("Los Angeles, USA"));
        verify(dynamoDBMapper).query(eq(Exhibition.class), any());
    }
    @Test
    void searchExhibitionsByDate_exhibitionsListEmpty_throwsException_callsMapper() {
        LocalDate date = LocalDate.now();

        when(dynamoDBMapper.scan(any(), any())).thenReturn(null);

        assertThrows(ExhibitionNotFoundException.class, () -> dao.searchExhibitionsByDate(date, date));
        verify(dynamoDBMapper).scan(eq(Exhibition.class), any());
    }
    @Test
    void searchExhibitionsByArtist_exhibitionsListEmpty_throwsException_callsMapper() {
        LocalDate date = LocalDate.now();

        when(dynamoDBMapper.scan(any(), any())).thenReturn(null);

        assertThrows(ExhibitionNotFoundException.class, () -> dao.searchExhibitionsByArtist("date, date"));
        verify(dynamoDBMapper).scan(eq(Exhibition.class), any());
    }

}

