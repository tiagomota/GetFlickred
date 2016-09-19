package me.tiagomota.getflickred.data.remote;


import me.tiagomota.getflickred.data.model.PhotoInfo;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.data.model.PhotosList;
import me.tiagomota.getflickred.data.model.User;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrService {

    @GET(FlickrEndpoint.FIND_BY_USERNAME)
    Observable<User> findByUsername(@Query(value = "username") String username);

    @GET(FlickrEndpoint.GET_PUBLIC_PHOTOS)
    Observable<PhotosList> getPublicPhotos(@Query(value = "user_id") String userId,
                                           @Query(value = "page") int page,
                                           @Query(value = "per_page") int perPage);

    @GET(FlickrEndpoint.GET_PHOTO_INFO)
    Observable<PhotoInfo> getPhotoInfo(@Query(value = "photo_id") String photoId,
                                       @Query(value = "secret") String secret);

    @GET(FlickrEndpoint.GET_PHOTO_SIZES)
    Observable<PhotoSize> getPhotoSizes(@Query(value = "photo_id") String photoId);
}
