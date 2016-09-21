package me.tiagomota.getflickred.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhotoInfo extends Base {

    @Expose
    @SerializedName("photo")
    private Info mInfo;

    public String getPostedDate() {
        long epoch = mInfo.mDates.mPosted / 1000;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(epoch * 1000));
    }

    public String getTakenDate() {
        return mInfo.mDates.mTaken;
    }

    public String getDescription() {
        return mInfo.mDescription.mContent;
    }

    public String getComments() {
        return mInfo.mComments.mContent;
    }

    public String getTags() {
        return "" + mInfo.mTags.mTags.size();
    }

    private static class Info implements Parcelable {

        @Expose
        @SerializedName("owner")
        Owner mOwner;

        @Expose
        @SerializedName("title")
        Title mTitle;

        @Expose
        @SerializedName("description")
        Description mDescription;

        @Expose
        @SerializedName("dates")
        Dates mDates;

        @Expose
        @SerializedName("comments")
        Comments mComments;

        @Expose
        @SerializedName("tags")
        Tags mTags;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.mOwner, flags);
            dest.writeParcelable(this.mTitle, flags);
            dest.writeParcelable(this.mDescription, flags);
            dest.writeParcelable(this.mDates, flags);
            dest.writeParcelable(this.mComments, flags);
            dest.writeParcelable(this.mTags, flags);
        }

        protected Info(Parcel in) {
            this.mOwner = in.readParcelable(Owner.class.getClassLoader());
            this.mTitle = in.readParcelable(Title.class.getClassLoader());
            this.mDescription = in.readParcelable(Description.class.getClassLoader());
            this.mDates = in.readParcelable(Dates.class.getClassLoader());
            this.mComments = in.readParcelable(Comments.class.getClassLoader());
            this.mTags = in.readParcelable(Tags.class.getClassLoader());
        }

        public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() {
            @Override
            public Info createFromParcel(Parcel source) {
                return new Info(source);
            }

            @Override
            public Info[] newArray(int size) {
                return new Info[size];
            }
        };
    }

    private static class Owner implements Parcelable {

        @Expose
        @SerializedName("nsid")
        String mNsid;

        @Expose
        @SerializedName("username")
        String mUsername;

        @Expose
        @SerializedName("realname")
        String mRealName;

        @Expose
        @SerializedName("location")
        String mLocation;

        @Expose
        @SerializedName("iconserver")
        String mIconServer;

        @Expose
        @SerializedName("iconfarm")
        String mIconFarm;

        @Expose
        @SerializedName("path_alias")
        String mPathAlias;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mNsid);
            dest.writeString(this.mUsername);
            dest.writeString(this.mRealName);
            dest.writeString(this.mLocation);
            dest.writeString(this.mIconServer);
            dest.writeString(this.mIconFarm);
            dest.writeString(this.mPathAlias);
        }

        protected Owner(Parcel in) {
            this.mNsid = in.readString();
            this.mUsername = in.readString();
            this.mRealName = in.readString();
            this.mLocation = in.readString();
            this.mIconServer = in.readString();
            this.mIconFarm = in.readString();
            this.mPathAlias = in.readString();
        }

        public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
            @Override
            public Owner createFromParcel(Parcel source) {
                return new Owner(source);
            }

            @Override
            public Owner[] newArray(int size) {
                return new Owner[size];
            }
        };
    }

    private static class Title implements Parcelable {

        @SerializedName("_content")
        @Expose
        String content;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.content);
        }

        protected Title(Parcel in) {
            this.content = in.readString();
        }

        public static final Parcelable.Creator<Title> CREATOR = new Parcelable.Creator<Title>() {
            @Override
            public Title createFromParcel(Parcel source) {
                return new Title(source);
            }

            @Override
            public Title[] newArray(int size) {
                return new Title[size];
            }
        };
    }

    private static class Description implements Parcelable {

        @SerializedName("_content")
        @Expose
        String mContent;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mContent);
        }

        protected Description(Parcel in) {
            this.mContent = in.readString();
        }

        public static final Parcelable.Creator<Description> CREATOR = new Parcelable.Creator<Description>() {
            @Override
            public Description createFromParcel(Parcel source) {
                return new Description(source);
            }

            @Override
            public Description[] newArray(int size) {
                return new Description[size];
            }
        };
    }

    private static class Comments implements Parcelable {

        @SerializedName("_content")
        @Expose
        String mContent;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mContent);
        }

        protected Comments(Parcel in) {
            this.mContent = in.readString();
        }

        public static final Parcelable.Creator<Comments> CREATOR = new Parcelable.Creator<Comments>() {
            @Override
            public Comments createFromParcel(Parcel source) {
                return new Comments(source);
            }

            @Override
            public Comments[] newArray(int size) {
                return new Comments[size];
            }
        };
    }

    private static class Tags implements Parcelable {

        @SerializedName("tag")
        @Expose
        List<Tag> mTags = new ArrayList<Tag>();

        static class Tag implements Parcelable {

            @SerializedName("id")
            @Expose
            String mId;

            @SerializedName("author")
            @Expose
            String mAuthor;

            @SerializedName("authorname")
            @Expose
            String mAuthorName;

            @SerializedName("raw")
            @Expose
            String mRaw;

            @SerializedName("_content")
            @Expose
            String mContent;


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.mId);
                dest.writeString(this.mAuthor);
                dest.writeString(this.mAuthorName);
                dest.writeString(this.mRaw);
                dest.writeString(this.mContent);
            }

            protected Tag(Parcel in) {
                this.mId = in.readString();
                this.mAuthor = in.readString();
                this.mAuthorName = in.readString();
                this.mRaw = in.readString();
                this.mContent = in.readString();
            }

            public static final Creator<Tag> CREATOR = new Creator<Tag>() {
                @Override
                public Tag createFromParcel(Parcel source) {
                    return new Tag(source);
                }

                @Override
                public Tag[] newArray(int size) {
                    return new Tag[size];
                }
            };
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.mTags);
        }

        protected Tags(Parcel in) {
            this.mTags = in.createTypedArrayList(Tag.CREATOR);
        }

        public static final Parcelable.Creator<Tags> CREATOR = new Parcelable.Creator<Tags>() {
            @Override
            public Tags createFromParcel(Parcel source) {
                return new Tags(source);
            }

            @Override
            public Tags[] newArray(int size) {
                return new Tags[size];
            }
        };
    }

    private static class Dates implements Parcelable {

        @SerializedName("posted")
        @Expose
        long mPosted;

        @SerializedName("taken")
        @Expose
        String mTaken;

        @SerializedName("takengranularity")
        @Expose
        int mTakenGranularity;

        @SerializedName("takenunknown")
        @Expose
        int mTakenUnknown;

        @SerializedName("lastupdate")
        @Expose
        long mLastUpdate;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.mPosted);
            dest.writeString(this.mTaken);
            dest.writeInt(this.mTakenGranularity);
            dest.writeInt(this.mTakenUnknown);
            dest.writeLong(this.mLastUpdate);
        }

        protected Dates(Parcel in) {
            this.mPosted = in.readLong();
            this.mTaken = in.readString();
            this.mTakenGranularity = in.readInt();
            this.mTakenUnknown = in.readInt();
            this.mLastUpdate = in.readLong();
        }

        public static final Parcelable.Creator<Dates> CREATOR = new Parcelable.Creator<Dates>() {
            @Override
            public Dates createFromParcel(Parcel source) {
                return new Dates(source);
            }

            @Override
            public Dates[] newArray(int size) {
                return new Dates[size];
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
        dest.writeParcelable(this.mInfo, flags);
    }

    protected PhotoInfo(Parcel in) {
        super(in);
        this.mInfo = in.readParcelable(Info.class.getClassLoader());
    }

    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel source) {
            return new PhotoInfo(source);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };
}
