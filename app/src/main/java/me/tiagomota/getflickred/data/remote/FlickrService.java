package me.tiagomota.getflickred.data.remote;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrService {

    // TODO change observable types

    @GET(FlickrEndpoint.FIND_BY_USERNAME)
    Observable<Void> findByUsername(@Query(value = "username") String username);

    @GET(FlickrEndpoint.GET_PUBLIC_PHOTOS)
    Observable<Void> getPublicPhotos(@Query(value = "user_id") String userId,
                                     @Query(value = "page") int page,
                                     @Query(value = "perpage") int perPage);

    @GET(FlickrEndpoint.GET_PHOTO_INFO)
    Observable<Void> getPhotoInfo(@Query(value = "photo_id") String photoId,
                                  @Query(value = "secret") String secret);

    @GET(FlickrEndpoint.GET_PHOTO_SIZES)
    Observable<Void> getPhotoSizes(@Query(value = "photo_id") String photoId);
}
