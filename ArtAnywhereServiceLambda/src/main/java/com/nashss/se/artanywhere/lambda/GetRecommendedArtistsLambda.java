package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.GetRecommendedArtistsRequest;
import com.nashss.se.artanywhere.activity.results.GetRecommendedArtistsResult;

public class GetRecommendedArtistsLambda extends LambdaActivityRunner<GetRecommendedArtistsRequest,
        GetRecommendedArtistsResult> implements RequestHandler<LambdaRequest<GetRecommendedArtistsRequest>,
        LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetRecommendedArtistsRequest> input, Context context) {
        return super.runActivity(
            ()-> {
                return input.fromPath(path-> GetRecommendedArtistsRequest.builder()
                .withArtistName(path.get("artistName"))
                .build());
            },
                ((request, serviceComponent) -> serviceComponent.provideGetRecommendedArtistsActivity().handleRequest(request))
        );
    }
}
