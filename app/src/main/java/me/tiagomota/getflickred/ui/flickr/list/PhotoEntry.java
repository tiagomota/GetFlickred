package me.tiagomota.getflickred.ui.flickr.list;

import me.tiagomota.getflickred.data.model.Photo;
import me.tiagomota.getflickred.data.model.PhotoInfo;
import me.tiagomota.getflickred.data.model.PhotoSize;

/**
 * UI specific model, that aims to wrap the a {@link Photo} object,
 * with it's {@link PhotoInfo} and {@link PhotoSize}.
 */
class PhotoEntry {

    private Photo mPhoto;
    private PhotoInfo mPhotoInfo;
    private PhotoSize mPhotoSize;

    public PhotoEntry(final Photo photo, final PhotoInfo photoInfo, final PhotoSize photoSize) {
        mPhoto = photo;
        mPhotoInfo = photoInfo;
        mPhotoSize = photoSize;
    }

    public String getPhotoTitle() {
        return mPhoto.getTitle();
    }

    public String getPhotoUploadDate() {
        return mPhotoInfo.getPostedDate();
    }

    public String getPhotoTakenDate() {
        return mPhotoInfo.getTakenDate();
    }

    public String getPhotoImageUrl(final PhotoSize.Dimension dimen) {
        return mPhotoSize.getUrl(dimen);
    }
}
