package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExhibitionDao {
    private final DynamoDBMapper dynamoDBMapper;
    @Inject
    public ExhibitionDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }
    public Exhibition getExhibition(String cityCountry, String name) {
        Exhibition exhibition = this.dynamoDBMapper.load(Exhibition.class, cityCountry, name);
        if (exhibition == null) {
            throw new ExhibitionNotFoundException(String.format("Exhibition named %s in %s not found in database.", name, cityCountry));
        }
        return exhibition;
    }
}
