package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.artanywhere.activity.requests.CreateWishlistRequest;
import com.nashss.se.artanywhere.activity.results.CreateWishlistResult;

public class CreateWishlistLambda
          extends LambdaActivityRunner<CreateWishlistRequest, CreateWishlistResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateWishlistRequest>, LambdaResponse> {
        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateWishlistRequest> input, Context context) {
        System.out.println("HANDLEREQUEST!!!");
        return super.runActivity(
                () -> {
                    CreateWishlistRequest unauthenticatedRequest = input.fromBody(CreateWishlistRequest.class);
                    System.out.println("REQUEST CREATED!!!");
                    return input.fromUserClaims(claims ->
                            CreateWishlistRequest.builder()
                                    .withListName(unauthenticatedRequest.getListName())
                                   // .withTags(unauthenticatedRequest.getTags())
                                    .withEmail(unauthenticatedRequest.getEmail())
                                   // .withEmail(claims.get("email"))
                                    //   .withCustomerName(claims.get("name"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateWishlistActivity().handleRequest(request)
        );
    }

}
