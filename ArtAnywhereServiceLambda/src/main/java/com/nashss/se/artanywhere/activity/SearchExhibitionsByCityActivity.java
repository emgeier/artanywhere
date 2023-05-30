package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.inject.Inject;

public class SearchExhibitionsByCityActivity {
    private final Logger log = LogManager.getLogger();
    private final ExhibitionDao exhibitionDao;
    @Inject
    public SearchExhibitionsByCityActivity(ExhibitionDao exhibitionDao) {
        this.exhibitionDao = exhibitionDao;
    }
    public SearchExhibitionsByCityResult handleRequest(SearchExhibitionsByCityRequest request) {
        log.info("SearchExhibitionsByCityRequest received {}.", request);
        List<Exhibition> searchResults;
        try{
            searchResults = exhibitionDao.searchExhibitionsByCity(request.getCityCountry());
        } catch (ExhibitionNotFoundException ex) {
            log.error("No exhibitions found in {}.", request.getCityCountry());
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }
        return SearchExhibitionsByCityResult.builder()
                .withExhibitions(new ModelConverter().toExhibitionModelList(searchResults))
                .build();
    }
}
