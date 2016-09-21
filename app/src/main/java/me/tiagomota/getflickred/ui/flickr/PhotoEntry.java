package me.tiagomota.getflickred.ui.flickr;

import android.os.Parcel;
import android.os.Parcelable;

import me.tiagomota.getflickred.data.model.Photo;
import me.tiagomota.getflickred.data.model.PhotoInfo;
import me.tiagomota.getflickred.data.model.PhotoSize;

/**
 * UI specific model, that aims to wrap the a {@link Photo} object,
 * with it's {@link PhotoInfo} and {@link PhotoSize}.
 */
public class PhotoEntry implements Parcelable {

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

    public String getPhotoDescription() {
        return mPhotoInfo.getDescription();
    }

    public String getPhotoNrOfComments() {
        return mPhotoInfo.getComments();
    }

    public String getPhotoNrOfTags() {
        return mPhotoInfo.getTags();
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mPhoto, flags);
        dest.writeParcelable(this.mPhotoInfo, flags);
        dest.writeParcelable(this.mPhotoSize, flags);
    }

    protected PhotoEntry(Parcel in) {
        this.mPhoto = in.readParcelable(Photo.class.getClassLoader());
        this.mPhotoInfo = in.readParcelable(PhotoInfo.class.getClassLoader());
        this.mPhotoSize = in.readParcelable(PhotoSize.class.getClassLoader());
    }

    public static final Parcelable.Creator<PhotoEntry> CREATOR = new Parcelable.Creator<PhotoEntry>() {
        @Override
        public PhotoEntry createFromParcel(Parcel source) {
            return new PhotoEntry(source);
        }

        @Override
        public PhotoEntry[] newArray(int size) {
            return new PhotoEntry[size];
        }
    };
}
