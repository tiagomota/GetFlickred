package me.tiagomota.getflickred.injection.modules;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.tiagomota.getflickred.injection.annotations.ApplicationContext;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

}
