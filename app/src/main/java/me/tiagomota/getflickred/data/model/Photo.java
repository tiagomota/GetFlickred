package me.tiagomota.getflickred.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {

    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("owner")
    @Expose
    private String mOwner;

    @SerializedName("secret")
    @Expose
    private String mSecret;

    @SerializedName("server")
    @Expose
    private String mServer;

    @SerializedName("farm")
    @Expose
    private Integer mFarm;

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("ispublic")
    @Expose
    private boolean mIsPublic;

    @SerializedName("isfriend")
    @Expose
    private boolean mIsFriend;

    @SerializedName("isfamily")
    @Expose
    private boolean mIsFamily;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mOwner);
        dest.writeString(this.mSecret);
        dest.writeString(this.mServer);
        dest.writeValue(this.mFarm);
        dest.writeString(this.mTitle);
        dest.writeByte(this.mIsPublic ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mIsFriend ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mIsFamily ? (byte) 1 : (byte) 0);
    }

    protected Photo(Parcel in) {
        this.mId = in.readString();
        this.mOwner = in.readString();
        this.mSecret = in.readString();
        this.mServer = in.readString();
        this.mFarm = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mTitle = in.readString();
        this.mIsPublic = in.readByte() != 0;
        this.mIsFriend = in.readByte() != 0;
        this.mIsFamily = in.readByte() != 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
