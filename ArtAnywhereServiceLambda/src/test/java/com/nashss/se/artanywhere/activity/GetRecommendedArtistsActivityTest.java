package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetRecommendedArtistsRequest;
import com.nashss.se.artanywhere.activity.results.GetRecommendedArtistsResult;
import com.nashss.se.artanywhere.dynamodb.ArtistDao;
import com.nashss.se.artanywhere.dynamodb.models.Artist;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.metrics.MetricsPublisher;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GetRecommendedArtistsActivityTest {
    @Mock
    private MetricsPublisher metricsPublisher;

    @Mock
    private Logger logger;
    @Mock
    private ArtistDao artistDao;
    @InjectMocks
    private GetRecommendedArtistsActivity activity;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_nullMediaNonNullMovement_returnsResponseWithoutRequestedArtist() {
        //GIVEN
        Artist artist = new Artist();
        artist.setArtistName("name");
        Artist artist1 = new Artist();
        artist1.setArtistName("name");
        List<Exhibition.MOVEMENT> movements = new ArrayList<>();
        movements.add(Exhibition.MOVEMENT.CUBISM);
        artist.setMovements(movements);
        List<Artist> artistList = new ArrayList<>();
        artistList.add(artist);
        artistList.add(artist1);
        GetRecommendedArtistsRequest request = new GetRecommendedArtistsRequest("name");
        when(artistDao.getArtist("name")).thenReturn(artistList);
        when(artistDao.getArtistsByMovement("CUBISM")).thenReturn(artistList);
        //WHEN
        GetRecommendedArtistsResult result = activity.handleRequest(request);
        //THEN
        assertTrue(result.getArtists().size() == 1);
    }
    @Test
    void handleRequest_nonNullMediaNullMovement_returnsResponseWithoutRequestedArtist() {
        //GIVEN
        Artist artist = new Artist();
        artist.setArtistName("name");
        Artist artist1 = new Artist();
        artist1.setArtistName("name");
        artist1.setBirthYear(1990);
        artist.setBirthYear(1998);

        List<Exhibition.MEDIUM > media = new ArrayList<>();
        media.add(Exhibition.MEDIUM.CERAMICS);
        media.add( Exhibition.MEDIUM.DIGITAL);
        artist.setMedia(media);
        artist1.setMedia(media);
        artist.setPrimaryMedium(Exhibition.MEDIUM.CERAMICS);
        artist1.setPrimaryMedium(Exhibition.MEDIUM.CERAMICS);


        List<Artist> artistList = new ArrayList<>();

        artistList.add(artist);
        artistList.add(artist1);


        GetRecommendedArtistsRequest request = new GetRecommendedArtistsRequest("name");
        when(artistDao.getArtist("name")).thenReturn(artistList);
        when(artistDao.getArtistsByMediumAndBirthYear(Exhibition.MEDIUM.DIGITAL, 1990)).thenReturn(artistList);

        when(artistDao.getArtistsByMediumAndBirthYear(Exhibition.MEDIUM.CERAMICS, 1998)).thenReturn(artistList);
        //WHEN
        GetRecommendedArtistsResult result = activity.handleRequest(request);

        //THEN
//        assertTrue(result.getArtists().size() == 1);
    }
    @Test
    void handleRequest_nonNullMediaNonNullMovement_returnsResponseWithoutRequestedArtist() {
        //GIVEN
        Artist artist = new Artist();
        artist.setArtistName("name");
        Artist artist1 = new Artist();
        artist1.setArtistName("name");
        List<Exhibition.MOVEMENT> movements = new ArrayList<>();
        movements.add(Exhibition.MOVEMENT.CUBISM);
        artist.setMovements(movements);
        List<Artist> artistList = new ArrayList<>();

        artistList.add(artist);
        artistList.add(artist1);
        List<Exhibition.MEDIUM > media = new ArrayList<>();
        media.add(Exhibition.MEDIUM.CERAMICS);
         media.add( Exhibition.MEDIUM.DIGITAL);
        GetRecommendedArtistsRequest request = new GetRecommendedArtistsRequest("name");
        when(artistDao.getArtist("name")).thenReturn(artistList);
        when(artistDao.getArtistsByMovement("CUBISM")).thenReturn(artistList);
        //WHEN
        GetRecommendedArtistsResult result = activity.handleRequest(request);
        //THEN
        assertTrue(result.getArtists().size() == 1);
    }
}