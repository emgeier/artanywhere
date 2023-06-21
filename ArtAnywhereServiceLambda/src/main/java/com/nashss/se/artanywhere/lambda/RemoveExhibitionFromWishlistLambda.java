package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.artanywhere.activity.requests.RemoveExhibitionFromWishlistRequest;
import com.nashss.se.artanywhere.activity.results.RemoveExhibitionFromWishlistResult;

public class RemoveExhibitionFromWishlistLambda
        extends LambdaActivityRunner<RemoveExhibitionFromWishlistRequest, RemoveExhibitionFromWishlistResult>
        implements RequestHandler<AuthenticatedLambdaRequest<RemoveExhibitionFromWishlistRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<RemoveExhibitionFromWishlistRequest> input,
                                        Context context) {
        return super.runActivity(
            () -> {
                RemoveExhibitionFromWishlistRequest unauthenticatedRequest = input.fromBody(RemoveExhibitionFromWishlistRequest.class);

                return input.fromUserClaims(claims ->
                        RemoveExhibitionFromWishlistRequest.builder()
                                .withEmail(unauthenticatedRequest.getEmail())
                                .withCityCountry(unauthenticatedRequest.getCityCountry())
                                .withExhibitionName(unauthenticatedRequest.getExhibitionName())
                                .withListName(unauthenticatedRequest.getListName())
                                .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideRemoveExhibitionFromWishlistActivity().handleRequest(request)
        );
    }
}
