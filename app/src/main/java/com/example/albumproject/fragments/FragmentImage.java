package com.example.albumproject.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.albumproject.R;
import com.example.albumproject.activities.MainActivity;
import com.example.albumproject.adapters.SearchItemAdapter;
import com.example.albumproject.adapters.TabImageAdapter;
import com.example.albumproject.data.ImageData;
import com.example.albumproject.models.FileModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class FragmentImage extends Fragment {

    Context context;


    int[] myImages = {R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example};

    ArrayList<ImageData> listImage;

//    Bundle bundle;

    public FragmentImage(ArrayList<ImageData> listImage) {
        this.listImage = listImage;
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        GridView gridView;
        gridView = (GridView) rootView.findViewById(R.id.gridImage);

        TabImageAdapter adapter = new TabImageAdapter(getActivity(), listImage);
        gridView.setAdapter(adapter);


//        list.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//                if (i == 0) {
//                    // check if we reached the top or bottom of the list
//                    View v = list.getChildAt(0);
//                    int offset = (v == null) ? 0 : v.getTop();
//                    if (offset == 0) {
//                        // reached the top:
//                        return;
//                    }
//                } else if (i2 - i1 == i) {
//                    View v = list.getChildAt(i2 - 2);
//                    int offset = (v == null) ? 0 : v.getTop();
//                    if (offset == 0) {
//                        if (isLoad == false) {
//                            isLoad = true;
//                            loadListImage(offsetList, limitList);
////                            isLoad = false;
//                            return;
//                        }
//                    }
//                }
//            }
//        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


//    public void loadListImage(int skip, int limit) {
//        if (ActivityCompat.checkSelfPermission(getContext(),
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions( //Method of Fragment
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    REQUEST_PERMISSION
//            );
//            return;
//        }
//
////        if (ContextCompat.checkSelfPermission((Activity)context,
////                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////            requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
////                    REQUEST_PERMISSION);
////            return;
////        }
//        final String[] columns = {MediaStore.Images.Media.DATA,
//                MediaStore.Images.Media.DATE_ADDED,
//                MediaStore.Images.Media.BUCKET_ID,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
//                MediaStore.Images.Media.DISPLAY_NAME,
//                MediaStore.Images.Media.SIZE,
//                MediaStore.Images.Media.DATE_TAKEN
//        };
//        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
//        MergeCursor cursor = new MergeCursor(new Cursor[]{
//                getContextOfApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC LIMIT " + limit + " OFFSET " + skip),
//        });
//        if (cursor.getCount() == 0) {
//            isMore = false;
//            return;
//        }
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            String url = cursor.getString(column_index);
//            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
//            String name = cursor.getString(column_index);
//            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
//            String date = cursor.getString(column_index);
//            ImageData data = new ImageData(name, url, date);
//            listImage.add(data);
//            cursor.moveToNext();
//        }
//        offsetList += limit;
//        if (listImage.size() % 10 == 0) {
//            isLoad = false;
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                loadListImage(offsetList, limitList);
//            } else {
//                // User refused to grant permission.
//            }
//        }
//    }
//

    }
