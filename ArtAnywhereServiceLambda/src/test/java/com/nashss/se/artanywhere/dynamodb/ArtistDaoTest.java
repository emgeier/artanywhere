package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.artanywhere.dynamodb.models.Artist;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ArtistNotFoundException;
import com.nashss.se.artanywhere.metrics.MetricsConstants;
import com.nashss.se.artanywhere.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static com.nashss.se.artanywhere.metrics.MetricsConstants.SEARCH_BY_MEDIUM_BIRTHYEAR_ARTIST_NOT_FOUND_COUNT;
import static com.nashss.se.artanywhere.metrics.MetricsConstants.SEARCH_BY_MOVEMENT_ARTIST_NOT_FOUND_COUNT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ArtistDaoTest {
    @Mock
    private DynamoDBMapper mapper;
    @Mock
    private MetricsPublisher metricsPublisher;
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
        } catch (ArtistNotFoundException ex) {}
        //
        verify(mapper).query(eq(Artist.class), any());
    }
    @Test
    void getArtistsByMovement_artistInDatabase_contactsMapper() throws InstantiationException, IllegalAccessException {
        //GIVEN
        Exhibition.MOVEMENT movement = Exhibition.MOVEMENT.IMPRESSIONISM;
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":movement", new AttributeValue().withS(movement.name()));


        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(movements, :movement)")
                .withExpressionAttributeValues(valueMap);


        when(mapper.scan(Artist.class, scanExpression)).thenReturn(null);

        //WHEN
        try {
            artistDao.getArtistsByMovement("IMPRESSIONISM");
        } catch (ArtistNotFoundException ex) {}
        //THEN //ScanExpressions newly made, no equals/hash methods for contents
        verify(mapper).scan(eq(Artist.class), any());

    }
    @Test
    void getArtistsByMovement_artistInDatabase_contactsMetricPublisher() throws InstantiationException, IllegalAccessException {
        //GIVEN
        Exhibition.MOVEMENT movement = Exhibition.MOVEMENT.IMPRESSIONISM;
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":movement", new AttributeValue().withS(movement.name()));


        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(movements, :movement)")
                .withExpressionAttributeValues(valueMap);


        when(mapper.scan(Artist.class, scanExpression)).thenReturn(null);

        //WHEN
        try {
            artistDao.getArtistsByMovement("IMPRESSIONISM");
        } catch (ArtistNotFoundException ex) {}
        //THEN

        verify(metricsPublisher).addMetric(SEARCH_BY_MOVEMENT_ARTIST_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);

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
        Exhibition.MOVEMENT movement = Exhibition.MOVEMENT.IMPRESSIONISM;
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":movement", new AttributeValue().withS(movement.name()));


        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(movements, :movement)")
                .withExpressionAttributeValues(valueMap);


        when(mapper.scan(Artist.class, scanExpression)).thenReturn(null);

        //WHEN

        assertThrows(ArtistNotFoundException.class, () ->  artistDao.getArtistsByMovement("IMPRESSIONISM"));

        //THEN //ScanExpressions newly made, no equals/hash methods for contents
        verify(mapper).scan(eq(Artist.class), any());
    }
    @Test
    void getArtistsByMovement_artistsInDatabase_publishesMetric() {
        //GIVEN
        Exhibition.MOVEMENT movement = Exhibition.MOVEMENT.IMPRESSIONISM;
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":movement", new AttributeValue().withS(movement.name()));


        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(movements, :movement)")
                .withExpressionAttributeValues(valueMap);


        when(mapper.scan(Artist.class, scanExpression)).thenReturn(null);

        //WHEN

        assertThrows(ArtistNotFoundException.class, () ->  artistDao.getArtistsByMovement("IMPRESSIONISM"));

        //THEN

        verify(metricsPublisher).addMetric(SEARCH_BY_MOVEMENT_ARTIST_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);

    }
    @Test
    void getArtistsByMediumAndBirthYear_methodCall_publishesMetricThrowsException() {
        Exhibition.MEDIUM medium = Exhibition.MEDIUM.CERAMICS;
        Integer birthYear = 1990;

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":medium", new AttributeValue().withN(medium.name()));
        valueMap.put(":birthYearEndRange", new AttributeValue().withN(String.valueOf(birthYear + 15)));
        valueMap.put(":birthYearStartRange", new AttributeValue().withN(String.valueOf(birthYear -15)));


        DynamoDBQueryExpression<Artist> queryExpression = new DynamoDBQueryExpression<Artist>()
                .withIndexName(Artist.MEDIUM_INDEX)
                .withKeyConditionExpression("primaryMedium = :medium")
                .withFilterExpression("birthYear < :birthYearEndRange and > :birthYearStartRange")
                .withConsistentRead(false)
                .withExpressionAttributeValues(valueMap);
        when(mapper.query(Artist.class, queryExpression)).thenReturn(null);
        //WHEN
        assertThrows(ArtistNotFoundException.class,
                () -> artistDao.getArtistsByMediumAndBirthYear(medium, birthYear));

        verify(metricsPublisher).addMetric(
                SEARCH_BY_MEDIUM_BIRTHYEAR_ARTIST_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);
    }
}