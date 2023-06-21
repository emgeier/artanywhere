package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByDateRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByDateResult;
import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class SearchExhibitionsByDateActivity {
    private final Logger log = LogManager.getLogger();
    private final ExhibitionDao exhibitionDao;
    @Inject
    public SearchExhibitionsByDateActivity(ExhibitionDao exhibitionDao) {
        this.exhibitionDao = exhibitionDao;
    }
    public SearchExhibitionsByDateResult handleRequest(SearchExhibitionsByDateRequest request) {
        log.info("SearchExhibitionsByDateRequest received {}.", request);
        List<Exhibition> searchResults;
        DateConverter dateConverter = new DateConverter();
        try{
            LocalDate startDateRequest = dateConverter.convertFromNumberString(request.getStartDate());
            LocalDate endDateRequest = dateConverter.convertFromNumberString(request.getEndDate());
            searchResults =exhibitionDao.searchExhibitionsByDate(startDateRequest, endDateRequest);
        } catch (ExhibitionNotFoundException ex) {
            log.error("No exhibitions found {} - {}.", request.getStartDate(), request.getEndDate());
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }
        return SearchExhibitionsByDateResult.builder()
                .withExhibitions(new ModelConverter().toExhibitionModelList(searchResults))
                .build();
    }
}
