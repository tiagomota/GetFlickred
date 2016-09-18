package me.tiagomota.getflickred.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Base implements Parcelable {

    private static final String SUCCESS = "ok";

    @Expose
    @SerializedName("stat")
    private String mStat;

    @Expose
    @SerializedName("message")
    private String mMessage;

    public boolean isStatusSuccess() {
        return mStat.equalsIgnoreCase(SUCCESS);
    }

    public String getMessage() {
        return mMessage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mStat);
        dest.writeString(this.mMessage);
    }

    protected Base(Parcel in) {
        this.mStat = in.readString();
        this.mMessage = in.readString();
    }

    public static final Creator<Base> CREATOR = new Creator<Base>() {
        @Override
        public Base createFromParcel(Parcel source) {
            return new Base(source);
        }

        @Override
        public Base[] newArray(int size) {
            return new Base[size];
        }
    };
}
