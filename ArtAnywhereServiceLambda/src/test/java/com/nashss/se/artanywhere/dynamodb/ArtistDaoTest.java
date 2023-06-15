package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
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
    void getArtist_artistInDatabase_returnsArtist() {
    }
    @Test
    void getArtist_artistNotInDatabase_throwsException() {
    }
    @Test
    void getArtistsByMovement_artistsInDatabase_throwsException/() {
    }
}