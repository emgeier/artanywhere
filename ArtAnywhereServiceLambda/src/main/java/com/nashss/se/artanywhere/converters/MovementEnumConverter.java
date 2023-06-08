package com.nashss.se.artanywhere.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import java.util.List;
import java.util.stream.Collectors;

public class MovementEnumConverter
        implements DynamoDBTypeConverter<String,  Exhibition.MOVEMENT> {


    @Override
    public String convert(Exhibition.MOVEMENT object) {
        System.out.println( "converting " + object.name());

        return object.name();
    }

    @Override
    public Exhibition.MOVEMENT unconvert(String object) {
        System.out.println("unconverting " + object);

        return Exhibition.MOVEMENT.valueOf(object);
    }
}