package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;

import com.nashss.se.artanywhere.metrics.MetricsConstants;
import com.nashss.se.artanywhere.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.LocalDate;
import java.util.*;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.nashss.se.artanywhere.dynamodb.models.Exhibition.MOVEMENT_INDEX;
import static com.nashss.se.artanywhere.metrics.MetricsConstants.*;

@Singleton
public class ExhibitionDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final Logger log = LogManager.getLogger();
    private final MetricsPublisher metricsPublisher;

    @Inject
    public ExhibitionDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
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
            metricsPublisher.addMetric(SEARCH_BY_CITY_EXHIBITION_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);
            throw new ExhibitionNotFoundException(String.format(
                    "No exhibitions in %s found in database.", cityCountry));
        }else {
            metricsPublisher.addMetric(SEARCH_BY_CITY_EXHIBITION_NOT_FOUND_COUNT, 0.0, StandardUnit.Count);
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
            metricsPublisher.addMetric(SEARCH_BY_MOVEMENT_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);
            throw new ExhibitionNotFoundException(String.format(
                    "No %s exhibitions found in database.", movement));
        } else {
            metricsPublisher.addMetric(SEARCH_BY_MOVEMENT_NOT_FOUND_COUNT, 0.0, StandardUnit.Count);
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

        PaginatedScanList<Exhibition> exhibitions = dynamoDBMapper.scan(Exhibition.class, scanExpression);
        if(exhibitions == null || exhibitions.isEmpty()) {
            metricsPublisher.addMetric(SEARCH_BY_DATE_EXHIBITION_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);
            throw new ExhibitionNotFoundException(String.format(
                    "No exhibitions found in database for %s - %s.", startDateRequest, endDateRequest));
        } else {
            metricsPublisher.addMetric(SEARCH_BY_DATE_EXHIBITION_NOT_FOUND_COUNT, 0.0, StandardUnit.Count);
        }
        return exhibitions;
    }

    public List<Exhibition> searchExhibitionsByCityAndMedium(String cityCountry, Exhibition.MEDIUM medium) {
        log.info(medium + " :exhibition dao search by medium and city "+ cityCountry);
        Exhibition targetExhibition = new Exhibition();
        targetExhibition.setCityCountry(cityCountry);

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":medium", new AttributeValue().withS(medium.name()));

        DynamoDBQueryExpression<Exhibition> queryExpression = new DynamoDBQueryExpression<Exhibition>()
                .withHashKeyValues(targetExhibition)
                .withFilterExpression("contains(media,:medium)")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Exhibition> exhibitionQueryList = dynamoDBMapper.query(Exhibition.class, queryExpression);

        if(exhibitionQueryList == null || exhibitionQueryList.isEmpty()) {
            metricsPublisher.addMetric(SEARCH_BY_CITY_MEDIUM_EXHIBITION_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);
            throw new ExhibitionNotFoundException(String.format(
                    "No exhibitions in %s found in database.", cityCountry));
        } else {
            metricsPublisher.addMetric(SEARCH_BY_CITY_MEDIUM_EXHIBITION_NOT_FOUND_COUNT, 0.0, StandardUnit.Count);
        }
        return exhibitionQueryList;
    }
    public List<Exhibition> searchExhibitionsByMedium(Exhibition.MEDIUM medium) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":medium", new AttributeValue().withS(medium.name()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(media, :medium)")
                .withExpressionAttributeValues(valueMap);

        PaginatedScanList<Exhibition> exhibitions = dynamoDBMapper.scan(Exhibition.class, scanExpression);
        if(exhibitions == null || exhibitions.isEmpty()) {
            metricsPublisher.addMetric(SEARCH_BY_MEDIUM_EXHIBITION_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);
            throw new ExhibitionNotFoundException(String.format("No %s exhibitions found in database.", medium));
        } else {
            metricsPublisher.addMetric(MetricsConstants.SEARCH_BY_MEDIUM_EXHIBITION_NOT_FOUND_COUNT, 0.0,
                    StandardUnit.Count);
        }
        return exhibitions;
    }
    public List<Exhibition> searchExhibitionsByArtist(String artistName) {

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":artist", new AttributeValue().withS(artistName));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(artists, :artist)")
                .withExpressionAttributeValues(valueMap);

        PaginatedScanList<Exhibition> exhibitions = dynamoDBMapper.scan(Exhibition.class, scanExpression);
        if(exhibitions == null || exhibitions.isEmpty()) {
            metricsPublisher.addMetric(MetricsConstants.SEARCH_BY_ARTIST_EXHIBITION_NOT_FOUND_COUNT, 1.0,
                    StandardUnit.Count);
            throw new ExhibitionNotFoundException(String.format(
                    "No exhibitions found in database for %s.", artistName));
        } else {
            metricsPublisher.addMetric(MetricsConstants.SEARCH_BY_ARTIST_EXHIBITION_NOT_FOUND_COUNT, 0.0,
                    StandardUnit.Count);
        }
        return exhibitions;
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
            metricsPublisher.addMetric(SEARCH_BY_CITY_DATE_EXHIBITION_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);
            throw new ExhibitionNotFoundException(String.format(
                    "No exhibitions in %s found in database %s - %s.", city, startDate, endDate));
        } else {
            metricsPublisher.addMetric(SEARCH_BY_CITY_DATE_EXHIBITION_NOT_FOUND_COUNT, 0.0, StandardUnit.Count);
        }
        return exhibitionQueryList;

    }
    public List<Exhibition> getRecommendedExhibitions(String cityCountry, String exhibitionName) {
        log.info("ExhibitionDao.getRecommendedExhibitions initiated with city: {} and exhibition: {}.",
                cityCountry, exhibitionName);
        Set<Exhibition> recommendedExhibitions = new HashSet<>();
        Exhibition targetExhibition = getExhibition(cityCountry, exhibitionName);
        List<Exhibition> similarExhibitions = new ArrayList<>();
//Search by city and medium, all media of exhibition added to wishlist
        if(targetExhibition.getMedia() != null) {
            for (int i = 0; i < targetExhibition.getMedia().size(); i++) {

                try {
                    similarExhibitions = searchExhibitionsByCityAndMedium(cityCountry,
                            targetExhibition.getMedia().get(i));

                } catch (ExhibitionNotFoundException ex) {
                    log.info("No exhibitions found like {} by city and medium {}.",
                            targetExhibition, targetExhibition.getMedia().get(i));
                    continue;
                }
                recommendedExhibitions.addAll(similarExhibitions);


            }
        }
//Search by city and dates of exhibition added to wishlist

        if (targetExhibition.getEndDate() != null && targetExhibition.getStartDate() != null) {
            try {
                similarExhibitions = searchExhibitionsByCityAndDate(cityCountry, LocalDate.now(),
                        targetExhibition.getEndDate());


            } catch (ExhibitionNotFoundException ex) {
                log.info("No exhibitions found like {} by city and date.", targetExhibition);

            }
            recommendedExhibitions.addAll(similarExhibitions);
        }
//Search by city and movement of exhibition added to wishlist

        if (targetExhibition.getMovement() != null) {
            try {
                similarExhibitions = searchExhibitionsByMovement(targetExhibition.getMovement());
                log.info("{} exhibitions found", targetExhibition.getMovement());

            } catch (ExhibitionNotFoundException ex) {
                log.info("No exhibitions found like {} by movement {}.", targetExhibition,
                        targetExhibition.getMovement());

            }
            recommendedExhibitions.addAll(similarExhibitions);
        }
//Search by medium, all media of exhibition added to wishlist
        if (recommendedExhibitions.size()<2 && targetExhibition.getMedia() != null) {
            for (Exhibition.MEDIUM medium: targetExhibition.getMedia() ){
                try {
                    similarExhibitions = searchExhibitionsByMedium(medium);
                    log.info("{} exhibitions found", medium);

                } catch (ExhibitionNotFoundException ex) {
                    log.info("No {} exhibitions found like {}.", medium,
                           targetExhibition.getExhibitionName());

                }
                recommendedExhibitions.addAll(similarExhibitions);

            }
        }

            recommendedExhibitions.remove(targetExhibition);

        if (recommendedExhibitions.isEmpty()) {
            metricsPublisher.addMetric(RECOMMEND_EXHIBITIONS_EXHIBITION_NOT_FOUND_COUNT, 1.0, StandardUnit.Count);

        } else {
            metricsPublisher.addMetric(RECOMMEND_EXHIBITIONS_EXHIBITION_NOT_FOUND_COUNT, 0.0, StandardUnit.Count);
        }
        return new ArrayList<>(recommendedExhibitions);
    }
}
