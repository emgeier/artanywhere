package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByCityRequest;
import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByMovementRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByMovementResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchExhibitionsByMovementLambda extends LambdaActivityRunner<SearchExhibitionsByMovementRequest,
        SearchExhibitionsByMovementResult> implements RequestHandler<LambdaRequest<SearchExhibitionsByMovementRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchExhibitionsByMovementRequest> input, Context context) {
        log.info("LambdaRequest received");
        return super.runActivity(
                () -> {
                    System.out.println("handle request input ");
                    log.info("SearchExhibitionsByMovementLambdaRequest created from user request");
                    return input.fromPath(path -> SearchExhibitionsByMovementRequest.builder()
                            .withMovement(path.get("cityCountry")).build());
                },
                (request, serviceComponent) ->
                        serviceComponent.providesSearchExhibitionsByMovementActivity().handleRequest(request)
        );
    }
}
