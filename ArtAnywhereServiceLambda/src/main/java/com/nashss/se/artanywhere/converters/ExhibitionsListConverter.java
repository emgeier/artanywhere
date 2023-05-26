package com.nashss.se.artanywhere.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import java.util.LinkedList;
import java.util.List;
/**
 * This class converts the list of Exhibitions into the Json version for DynamoDB
 */
public class ExhibitionsListConverter implements DynamoDBTypeConverter<String, List>{
    private static final Gson GSON = new Gson();

    @Override
    public String convert(List listToBeConverted) {
        return GSON.toJson(listToBeConverted);
    }

    @Override
    public List unconvert(String object) {

        // need to provide the type parameter of the list to convert correctly
        return GSON.fromJson(object, new TypeToken<List<Exhibition>>() { } .getType());

    }

}


