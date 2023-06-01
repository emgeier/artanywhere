package com.nashss.se.artanywhere.dynamodb;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.print.attribute.Attribute;

import static com.nashss.se.artanywhere.dynamodb.models.Exhibition.MOVEMENT_INDEX;

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
            throw new ExhibitionNotFoundException(String.format(
                    "Exhibition named %s in %s not found in database.", name, cityCountry));
        }

        return exhibition;
    }
    public List<Exhibition> searchExhibitionsByCity(String cityCountry) {
        Exhibition targetExhibition = new Exhibition();
        targetExhibition.setCityCountry(cityCountry);
        DynamoDBQueryExpression<Exhibition> queryExpression = new DynamoDBQueryExpression<Exhibition>()
                .withHashKeyValues(targetExhibition);
        PaginatedQueryList<Exhibition> exhibitionQueryList = dynamoDBMapper.query(Exhibition.class, queryExpression);
        if(exhibitionQueryList == null || exhibitionQueryList.isEmpty()) {
            throw new ExhibitionNotFoundException(String.format(
                    "No exhibitions in %s found in database.", cityCountry));
        }
        return exhibitionQueryList;
    }
    public List<Exhibition> searchExhibitionsByMovement(Exhibition.MOVEMENT movement) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":movement", new AttributeValue().withS(movement.name()));

        DynamoDBQueryExpression<Exhibition> queryExpression = new DynamoDBQueryExpression<Exhibition>()
                .withIndexName(MOVEMENT_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("movement = :movement")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Exhibition> exhibitionQueryList = dynamoDBMapper.query(Exhibition.class, queryExpression);
        if(exhibitionQueryList == null || exhibitionQueryList.isEmpty()) {
            throw new ExhibitionNotFoundException(String.format(
                    "No %s exhibitions found in database.", movement));
        }
        return exhibitionQueryList;
    }
}
