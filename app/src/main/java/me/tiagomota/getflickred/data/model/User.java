package me.tiagomota.getflickred.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User extends Base {

    @Expose
    @SerializedName("user")
    private UserId mUser;


    /**
     * Getter of the user ID.
     *
     * @return String
     */
    public String getId() {
        return mUser.mId;
    }

    /**
     * Getter of the user NS ID.
     *
     * @return String
     */
    public String getNsid() {
        return mUser.mNsid;
    }

    /**
     * Getter of the user name.
     *
     * @return String
     */
    public String getUsername() {
        return mUser.mUsername.mContent;
    }

    private static class UserId implements Parcelable {

        @Expose
        @SerializedName("id")
        String mId;

        @Expose
        @SerializedName("nsid")
        String mNsid;

        @Expose
        @SerializedName("username")
        Username mUsername;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mId);
            dest.writeString(this.mNsid);
            dest.writeParcelable(this.mUsername, flags);
        }

        UserId(Parcel in) {
            this.mId = in.readString();
            this.mNsid = in.readString();
            this.mUsername = in.readParcelable(Username.class.getClassLoader());
        }

        public static final Parcelable.Creator<UserId> CREATOR = new Parcelable.Creator<UserId>() {
            @Override
            public UserId createFromParcel(Parcel source) {
                return new UserId(source);
            }

            @Override
            public UserId[] newArray(int size) {
                return new UserId[size];
            }
        };
    }

    private static class Username implements Parcelable {

        @Expose
        @SerializedName("_content")
        String mContent;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mContent);
        }

        protected Username(Parcel in) {
            this.mContent = in.readString();
        }

        public static final Parcelable.Creator<Username> CREATOR = new Parcelable.Creator<Username>() {
            @Override
            public Username createFromParcel(Parcel source) {
                return new Username(source);
            }

            @Override
            public Username[] newArray(int size) {
                return new Username[size];
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
        dest.writeParcelable(this.mUser, flags);
    }

    protected User(Parcel in) {
        super(in);
        this.mUser = in.readParcelable(UserId.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
