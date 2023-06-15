package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.artanywhere.activity.requests.GetExhibitionRequest;
import com.nashss.se.artanywhere.activity.results.GetExhibitionResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class GetExhibitionLambda extends LambdaActivityRunner<GetExhibitionRequest, GetExhibitionResult>
    implements RequestHandler<AuthenticatedLambdaRequest<GetExhibitionRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetExhibitionRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest received.");
        return super.runActivity(
            () -> {
                log.info("GetExhibitionRequest created from user request");
                 return input.fromPath(path-> {
                     String requestCity=  URLDecoder.decode(path.get("cityCountry"), StandardCharsets.UTF_8);
                     String requestExhibition =  URLDecoder.decode(path.get("exhibitionName"), StandardCharsets.UTF_8);
                     return GetExhibitionRequest.builder()
                             .withExhibitionName(requestExhibition)
                             .withCityCountry(requestCity)
                             .build();
                 });
                },
                ((request, serviceComponent) -> serviceComponent.provideGetExhibitionActivity().handleRequest(request))
        );
    }
}
