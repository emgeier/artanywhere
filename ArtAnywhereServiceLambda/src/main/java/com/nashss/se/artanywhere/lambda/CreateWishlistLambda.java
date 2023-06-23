package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.artanywhere.activity.requests.CreateWishlistRequest;
import com.nashss.se.artanywhere.activity.results.CreateWishlistResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CreateWishlistLambda
          extends LambdaActivityRunner<CreateWishlistRequest, CreateWishlistResult>
    implements RequestHandler<AuthenticatedLambdaRequest<CreateWishlistRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateWishlistRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest received");
        return super.runActivity(
            () -> {
                    CreateWishlistRequest unauthenticatedRequest = input.fromBody(CreateWishlistRequest.class);
                    String requestString =  URLDecoder.decode(unauthenticatedRequest.getListName(), StandardCharsets.UTF_8);
                    log.info("CreateWishlistLambdaRequest created from user request");
                    return input.fromUserClaims(claims ->
                            CreateWishlistRequest.builder()
                                    .withListName(requestString)
                                    .withDescription(unauthenticatedRequest.getDescription())
                                    .withEmail(claims.get("email"))
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideCreateWishlistActivity().handleRequest(request)
        );
    }

}
