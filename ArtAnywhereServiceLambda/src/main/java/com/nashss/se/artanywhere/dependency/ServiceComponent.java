package com.nashss.se.artanywhere.dependency;

import com.nashss.se.artanywhere.activity.AddExhibitionToWishlistActivity;
import com.nashss.se.artanywhere.activity.CreateWishlistActivity;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    CreateWishlistActivity provideCreateWishlistActivity();

    AddExhibitionToWishlistActivity provideAddExhibitionToWishlistActivity();
}
