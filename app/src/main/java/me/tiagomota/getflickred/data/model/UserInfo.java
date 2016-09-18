package me.tiagomota.getflickred.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo extends Base {

    @SerializedName("person")
    @Expose
    private Person mPerson;

    public String getId() {
        return mPerson.mId;
    }

    public String getUsername() {
        return mPerson.mUsername.mContent;
    }

    public String getRealName() {
        return mPerson.mRealname.mContent;
    }

    private static class Person implements Parcelable {

        @SerializedName("id")
        @Expose
        private String mId;

        @SerializedName("username")
        @Expose
        private Username mUsername;

        @SerializedName("realname")
        @Expose
        private RealName mRealname;

        @SerializedName("photosurl")
        @Expose
        private PhotoUrl mPhotosurl;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mId);
            dest.writeParcelable(this.mUsername, flags);
            dest.writeParcelable(this.mRealname, flags);
            dest.writeParcelable(this.mPhotosurl, flags);
        }

        protected Person(Parcel in) {
            this.mId = in.readString();
            this.mUsername = in.readParcelable(Username.class.getClassLoader());
            this.mRealname = in.readParcelable(RealName.class.getClassLoader());
            this.mPhotosurl = in.readParcelable(PhotoUrl.class.getClassLoader());
        }

        public static final Creator<Person> CREATOR = new Creator<Person>() {
            @Override
            public Person createFromParcel(Parcel source) {
                return new Person(source);
            }

            @Override
            public Person[] newArray(int size) {
                return new Person[size];
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

        public static final Creator<Username> CREATOR = new Creator<Username>() {
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

    private static class RealName implements Parcelable {

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

        protected RealName(Parcel in) {
            this.mContent = in.readString();
        }

        public static final Creator<RealName> CREATOR = new Creator<RealName>() {
            @Override
            public RealName createFromParcel(Parcel source) {
                return new RealName(source);
            }

            @Override
            public RealName[] newArray(int size) {
                return new RealName[size];
            }
        };
    }

    private static class PhotoUrl implements Parcelable {

        @SerializedName("_content")
        @Expose
        private String mContent;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mContent);
        }

        protected PhotoUrl(Parcel in) {
            this.mContent = in.readString();
        }

        public static final Creator<PhotoUrl> CREATOR = new Creator<PhotoUrl>() {
            @Override
            public PhotoUrl createFromParcel(Parcel source) {
                return new PhotoUrl(source);
            }

            @Override
            public PhotoUrl[] newArray(int size) {
                return new PhotoUrl[size];
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
        dest.writeParcelable(this.mPerson, flags);
    }

    protected UserInfo(Parcel in) {
        super(in);
        this.mPerson = in.readParcelable(Person.class.getClassLoader());
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
