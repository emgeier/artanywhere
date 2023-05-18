package com.nashss.se.artanywhere.dependency;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.artanywhere.activity.CreateWishlistActivity;
import com.nashss.se.artanywhere.activity.requests.CreateWishlistRequest;
import dagger.Component;
import dagger.Provides;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    CreateWishlistActivity provideCreateWishlistActivity();


}
