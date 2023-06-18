package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.artanywhere.dynamodb.models.Artist;
import com.nashss.se.artanywhere.exceptions.ArtistNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ArtistDaoTest {
    @Mock
    private DynamoDBMapper mapper;
    @InjectMocks
    private ArtistDao artistDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getArtist_artistInDatabase_contactsMapper() {
        //GIVEN
        Artist targetArtist = new Artist();
        targetArtist.setArtistName("Degas");
        DynamoDBQueryExpression<Artist> queryExpression = new DynamoDBQueryExpression<Artist>()
                .withHashKeyValues(targetArtist);

        when(mapper.query(Artist.class, queryExpression)).thenReturn(null);

        //WHEN
        try {
            artistDao.getArtist("Degas");
        } catch (RuntimeException ex) {}
        //
        verify(mapper).query(eq(Artist.class), any());
    }
    @Test
    void getArtistsByMovement_artistInDatabase_contactsMapper() {
//        //GIVEN
//        Artist targetArtist = new Artist();
//        targetArtist.setArtistName("Degas");
//        DynamoDBQueryExpression<Artist> queryExpression = new DynamoDBQueryExpression<Artist>()
//                .withHashKeyValues(targetArtist);
//
//        when(mapper.query(Artist.class, queryExpression)).thenReturn(null);
//
//        //WHEN
//        try {
//            artistDao.getArtistsByMovement("IMPRESSIONISM");
//        } catch (RuntimeException ex) {}
//        //
//        verify(mapper).query(eq(Artist.class), any());
    }
    @Test
    void getArtist_artistNotInDatabase_throwsException() {
        //GIVEN
        Artist targetArtist = new Artist();
        targetArtist.setArtistName("Degas");
        DynamoDBQueryExpression<Artist> queryExpression = new DynamoDBQueryExpression<Artist>()
                .withHashKeyValues(targetArtist);

        when(mapper.query(Artist.class, queryExpression)).thenReturn(null);

        //WHEN/THEN
        assertThrows(ArtistNotFoundException.class, () -> artistDao.getArtist("Degas"));

    }
    @Test
    void getArtistsByMovement_artistsInDatabase_throwsException() {
        //GIVEN
        Artist targetArtist = new Artist();
        targetArtist.setArtistName("Degas");
        DynamoDBQueryExpression<Artist> queryExpression = new DynamoDBQueryExpression<Artist>()
                .withHashKeyValues(targetArtist);

        when(mapper.query(Artist.class, queryExpression)).thenReturn(null);

        //WHEN/THEN
        assertThrows(ArtistNotFoundException.class, () -> artistDao.getArtistsByMovement("Degas"));

    }
}