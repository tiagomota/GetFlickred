package me.tiagomota.getflickred.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoSize extends Base {

    public enum Dimension {
        SQUARE("Square"),
        LARGE_SQUARE("Large Square"),
        THUMBNAIL("Large Square"),
        SMALL("Small"),
        MEDIUM("Medium"),
        ORIGINAL("Original");

        private String mLabel;

        Dimension(final String dimen) {
            mLabel = dimen;
        }

        public String getLabel() {
            return mLabel;
        }
    }



    @Expose
    @SerializedName("sizes")
    private Sizes mSizes;


    @Nullable
    public String getUrl(final Dimension dimension) {
        String url = null;

        for (final Size size : mSizes.mSize) {
            if (size.mLabel.equalsIgnoreCase(dimension.getLabel())) {
                url = size.mSource;
                break;
            }
        }

        return url;
    }


    private static class Sizes implements Parcelable {

        @Expose
        @SerializedName("canblog")
        int mCanBlog;

        @Expose
        @SerializedName("canprint")
        int mCanPrint;

        @Expose
        @SerializedName("candownload")
        int mCanDownload;

        @Expose
        @SerializedName("size")
        List<Size> mSize;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mCanBlog);
            dest.writeInt(this.mCanPrint);
            dest.writeInt(this.mCanDownload);
            dest.writeTypedList(this.mSize);
        }

        protected Sizes(Parcel in) {
            this.mCanBlog = in.readInt();
            this.mCanPrint = in.readInt();
            this.mCanDownload = in.readInt();
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
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.mSizes, flags);
    }

    protected PhotoSize(Parcel in) {
        super(in);
        this.mSizes = in.readParcelable(Sizes.class.getClassLoader());
    }

    public static final Creator<PhotoSize> CREATOR = new Creator<PhotoSize>() {
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
