package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.GetArtistRequest;
import com.nashss.se.artanywhere.activity.results.GetArtistResult;

public class GetArtistLambda extends LambdaActivityRunner<GetArtistRequest, GetArtistResult>
    implements RequestHandler<LambdaRequest<GetArtistRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetArtistRequest> input, Context context) {
        return super.runActivity(
                ()-> {
                return input.fromPath(path -> GetArtistRequest.builder()
                    .withArtistName(path.get("artistName"))
                    .build());
                },
                ((request, serviceComponent) -> serviceComponent.provideGetArtistActivity().handleRequest(request))
            );
    }
}
