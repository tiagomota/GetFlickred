package me.tiagomota.getflickred.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PhotosList extends Base {

    @SerializedName("photos")
    @Expose
    private Content mContent;

    public List<Photo> getPhotos() {
        return mContent.mPhotos;
    }

    public int getPage() {
        return mContent.mPage;
    }

    public int getPerPage() {
        return mContent.mPerPage;
    }

    public int getTotalPages() {
        return mContent.mTotalPages;
    }

    public void setTotalPages(final int totalPages) {
        mContent.mTotalPages = totalPages;
    }

    public int getTotalPhotos() {
        return mContent.mTotalPhotos;
    }

    private static class Content implements Parcelable {

        @SerializedName("photo")
        @Expose
        List<Photo> mPhotos = new ArrayList<>();

        @SerializedName("page")
        @Expose
        int mPage;

        @SerializedName("perpage")
        @Expose
        int mPerPage;

        @SerializedName("pages")
        @Expose
        int mTotalPages;

        @SerializedName("total")
        @Expose
        int mTotalPhotos;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.mPhotos);
            dest.writeInt(this.mPage);
            dest.writeInt(this.mPerPage);
            dest.writeInt(this.mTotalPages);
            dest.writeInt(this.mTotalPhotos);
        }

        protected Content(Parcel in) {
            this.mPhotos = in.createTypedArrayList(Photo.CREATOR);
            this.mPage = in.readInt();
            this.mPerPage = in.readInt();
            this.mTotalPages = in.readInt();
            this.mTotalPhotos = in.readInt();
        }

        public static final Parcelable.Creator<Content> CREATOR = new Parcelable.Creator<Content>() {
            @Override
            public Content createFromParcel(Parcel source) {
                return new Content(source);
            }

            @Override
            public Content[] newArray(int size) {
                return new Content[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.mContent, flags);
    }

    protected PhotosList(Parcel in) {
        super(in);
        this.mContent = in.readParcelable(Content.class.getClassLoader());
    }

    public static final Creator<PhotosList> CREATOR = new Creator<PhotosList>() {
        @Override
        public PhotosList createFromParcel(Parcel source) {
            return new PhotosList(source);
        }

        @Override
        public PhotosList[] newArray(int size) {
            return new PhotosList[size];
        }
    };
}
