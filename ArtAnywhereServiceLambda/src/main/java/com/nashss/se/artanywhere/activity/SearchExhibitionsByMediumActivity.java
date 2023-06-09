package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByMediumRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByMediumResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SearchExhibitionsByMediumActivity {
    private final Logger log = LogManager.getLogger();
    private final ExhibitionDao exhibitionDao;

    @Inject
    public SearchExhibitionsByMediumActivity(ExhibitionDao exhibitionDao) {
        this.exhibitionDao = exhibitionDao;
    }
    public SearchExhibitionsByMediumResult handleRequest(SearchExhibitionsByMediumRequest request) {
        log.info("SearchExhibitionByMediumRequest {} received.", request);
System.out.println(request.getMedium());
        List<Exhibition> searchResults;
        try {
            System.out.println(Exhibition.MEDIUM.valueOf(request.getMedium()));
            searchResults = exhibitionDao.searchExhibitionsByMedium(Exhibition.MEDIUM.valueOf(request.getMedium()));
        } catch (ExhibitionNotFoundException ex) {
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }
        return SearchExhibitionsByMediumResult.builder()
                .withExhibitions(new ModelConverter().toExhibitionModelList(searchResults))
                .build();
    }
}
