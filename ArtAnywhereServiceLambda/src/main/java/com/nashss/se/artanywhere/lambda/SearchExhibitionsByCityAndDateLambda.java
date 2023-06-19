package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityAndDateRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityAndDateResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SearchExhibitionsByCityAndDateLambda extends LambdaActivityRunner<SearchExhibitionsByCityAndDateRequest,
        SearchExhibitionsByCityAndDateResult> implements RequestHandler<LambdaRequest<SearchExhibitionsByCityAndDateRequest>,
        LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchExhibitionsByCityAndDateRequest> input, Context context) {
        log.info("SearchExhibitionsByCityAndDateLambdaRequest received {}", input.getPath());
        return super.runActivity(
           () -> {
               log.info("SearchExhibitionsByCityAndDateLambdaRequest created from user request");
               return input.fromPath(path ->
                  {
                  String requestString = URLDecoder.decode(path.get("cityCountry"), StandardCharsets.UTF_8);
                  log.info("SearchExhibitionsByCityAndDateLambda request path {}", path.get("startDate"));

                  return SearchExhibitionsByCityAndDateRequest.builder()
                            .withCityCountry(requestString)
                            .withStartDate(path.get("startDate"))
                            .withEndDate(path.get("endDate"))
                             .build();
                  });
                },
                (request, serviceComponent) ->
                        serviceComponent.providesSearchExhibitionsByCityAndDateActivity().handleRequest(request)
        );
    }
}
