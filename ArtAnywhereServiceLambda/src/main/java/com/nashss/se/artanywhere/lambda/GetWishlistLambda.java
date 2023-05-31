package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.AddExhibitionToWishlistRequest;
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

        return super.runActivity(
            () -> {
System.out.println("handle request input ");
                log.info("GetWishlistLambdaRequest created from user request");
                return input.fromPath(path-> GetWishlistRequest.builder()
                       .withEmail(path.get("email"))
                       .withListName(path.get("listName"))
                       .build());
            },
            (request, serviceComponent) ->
                   serviceComponent.provideGetWishlistActivity().handleRequest(request)
        );
    }
}
