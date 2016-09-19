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

    /**
     * Returns an Observable prepared by Retrofit to fetch the User public Photos
     * with the given user id, page and per page photos number.
     *
     * @param userId String
     * @param page int
     * @param perPage int
     * @return Observable
     */
    public Observable<PhotosList> getPublicPhotos(final String userId, final int page, final int perPage) {
        return mFlickrService.getPublicPhotos(userId, page, perPage);
    }

    /**
     * Returns an observable prepared by Retrofit to fetch the Photo Info.
     *
     * @param photoId String
     * @param photoSecret String
     * @return Observable
     */
    public Observable<PhotoInfo> getPhotoInfo(final String photoId, final String photoSecret) {
        return mFlickrService.getPhotoInfo(photoId, photoSecret);
    }

    /**
     * Returns an observable prepared by Retrofit to fetch the Photo Sizes.
     *
     * @param photoId String
     * @return Observable
     */
    public Observable<PhotoSize> getPhotoSizes(final String photoId) {
        return mFlickrService.getPhotoSizes(photoId);
    }
}
