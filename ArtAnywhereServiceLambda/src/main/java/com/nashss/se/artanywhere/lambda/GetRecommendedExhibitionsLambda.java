package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.artanywhere.activity.requests.GetRecommendedExhibitionsRequest;
import com.nashss.se.artanywhere.activity.results.GetRecommendedExhibitionsResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


public class GetRecommendedExhibitionsLambda  extends LambdaActivityRunner<GetRecommendedExhibitionsRequest,
        GetRecommendedExhibitionsResult> implements RequestHandler<LambdaRequest<GetRecommendedExhibitionsRequest>,
        LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetRecommendedExhibitionsRequest> input, Context context) {
        log.info("LambdaRequest received.");
        return super.runActivity(
                () -> {
                    log.info("GetRecommendedExhibitionsRequest created from user request");
                    return input.fromPath(path-> {
                        String requestCity=  URLDecoder.decode(path.get("cityCountry"), StandardCharsets.UTF_8);
                        String requestExhibition =  URLDecoder.decode(path.get("exhibitionName"), StandardCharsets.UTF_8);
                        log.info("GetRecommendedExhibitionsRequest with city: {} and exhibition: {}", requestCity, requestExhibition);
                        return GetRecommendedExhibitionsRequest.builder()
                                .withExhibitionName(requestExhibition)
                                .withCityCountry(requestCity)
                                .build();
                            }
                    );

                },
                    ((request, serviceComponent) -> serviceComponent.provideGetRecommendedExhibitionsActivity().handleRequest(request))
        );

    }
}
//        return super.runActivity(
//            ()-> {
//        return input.fromPath(path-> {
//            String requestString =  URLDecoder.decode(path.get("artistName"), StandardCharsets.UTF_8);
//            return GetRecommendedArtistsRequest.builder()
//                    .withArtistName(requestString)
//                    .build();
//        });
//    },
//            ((request, serviceComponent) -> serviceComponent.provideGetRecommendedArtistsActivity().handleRequest(request))
//            );
//}