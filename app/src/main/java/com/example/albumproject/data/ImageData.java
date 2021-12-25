package com.example.albumproject.data;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.example.albumproject.models.FileModel;

import java.util.ArrayList;

public class ImageData implements Parcelable {
    public String name;
    public String url;
    public String date;

    public ImageData(String name, String url, String date) {
        this.name = name;
        this.url = url;
        this.date = date;
    }

    protected ImageData(Parcel in) {
        name = in.readString();
        url = in.readString();
        date = in.readString();
    }

    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel in) {
            return new ImageData(in);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

//    public static FileModel[] fakeData() {
//        FileModel[] list = new FileModel[5];
//        return list;
//    }

    public static ArrayList<FileModel> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<FileModel> listOfAllImages = new ArrayList<FileModel>();
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        Integer index = 0;
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            FileModel data = new FileModel("Anh" + index, absolutePathOfImage, "12/12/2021");
            listOfAllImages.add(data);
            index++;
        }
        return listOfAllImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeString(date);
    }
}

