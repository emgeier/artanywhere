package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.nashss.se.artanywhere.dynamodb.models.Exhibition.MOVEMENT_INDEX;

@Singleton
public class ExhibitionDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final Logger log = LogManager.getLogger();
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

    public List<Exhibition> searchExhibitionsByDate(LocalDate startDateRequest, LocalDate endDateRequest) {
        DateConverter dateConverter = new DateConverter();
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":startDate", new AttributeValue().withS(dateConverter.convert(startDateRequest)));
        valueMap.put(":endDate", new AttributeValue().withS(dateConverter.convert(endDateRequest)));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("startDate <= :endDate and endDate >= :startDate")
                .withExpressionAttributeValues(valueMap);
        return dynamoDBMapper.scan(Exhibition.class, scanExpression);
    }

    public List<Exhibition> searchExhibitionsByCityAndMedium(String cityCountry, Exhibition.MEDIUM medium) {
System.out.println(medium + " :exhibition dao search by medium and city "+ cityCountry);
        Exhibition targetExhibition = new Exhibition();
        targetExhibition.setCityCountry(cityCountry);

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":medium", new AttributeValue().withS(medium.name()));
System.out.println(valueMap);
        DynamoDBQueryExpression<Exhibition> queryExpression = new DynamoDBQueryExpression<Exhibition>()
                .withHashKeyValues(targetExhibition)
                .withFilterExpression("contains(media,:medium)")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Exhibition> exhibitionQueryList = dynamoDBMapper.query(Exhibition.class, queryExpression);
System.out.println(exhibitionQueryList);
        if(exhibitionQueryList == null || exhibitionQueryList.isEmpty()) {
            throw new ExhibitionNotFoundException(String.format(
                    "No exhibitions in %s found in database.", cityCountry));
        }
        return exhibitionQueryList;
    }
    public List<Exhibition> searchExhibitionsByMedium(Exhibition.MEDIUM medium) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":medium", new AttributeValue().withS(medium.name()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(media, :medium)")
                .withExpressionAttributeValues(valueMap);

        return dynamoDBMapper.scan(Exhibition.class, scanExpression);
    }
    public List<Exhibition> searchExhibitionsByArtist(String artistName) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":artist", new AttributeValue().withS(artistName));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(artists, :artist)")
                .withExpressionAttributeValues(valueMap);

        return dynamoDBMapper.scan(Exhibition.class, scanExpression);
    }

    public List<Exhibition> searchExhibitionsByCityAndDate(String city, LocalDate startDate, LocalDate endDate) {
        Exhibition targetExhibition = new Exhibition();
        targetExhibition.setCityCountry(city);
        DateConverter dateConverter = new DateConverter();
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":startDate", new AttributeValue().withS(dateConverter.convert(startDate)));
        valueMap.put(":endDate", new AttributeValue().withS(dateConverter.convert(endDate)));
        log.info("Value map for DynamoDB query created {}.", valueMap);
        DynamoDBQueryExpression<Exhibition> queryExpression = new DynamoDBQueryExpression<Exhibition>()
                .withHashKeyValues(targetExhibition)
                .withFilterExpression("startDate <= :endDate and endDate >= :startDate")
                .withExpressionAttributeValues(valueMap);
        log.info("DynamoDB query created {}.", queryExpression);
        PaginatedQueryList<Exhibition> exhibitionQueryList = dynamoDBMapper.query(Exhibition.class, queryExpression);
        if(exhibitionQueryList == null || exhibitionQueryList.isEmpty()) {
            log.info("ExhibitionNotFoundException -- ExhibitionsByCityAndDate query {} results null.",
                    queryExpression);
            throw new ExhibitionNotFoundException(String.format(
                    "No exhibitions in %s found in database %s - %s.", city, startDate, endDate));
        }
        return exhibitionQueryList;

    }
    public List<Exhibition> getRecommendedExhibitions(String cityCountry, String exhibitionName) {

        List<Exhibition> recommendedExhibitions = new ArrayList<>();
        Exhibition targetExhibition = getExhibition(cityCountry, exhibitionName);
        List<Exhibition> similarExhibitions = new ArrayList<>();
        for(int i = 0; i < targetExhibition.getMedia().size()-1; i++) {
            System.out.println(i);
            try {
                similarExhibitions = searchExhibitionsByCityAndMedium(cityCountry,
                        targetExhibition.getMedia().get(i));
            } catch (ExhibitionNotFoundException ex) {
                log.info("No exhibitions found like {} by city and medium {}.",
                        targetExhibition, targetExhibition.getMedia().get(i));
                continue;
            }
            recommendedExhibitions.addAll(similarExhibitions);
System.out.println("recommended from Media" + similarExhibitions);
        }
        try {
            similarExhibitions = searchExhibitionsByCityAndDate(cityCountry, LocalDate.now(),
                    targetExhibition.getEndDate());
        } catch (ExhibitionNotFoundException ex) {
            log.info("No exhibitions found like {} by city and date.", targetExhibition);
        }
        recommendedExhibitions.addAll(similarExhibitions);
        try {
            similarExhibitions = searchExhibitionsByMovement(targetExhibition.getMovement());
        } catch (ExhibitionNotFoundException ex) {
            log.info("No exhibitions found like {} by movement {}.", targetExhibition,
                    targetExhibition.getMovement());
        }
        recommendedExhibitions.addAll(similarExhibitions);
System.out.println(similarExhibitions);
        return recommendedExhibitions;
    }
}
