package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityAndMediumRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByCityAndMediumResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchExhibitionsByCityAndMediumLambda extends LambdaActivityRunner<SearchExhibitionsByCityAndMediumRequest,
        SearchExhibitionsByCityAndMediumResult> implements RequestHandler<LambdaRequest<SearchExhibitionsByCityAndMediumRequest>,
        LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchExhibitionsByCityAndMediumRequest> input, Context context) {
        log.info("LambdaRequest received");
        return super.runActivity(
                () -> {
System.out.println("handle request input ");

                    log.info("SearchExhibitionsByCityAndMediumLambdaRequest created from user request");
                    return input.fromPath(path -> SearchExhibitionsByCityAndMediumRequest.builder()
                            .withCityCountry(path.get("cityCountry"))
                            .withMedium(path.get("medium")).build());
                },
                (request, serviceComponent) ->
                        serviceComponent.providesSearchExhibitionsByCityAndMediumActivity().handleRequest(request)
        );
    }
}
