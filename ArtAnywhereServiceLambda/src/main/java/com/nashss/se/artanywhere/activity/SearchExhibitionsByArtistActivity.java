package com.nashss.se.artanywhere.activity;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByArtistRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByArtistResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;

import com.nashss.se.artanywhere.metrics.MetricsConstants;
import com.nashss.se.artanywhere.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class SearchExhibitionsByArtistActivity {
    private final Logger log = LogManager.getLogger();
    private final MetricsPublisher metricsPublisher;
    private final ExhibitionDao exhibitionDao;
    @Inject
    public SearchExhibitionsByArtistActivity(MetricsPublisher metricsPublisher, ExhibitionDao exhibitionDao) {
        this.metricsPublisher = metricsPublisher;
        this.exhibitionDao = exhibitionDao;
    }
    public SearchExhibitionsByArtistResult handleRequest(SearchExhibitionsByArtistRequest request) {

        log.info("SearchExhibitionsByArtistRequest received {}.", request);
        List<Exhibition> searchResults;
        try{
            searchResults = exhibitionDao.searchExhibitionsByArtist(request.getArtistName());
            metricsPublisher.addMetric(MetricsConstants.SEARCH_BY_ARTIST_EXHIBITION_NOT_FOUND_COUNT, 0.0,
                    StandardUnit.Count);
        } catch (ExhibitionNotFoundException ex) {
            log.error("No exhibitions for artist {} found.", request.getArtistName());
            metricsPublisher.addMetric(MetricsConstants.SEARCH_BY_ARTIST_EXHIBITION_NOT_FOUND_COUNT, 1.0,
                StandardUnit.Count);
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }
        return SearchExhibitionsByArtistResult.builder()
                .withExhibitions(new ModelConverter().toExhibitionModelList(searchResults))
                .build();
    }
}
