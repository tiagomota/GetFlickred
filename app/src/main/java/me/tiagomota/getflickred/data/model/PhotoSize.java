package me.tiagomota.getflickred.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoSize implements Parcelable {

    @Expose
    @SerializedName("sizes")
    private Sizes mSizes;

    public boolean isBloggable() {
        return mSizes.mCanBlog;
    }

    public boolean isPrintable() {
        return mSizes.mCanPrint;
    }

    public boolean isDownloadable() {
        return mSizes.mCanDownload;
    }

    public List<Size> getSizes() {
        return mSizes.mSize;
    }

    private static class Sizes implements Parcelable {

        @Expose
        @SerializedName("canblog")
        boolean mCanBlog;

        @Expose
        @SerializedName("canprint")
        boolean mCanPrint;

        @Expose
        @SerializedName("candownload")
        boolean mCanDownload;

        @Expose
        @SerializedName("size")
        List<Size> mSize;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.mCanBlog ? (byte) 1 : (byte) 0);
            dest.writeByte(this.mCanPrint ? (byte) 1 : (byte) 0);
            dest.writeByte(this.mCanDownload ? (byte) 1 : (byte) 0);
            dest.writeTypedList(this.mSize);
        }

        protected Sizes(Parcel in) {
            this.mCanBlog = in.readByte() != 0;
            this.mCanPrint = in.readByte() != 0;
            this.mCanDownload = in.readByte() != 0;
            this.mSize = in.createTypedArrayList(Size.CREATOR);
        }

        public static final Creator<Sizes> CREATOR = new Creator<Sizes>() {
            @Override
            public Sizes createFromParcel(Parcel source) {
                return new Sizes(source);
            }

            @Override
            public Sizes[] newArray(int size) {
                return new Sizes[size];
            }
        };
    }

    static class Size implements Parcelable {

        @Expose
        @SerializedName("label")
        String mLabel;

        @Expose
        @SerializedName("width")
        int mWidth;

        @Expose
        @SerializedName("height")
        int mHeight;

        @Expose
        @SerializedName("source")
        String mSource;

        @Expose
        @SerializedName("url")
        String mUrl;

        @Expose
        @SerializedName("media")
        String mMedia;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mLabel);
            dest.writeInt(this.mWidth);
            dest.writeInt(this.mHeight);
            dest.writeString(this.mSource);
            dest.writeString(this.mUrl);
            dest.writeString(this.mMedia);
        }

        protected Size(Parcel in) {
            this.mLabel = in.readString();
            this.mWidth = in.readInt();
            this.mHeight = in.readInt();
            this.mSource = in.readString();
            this.mUrl = in.readString();
            this.mMedia = in.readString();
        }

        public static final Parcelable.Creator<Size> CREATOR = new Parcelable.Creator<Size>() {
            @Override
            public Size createFromParcel(Parcel source) {
                return new Size(source);
            }

            @Override
            public Size[] newArray(int size) {
                return new Size[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSizes, flags);
    }


    protected PhotoSize(Parcel in) {
        this.mSizes = in.readParcelable(Sizes.class.getClassLoader());
    }

    public static final Parcelable.Creator<PhotoSize> CREATOR = new Parcelable.Creator<PhotoSize>() {
        @Override
        public PhotoSize createFromParcel(Parcel source) {
            return new PhotoSize(source);
        }

        @Override
        public PhotoSize[] newArray(int size) {
            return new PhotoSize[size];
        }
    };
}
