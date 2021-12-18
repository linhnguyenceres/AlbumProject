package com.example.albumproject.models;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.example.albumproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileModel {
    public String name;
    public String url;
    public String date;

   public FileModel(String name, String url,String date){
        this.name = name;
        this.url = url;
        this.date = date;
   }

   public static FileModel[] fakeData(){
        FileModel[] list = new FileModel[5];
        return list;
   }

    public static ArrayList<FileModel> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<FileModel> listOfAllImages = new ArrayList<FileModel>();
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        Integer index = 0;
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            FileModel data = new FileModel("Anh" + index,absolutePathOfImage,"12/12/2021");
            listOfAllImages.add(data);
            index++;
        }
        return listOfAllImages;
    }
}
