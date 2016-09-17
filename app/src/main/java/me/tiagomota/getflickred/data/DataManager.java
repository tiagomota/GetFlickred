package me.tiagomota.getflickred.data;

import me.tiagomota.getflickred.data.model.PhotoInfo;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.data.model.PhotosList;
import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.data.remote.FlickrService;
import rx.Observable;

public class DataManager {

    private final FlickrService mFlickrService;

    public DataManager(final FlickrService flickrService) {
        mFlickrService = flickrService;
    }

    /**
     * Returns an Observable prepared by Retrofit to fetch the User
     * with the given username.
     *
     * @param username String
     * @return Observable
     */
    public Observable<User> getUser(final String username) {
        return mFlickrService.findByUsername(username);
    }

    public Observable<PhotosList> getPublicPhotos(final String userId, final int page, final int perPage) {
        return mFlickrService.getPublicPhotos(userId, page, perPage);
    }

    public Observable<PhotoInfo> getPhotoInfo(final String photoId, final String photoSecret) {
        return mFlickrService.getPhotoInfo(photoId, photoSecret);
    }

    public Observable<PhotoSize> getPhotoSizes(final String photoId) {
        return mFlickrService.getPhotoSizes(photoId);
    }
}
