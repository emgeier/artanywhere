package com.nashss.se.artanywhere.dependency;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.*;

import com.nashss.se.artanywhere.activity.requests.*;
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

    SearchExhibitionsByCityActivity providesSearchExhibitionsByCityActivity();

    SearchExhibitionsByMovementActivity providesSearchExhibitionsByMovementActivity();

    SearchExhibitionsByDateActivity providesSearchExhibitionsByDateActivity();

    SearchExhibitionsByMediumActivity providesSearchExhibitionsByMediumActivity();
    SearchExhibitionsByCityAndMediumActivity providesSearchExhibitionsByCityAndMediumActivity();

    SearchExhibitionsByArtistActivity providesSearchExhibitionsByArtistActivity();

    GetArtistActivity provideGetArtistActivity();

    GetRecommendedArtistsActivity provideGetRecommendedArtistsActivity();
}
