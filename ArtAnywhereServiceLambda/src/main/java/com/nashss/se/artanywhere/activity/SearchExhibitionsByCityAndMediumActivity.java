package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityAndMediumRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityAndMediumResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class SearchExhibitionsByCityAndMediumActivity {
    private final Logger log = LogManager.getLogger();
    private final ExhibitionDao exhibitionDao;
    @Inject
    public SearchExhibitionsByCityAndMediumActivity(ExhibitionDao exhibitionDao) {
        this.exhibitionDao = exhibitionDao;
    }
    public SearchExhibitionsByCityAndMediumResult handleRequest(SearchExhibitionsByCityAndMediumRequest request) {
        log.info("SearchExhibitionsByCityAndMediumRequest received {}.", request);
        List<Exhibition> searchResults;
        try{
            System.out.println("activity: " + request.toString());
            System.out.println(Exhibition.MEDIUM.valueOf(request.getMedium()));
            searchResults = exhibitionDao.searchExhibitionsByCityAndMedium(request.getCityCountry(), Exhibition.MEDIUM.valueOf(request.getMedium()));
        } catch (ExhibitionNotFoundException ex) {
            log.error("No {} exhibitions found in {}.", request.getMedium(), request.getCityCountry());
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }
        return SearchExhibitionsByCityAndMediumResult.builder()
                .withExhibitions(new ModelConverter().toExhibitionModelList(searchResults))
                .build();
    }
}
