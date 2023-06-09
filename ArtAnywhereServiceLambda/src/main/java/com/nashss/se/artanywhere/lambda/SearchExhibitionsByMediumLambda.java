package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByMediumRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByMediumResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchExhibitionsByMediumLambda extends LambdaActivityRunner<SearchExhibitionsByMediumRequest,
        SearchExhibitionsByMediumResult> implements RequestHandler<LambdaRequest<SearchExhibitionsByMediumRequest>,
        LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchExhibitionsByMediumRequest> input, Context context) {
        log.info("LambdaRequest received");
        return super.runActivity(
                () -> {
                    System.out.println("handle request input ");

                    log.info("SearchExhibitionsByMediumLambdaRequest created from user request");
                    return input.fromPath(path -> SearchExhibitionsByMediumRequest.builder()
                            .withMedium(path.get("medium")).build());
                },
                (request, serviceComponent) ->
                        serviceComponent.providesSearchExhibitionsByMediumActivity().handleRequest(request)
        );
    }
}
