package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.artanywhere.dynamodb.models.Artist;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ArtistNotFoundException;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import org.checkerframework.checker.units.qual.A;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Singleton
public class ArtistDao {
    private final DynamoDBMapper dynamoDBMapper;
    @Inject
    public ArtistDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
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
            throw new ArtistNotFoundException(String.format(
                    " %s not found in database.", movement));
        }
        return artistList;
    }
}



