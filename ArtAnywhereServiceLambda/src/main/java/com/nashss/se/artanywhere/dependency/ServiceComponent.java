package com.nashss.se.artanywhere.dependency;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.AddExhibitionToWishlistActivity;
import com.nashss.se.artanywhere.activity.CreateWishlistActivity;

import com.nashss.se.artanywhere.activity.GetExhibitionActivity;
import com.nashss.se.artanywhere.activity.GetWishlistActivity;
import com.nashss.se.artanywhere.activity.requests.GetExhibitionRequest;
import com.nashss.se.artanywhere.activity.requests.GetWishlistRequest;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    CreateWishlistActivity provideCreateWishlistActivity();

    AddExhibitionToWishlistActivity provideAddExhibitionToWishlistActivity();

   GetWishlistActivity provideGetWishlistActivity();

    GetExhibitionActivity provideGetExhibitionActivity();
}
