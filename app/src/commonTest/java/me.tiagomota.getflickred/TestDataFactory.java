package me.tiagomota.getflickred;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.tiagomota.getflickred.data.model.Photo;
import me.tiagomota.getflickred.data.model.PhotoInfo;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.data.model.PhotosList;
import me.tiagomota.getflickred.data.model.User;

public class TestDataFactory {

    private static TestDataFactory sInstance;
    private Gson mGson;

    public static TestDataFactory get() {
        if (sInstance == null) {
            sInstance = new TestDataFactory();
            sInstance.mGson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
        }

        return sInstance;
    }


    public User makeUser() {
        return mGson.fromJson(DataHelper.USER, User.class);
    }

    public PhotosList makePhotosList(final int elems) {
        PhotosList photosList = mGson.fromJson(DataHelper.PHOTOS_LIST, PhotosList.class);
        for (int i = 0; i < elems; i++) {
            photosList.getPhotos().add(TestDataFactory.get().makePhoto());
        }
        return photosList;
    }

    public Photo makePhoto() {
        return mGson.fromJson(DataHelper.PHOTO, Photo.class);
    }

    public PhotoInfo makePhotoInfo() {
        return mGson.fromJson(DataHelper.PHOTO_INFO, PhotoInfo.class);
    }

    public PhotoSize makePhotoSize() {
        return mGson.fromJson(DataHelper.PHOTO_SIZE, PhotoSize.class);
    }

    /**
     * Utility method that search for the given JSON file and reads it.
     *
     * @param name String
     * @return String
     */
    private String readFile(final String name) {
        String result = "";
        BufferedReader reader;

        try {
            String currentLine;
            reader = new BufferedReader(new InputStreamReader(
                    this.getClass()
                            .getClassLoader()
                            .getResourceAsStream(name)
            ));

            while ((currentLine = reader.readLine()) != null) {
                result += currentLine;
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading asset with name: " + name);
        }

        return result;
    }
}
