package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetExhibitionRequest;
import com.nashss.se.artanywhere.activity.results.GetExhibitionResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetExhibitionActivity {
    private final Logger log = LogManager.getLogger();
    private final ExhibitionDao exhibitionDao;
    @Inject
    public GetExhibitionActivity(ExhibitionDao exhibitionDao) {
        this.exhibitionDao = exhibitionDao;
    }
    public GetExhibitionResult handleRequest(GetExhibitionRequest request) {
        log.info("GetExhibitionRequest received {}.", request);
        Exhibition exhibition;
        try {
            exhibition = exhibitionDao.getExhibition(request.getCityCountry(), request.getExhibitionName());
        } catch (ExhibitionNotFoundException ex) {
            log.error("Exhibition named {} in {} is not found in database.",
                    request.getExhibitionName(), request.getCityCountry());
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }
        return GetExhibitionResult.builder()
                .withExhibition(new ModelConverter().toExhibitionModel(exhibition))
                .build();
    }
}
