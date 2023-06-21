package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByArtistRequest;
import com.nashss.se.artanywhere.activity.results.SearchExhibitionsByArtistResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SearchExhibitionsByArtistLambda extends LambdaActivityRunner<SearchExhibitionsByArtistRequest,
        SearchExhibitionsByArtistResult> implements RequestHandler<LambdaRequest<SearchExhibitionsByArtistRequest>,
        LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchExhibitionsByArtistRequest> input, Context context) {
        log.info("LambdaRequest received");
        return super.runActivity(
            () -> {
                log.info("SearchExhibitionsByMediumLambdaRequest created from user request");
                return input.fromPath(path ->  {
                           String requestString =  URLDecoder.decode(path.get("artistName"), StandardCharsets.UTF_8);
                   return SearchExhibitionsByArtistRequest.builder()
                            .withArtistName(requestString).build();});
                },
                (request, serviceComponent) ->
                        serviceComponent.providesSearchExhibitionsByArtistActivity().handleRequest(request)
        );
    }
}
