package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetArtistRequest;
import com.nashss.se.artanywhere.activity.results.GetArtistResult;
import com.nashss.se.artanywhere.dynamodb.ArtistDao;
import com.nashss.se.artanywhere.dynamodb.models.Artist;
import com.nashss.se.artanywhere.exceptions.ArtistNotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GetArtistActivityTest {
    @Mock
    private ArtistDao artistDao;
    @InjectMocks
    private GetArtistActivity activity;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validInput_returnsArtist() {
        //GIVEN
        GetArtistRequest request = new GetArtistRequest("Chagall");
        List<Artist> artistList = new ArrayList<>();
        Artist Kandinsky = new Artist();
        Kandinsky.setArtistName("Kandinsky");
        Kandinsky.setBirthCountry("Russia");
        Artist Chagall = new Artist();
        Chagall.setArtistName("Chagall");
        Chagall.setBirthCountry("Russia");

        artistList.add(Chagall);
        when(artistDao.getArtist(request.getArtistName())).thenReturn(artistList);
        //WHEN
        GetArtistResult result = activity.handleRequest(request);
        //THEN
        assertTrue(result.getArtist().get(0).getArtistName().equals("Chagall"));
    }
    @Test
    void handleRequest_daoThrowsArtistNotFoundException_propagatesException() {
        //GIVEN
        GetArtistRequest request = new GetArtistRequest("Chagall");

        when(artistDao.getArtist(request.getArtistName())).thenThrow(ArtistNotFoundException.class);
        //WHEN//THEN
        assertThrows(ArtistNotFoundException.class, ()->activity.handleRequest(request));
    }
}