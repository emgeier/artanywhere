package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.artanywhere.activity.requests.GetArtistRequest;
import com.nashss.se.artanywhere.activity.results.GetArtistResult;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class GetArtistLambda extends LambdaActivityRunner<GetArtistRequest, GetArtistResult>
    implements RequestHandler<LambdaRequest<GetArtistRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetArtistRequest> input, Context context) {
        return super.runActivity(
                ()-> {

                return input.fromPath(path -> {
                    String requestString =  URLDecoder.decode(path.get("artistName"), StandardCharsets.UTF_8);

                    return GetArtistRequest.builder()
                            .withArtistName(requestString)
                            .build();
                });
                },
                ((request, serviceComponent) -> serviceComponent.provideGetArtistActivity().handleRequest(request))
            );
    }
}
