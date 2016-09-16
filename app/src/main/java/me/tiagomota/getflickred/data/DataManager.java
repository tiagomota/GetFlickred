package me.tiagomota.getflickred.data;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.tiagomota.getflickred.data.remote.FlickrService;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DataManager {

    private final FlickrService mFlickrService;

    public DataManager(final FlickrService flickrService) {
        mFlickrService = flickrService;
    }


    public void fetchUser(final String username) {
        mFlickrService.findByUsername(username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Void>() {
                            @Override
                            public void call(final Void aVoid) {
                                Log.d("DataManager", "Fetch User SUCCESS");
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(final Throwable throwable) {
                                Log.e("DataManager", "Fetch User FAILURE", throwable);
                            }
                        }
                );
    }

}
