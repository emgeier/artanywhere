package com.nashss.se.artanywhere.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import java.util.List;
import java.util.stream.Collectors;

public class MovementListConverter implements DynamoDBTypeConverter<List<String>, List<Exhibition.MOVEMENT>> {
    @Override
    public List<String> convert(List<Exhibition.MOVEMENT> objects) {
        return objects.stream().map(Exhibition.MOVEMENT::name).collect(Collectors.toList());
    }

    @Override
    public List<Exhibition.MOVEMENT> unconvert(List<String> objects) {
        return objects.stream().map(Exhibition.MOVEMENT::valueOf).collect(Collectors.toList());
    }
}
