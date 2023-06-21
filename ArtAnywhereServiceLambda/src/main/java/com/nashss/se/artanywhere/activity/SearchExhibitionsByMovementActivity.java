package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityRequest;
import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByMovementRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityResult;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByMovementResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class SearchExhibitionsByMovementActivity {
    private final Logger log = LogManager.getLogger();
    private final ExhibitionDao exhibitionDao;
    @Inject
    public SearchExhibitionsByMovementActivity(ExhibitionDao exhibitionDao) {
        this.exhibitionDao = exhibitionDao;
    }
    public SearchExhibitionsByMovementResult handleRequest(SearchExhibitionsByMovementRequest request) {
        log.info("SearchExhibitionsByMovementRequest received {}.", request);
        List<Exhibition> searchResults;
        try{
            Exhibition.MOVEMENT movement = Exhibition.MOVEMENT.valueOf(request.getMovement());
            searchResults = exhibitionDao.searchExhibitionsByMovement(movement);
        } catch (ExhibitionNotFoundException ex) {
            log.error("No exhibitions found {}.", request.getMovement());
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }
        return SearchExhibitionsByMovementResult.builder()
                .withExhibitions(new ModelConverter().toExhibitionModelList(searchResults))
                .build();
    }
}
