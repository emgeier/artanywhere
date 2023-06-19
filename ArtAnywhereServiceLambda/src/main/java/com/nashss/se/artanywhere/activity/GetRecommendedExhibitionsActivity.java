package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetExhibitionRequest;
import com.nashss.se.artanywhere.activity.requests.GetRecommendedExhibitionsRequest;
import com.nashss.se.artanywhere.activity.results.GetExhibitionResult;
import com.nashss.se.artanywhere.activity.results.GetRecommendedExhibitionsResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ExhibitionDao;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetRecommendedExhibitionsActivity {
    private final Logger log = LogManager.getLogger();
    private final ExhibitionDao exhibitionDao;
    @Inject
    public GetRecommendedExhibitionsActivity(ExhibitionDao exhibitionDao) {
        this.exhibitionDao = exhibitionDao;
    }



    public GetRecommendedExhibitionsResult handleRequest(GetRecommendedExhibitionsRequest request) {
        log.info("GetRecommendedExhibitionsRequest received {}.", request);
        List<Exhibition> exhibitions;
System.out.println(request.getCityCountry() +" Activity " + request.getExhibitionName());
        try {
            exhibitions = exhibitionDao.getRecommendedExhibitions(request.getCityCountry(), request.getExhibitionName());
        } catch (ExhibitionNotFoundException ex) {
            log.error("Exhibitions similar to exhibition named {} in {} not found in database.",
                    request.getExhibitionName(), request.getCityCountry());
            throw new ExhibitionNotFoundException(ex.getMessage(), ex.getCause());
        }

        return GetRecommendedExhibitionsResult.builder()
                .withExhibitions(new ModelConverter()
                        .toExhibitionModelListOfRecommendations(exhibitions, request.getExhibitionName()))
                .build();
    }
}
