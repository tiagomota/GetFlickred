package me.tiagomota.getflickred.data.injection;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.tiagomota.getflickred.BuildConfig;
import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.remote.FlickrService;
import me.tiagomota.getflickred.ui.base.injection.qualifiers.ApplicationContext;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    private static final String BASE_URL = "https://api.flickr.com/services/rest/";
    private static final int REQUEST_TIMEOUT = 15; // seconds
    private static final int CACHE_SIZE = 10 * 1024 * 1024; // 10 Mb


    @Singleton
    @Provides
    FlickrService providesFlickrService(final @ApplicationContext Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(FlickrService.class);
    }

    @Provides
    @Singleton
    DataManager providesDataManager(final FlickrService flickrService) {
        return new DataManager(flickrService);
    }


    /**
     * Builds and returns the OkHttpClient to be used with Retrofit.
     *
     * @param context Context
     * @return OkHttpClient
     */
    private OkHttpClient getOkHttpClient(final @ApplicationContext Context context) {
        // create a Logger for the communications
        final HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        // create the okClient to use in retrofit
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.cache(new Cache(context.getCacheDir(), CACHE_SIZE))
                .followSslRedirects(true)
                .followRedirects(true)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logger);

        return clientBuilder.build();
    }
}
