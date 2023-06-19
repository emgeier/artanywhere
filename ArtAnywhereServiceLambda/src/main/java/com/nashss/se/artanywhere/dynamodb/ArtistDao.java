package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.artanywhere.dynamodb.models.Artist;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ArtistNotFoundException;
import com.nashss.se.artanywhere.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nashss.se.artanywhere.metrics.MetricsConstants.SEARCH_BY_MEDIUM_BIRTHYEAR_ARTIST_NOT_FOUND_COUNT;
import static com.nashss.se.artanywhere.metrics.MetricsConstants.SEARCH_BY_MOVEMENT_ARTIST_NOT_FOUND_COUNT;

@Singleton
public class ArtistDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final Logger log = LogManager.getLogger();
    private final MetricsPublisher metricsPublisher;
    @Inject
    public ArtistDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }
    public List<Artist> getArtist(String artistName) {
        Artist targetArtist = new Artist();
        targetArtist.setArtistName(artistName);
        DynamoDBQueryExpression<Artist> queryExpression = new DynamoDBQueryExpression<Artist>()
                .withHashKeyValues(targetArtist);
        PaginatedQueryList<Artist> artistPaginatedQueryList = dynamoDBMapper.query(Artist.class, queryExpression);
        if(artistPaginatedQueryList == null || artistPaginatedQueryList.isEmpty()) {
            throw new ArtistNotFoundException(String.format(
                    " %s not found in database.", artistName));
        }
        return artistPaginatedQueryList;
    }
    public List<Artist> getArtistsByMovement(String movement) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":movement", new AttributeValue().withS(movement));


        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(movements, :movement)")
                .withExpressionAttributeValues(valueMap);
        PaginatedScanList<Artist> artistList = dynamoDBMapper.scan(Artist.class, scanExpression);
        if(artistList == null || artistList.isEmpty()) {
            metricsPublisher.addMetric(SEARCH_BY_MOVEMENT_ARTIST_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);
            throw new ArtistNotFoundException(String.format(
                    " %s not found in database.", movement));
        } else {
            metricsPublisher.addMetric(SEARCH_BY_MOVEMENT_ARTIST_NOT_FOUND_COUNT, 0.0, StandardUnit.Count);
        }
        return artistList;
    }
    public List<Artist> getArtistsByMediumAndBirthYear(Exhibition.MEDIUM medium, Integer birthYear) {
System.out.println("1*");
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":medium", new AttributeValue().withS(medium.name()));
        valueMap.put(":birthYearEndRange", new AttributeValue().withN(String.valueOf(birthYear + 15)));
        valueMap.put(":birthYearStartRange", new AttributeValue().withN(String.valueOf(birthYear -15)));
System.out.println("2*");

        DynamoDBQueryExpression<Artist> queryExpression = new DynamoDBQueryExpression<Artist>()
                .withIndexName(Artist.MEDIUM_INDEX)
                .withKeyConditionExpression("primaryMedium = :medium and birthYear between :birthYearStartRange and :birthYearEndRange")
                .withConsistentRead(false)
                .withExpressionAttributeValues(valueMap);
System.out.println("3*");
        PaginatedQueryList<Artist> exhibitionQueryList = dynamoDBMapper.query(Artist.class, queryExpression);
System.out.println("4*");
        if(exhibitionQueryList == null || exhibitionQueryList.isEmpty()) {
            log.error("No {} artist around {} found.", medium, birthYear);
            metricsPublisher.addMetric(SEARCH_BY_MEDIUM_BIRTHYEAR_ARTIST_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);
            throw new ArtistNotFoundException(String.format(
                "No %s artist around %d found in database.", medium, birthYear));
        } else {
            metricsPublisher.addMetric(SEARCH_BY_MEDIUM_BIRTHYEAR_ARTIST_NOT_FOUND_COUNT, 0.0, StandardUnit.Count);
        }
System.out.println("5*");
        return exhibitionQueryList;
    }



}



