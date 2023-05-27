package com.nashss.se.artanywhere.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import java.util.List;
import java.util.stream.Collectors;

public class MediaListConverter implements DynamoDBTypeConverter<List<String>, List<Exhibition.MEDIUM>> {

    @Override
    public List<String> convert(List<Exhibition.MEDIUM> objects) {
       return objects.stream().map(Exhibition.MEDIUM::name).collect(Collectors.toList());
    }

    @Override
    public List<Exhibition.MEDIUM> unconvert(List<String> objects) {
        return objects.stream().map(Exhibition.MEDIUM::valueOf).collect(Collectors.toList());
    }

}
