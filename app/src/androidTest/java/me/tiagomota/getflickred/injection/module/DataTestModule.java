package me.tiagomota.getflickred.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.remote.FlickrService;

import static org.mockito.Mockito.mock;

@Module
public class DataTestModule {

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    FlickrService provideFlickrService() {
        return mock(FlickrService.class);
    }

}
