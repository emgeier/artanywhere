package com.nashss.se.artanywhere.dependency;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.*;

import com.nashss.se.artanywhere.activity.requests.DeleteWishlistRequest;
import com.nashss.se.artanywhere.activity.requests.GetExhibitionRequest;
import com.nashss.se.artanywhere.activity.requests.GetWishlistRequest;
import com.nashss.se.artanywhere.activity.requests.RemoveExhibitionFromWishlistRequest;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    CreateWishlistActivity provideCreateWishlistActivity();

    AddExhibitionToWishlistActivity provideAddExhibitionToWishlistActivity();

   GetWishlistActivity provideGetWishlistActivity();

    GetExhibitionActivity provideGetExhibitionActivity();

    RemoveExhibitionFromWishlistActivity provideRemoveExhibitionFromWishlistActivity();

    DeleteWishlistActivity provideDeleteWishlistActivity();
}
