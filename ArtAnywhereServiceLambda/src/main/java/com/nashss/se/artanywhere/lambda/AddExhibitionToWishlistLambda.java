package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.AddExhibitionToWishlistRequest;
import com.nashss.se.artanywhere.activity.results.AddExhibitionToWishlistResult;

public class AddExhibitionToWishlistLambda
        extends LambdaActivityRunner<AddExhibitionToWishlistRequest, AddExhibitionToWishlistResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddExhibitionToWishlistRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddExhibitionToWishlistRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    AddExhibitionToWishlistRequest unauthenticatedRequest = input.fromBody(AddExhibitionToWishlistRequest.class);
                    return input.fromUserClaims(claims ->
                            AddExhibitionToWishlistRequest.builder()
                                    .withEmail(claims.get("email"))
                                    .withCityCountry(unauthenticatedRequest.getCityCountry())
                                    .withExhibitionName(unauthenticatedRequest.getExhibitionName())
                                    .withListName(unauthenticatedRequest.getListName())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddExhibitionToWishlistActivity().handleRequest(request)
        );
    }
}
