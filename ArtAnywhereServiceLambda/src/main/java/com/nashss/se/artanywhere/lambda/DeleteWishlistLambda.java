package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.requests.DeleteWishlistRequest;
import com.nashss.se.artanywhere.activity.requests.GetExhibitionRequest;
import com.nashss.se.artanywhere.activity.results.DeleteWishlistResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteWishlistLambda extends LambdaActivityRunner<DeleteWishlistRequest, DeleteWishlistResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteWishlistRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteWishlistRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<DeleteWishlistRequest> received");
        return super.runActivity(
                () -> {
                    log.info("DeleteWishlistRequest created from user request");
                    return input.fromPath(path-> DeleteWishlistRequest.builder()
                            .withEmail(path.get("email"))
                            .withListName(path.get("listName"))
                            .build());
                },
                ((request, serviceComponent) -> serviceComponent.provideDeleteWishlistActivity().handleRequest(request)));

    }
}
