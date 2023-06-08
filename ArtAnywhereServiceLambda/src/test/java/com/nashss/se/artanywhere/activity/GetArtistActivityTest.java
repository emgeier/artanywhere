package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetArtistRequest;
import com.nashss.se.artanywhere.dynamodb.ArtistDao;
import com.nashss.se.artanywhere.dynamodb.models.Artist;
import org.checkerframework.checker.units.qual.A;
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
    void handleRequest_validInput_returnsArtistList() {
        GetArtistRequest request = new GetArtistRequest("Chagall");
//        List<Artist> artistList = new ArrayList<>();
//        Artist Kandinsky = new Artist();
//        Kandinsky.setArtistName("Kandinsky");
//        Kandinsky.setBirthCountry("Russia");
//        Kandinsky.
//        artistList.add
 //       when(artistDao.getArtist(request.getArtistName())).thenReturn(artistList)
    }
}