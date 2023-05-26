package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.GetWishlistRequest;
import com.nashss.se.artanywhere.activity.results.GetWishlistResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetWishlistLambda extends LambdaActivityRunner<GetWishlistRequest, GetWishlistResult>
    implements RequestHandler<AuthenticatedLambdaRequest <GetWishlistRequest>,LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetWishlistRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest received");
        System.out.println("request received");
        System.out.println(input.fromPath(path-> GetWishlistRequest.builder().withListName("listName").build()));
        return super.runActivity(
                () -> {
                    GetWishlistRequest unauthenticatedRequest = input.fromBody(GetWishlistRequest.class);
//                    GetWishlistRequest unauthenticatedRequest = input.fromBody(GetWishlistRequest.class);
                    System.out.println("handle request input reeceeeeived");
                    log.info("GetWishlistLambdaRequest created from user request");
                    return input.fromUserClaims(claims ->
                            GetWishlistRequest.builder()
                                    .withEmail(claims.get("email"))
                                    .withListName(unauthenticatedRequest.getListName())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetWishlistActivity().handleRequest(request)
        );
    }
}
