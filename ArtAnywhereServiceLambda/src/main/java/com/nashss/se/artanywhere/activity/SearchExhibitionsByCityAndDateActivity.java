package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityAndDateRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityAndDateResult;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityAndMediumResult;
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

public class SearchExhibitionsByCityAndDateActivity {
    private final Logger log = LogManager.getLogger();
    private final ExhibitionDao exhibitionDao;
    @Inject
    public SearchExhibitionsByCityAndDateActivity(ExhibitionDao exhibitionDao) {
        this.exhibitionDao = exhibitionDao;
    }
    public SearchExhibitionsByCityAndDateResult handleRequest(SearchExhibitionsByCityAndDateRequest request) {
        log.info("SearchExhibitionsByCityAndDateRequest received {}.", request);
        List<Exhibition> searchResults;
        DateConverter dateConverter = new DateConverter();
        try{
            LocalDate startDateRequest = dateConverter.convertFromNumberString(request.getStartDate());

            LocalDate endDateRequest = dateConverter.convertFromNumberString(request.getEndDate());

            log.info("SearchExhibitionsByCityAndDateActivity has received request for City: {}.",
                    request.getCityCountry());
            log.info("SearchExhibitionsByCityAndDateRequest received for dates {} - {}.",
                    startDateRequest, endDateRequest);

            searchResults = exhibitionDao.searchExhibitionsByCityAndDate(request.getCityCountry(), startDateRequest, endDateRequest);
        } catch (ExhibitionNotFoundException ex) {
            log.error("No exhibitions found in {} for {} - {}.", request.getCityCountry(),
                    request.getStartDate(), request.getEndDate());
            throw new ExhibitionNotFoundException(String.format("No exhibitions found in %s for %s - %s.",
                    request.getCityCountry(), request.getStartDate(), request.getEndDate()), ex.getCause());
        }
        return SearchExhibitionsByCityAndDateResult.builder()
                .withExhibitions(new ModelConverter().toExhibitionModelList(searchResults))
                .build();

    }
}
