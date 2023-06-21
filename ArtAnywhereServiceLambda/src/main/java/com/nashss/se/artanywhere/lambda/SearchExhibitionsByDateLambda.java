package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByDateRequest;
import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByMovementRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByDateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchExhibitionsByDateLambda extends LambdaActivityRunner<SearchExhibitionsByDateRequest,
        SearchExhibitionsByDateResult> implements RequestHandler<LambdaRequest<SearchExhibitionsByDateRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchExhibitionsByDateRequest> input, Context context) {
        log.info("LambdaRequest received");
        return super.runActivity(
                () -> {
                    log.info("SearchExhibitionsByDateLambdaRequest created from user request");
                    return input.fromPath(path -> SearchExhibitionsByDateRequest.builder()
                            .withStartDate(path.get("startDate"))
                            .withEndDate(path.get("endDate"))
                            .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.providesSearchExhibitionsByDateActivity().handleRequest(request)
        );
    }
}
