package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SearchExhibitionsByCityLambda extends LambdaActivityRunner<SearchExhibitionsByCityRequest,
        SearchExhibitionsByCityResult> implements RequestHandler<LambdaRequest<SearchExhibitionsByCityRequest>,
        LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchExhibitionsByCityRequest> input, Context context) {
        log.info("LambdaRequest received");
        return super.runActivity(
                () -> {
                    log.info("SearchExhibitionsByCityLambdaRequest created from user request");

                    return input.fromPath(path -> {
                            String requestString =  URLDecoder.decode(path.get("cityCountry"), StandardCharsets.UTF_8);
                            return SearchExhibitionsByCityRequest.builder()
                                .withCityCountry(requestString).build();
                    });
                },
                (request, serviceComponent) ->
                        serviceComponent.providesSearchExhibitionsByCityActivity().handleRequest(request)
        );
    }
}
