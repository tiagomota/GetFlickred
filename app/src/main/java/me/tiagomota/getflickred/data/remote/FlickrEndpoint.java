package me.tiagomota.getflickred.data.remote;


final class FlickrEndpoint {

    private static final String FLICKR_KEY = "&api_key=e09ce71c9f7dbbbf23df45f1e1eb144c";
    private static final String FORMAT = "&format=me.tiagomota.getflickred.util.json&nojsoncallback=1";

    // Find By Username
    static final String FIND_BY_USERNAME = "?method=flickr.people.findByUsername" + FLICKR_KEY + FORMAT;

    // Get Public Photos
    static final String GET_PUBLIC_PHOTOS = "?method=flickr.people.getPublicPhotos" + FLICKR_KEY + FORMAT;

    // Get Photo Info
    static final String GET_PHOTO_INFO = "?method=flickr.photos.getInfo" + FLICKR_KEY + FORMAT;

    // Get Photo Sizes
    static final String GET_PHOTO_SIZES = "?method=flickr.photos.getSizes" + FLICKR_KEY + FORMAT;
}
