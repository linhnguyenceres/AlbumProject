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
import com.example.albumproject.adapters.TabImageParentAdapter;
import com.example.albumproject.data.ImageData;
import com.example.albumproject.models.FileMainModel;
import com.example.albumproject.models.FileModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class FragmentImage extends Fragment {

    Context context;
    MainActivity main;
    boolean isLoad = false;


    int[] myImages = {R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example};

    ArrayList<FileMainModel> list;

//    Bundle bundle;

    public FragmentImage(ArrayList<FileMainModel> list) {
        this.list = list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_image, container, false);

        ListView listVew = (ListView) rootView.findViewById(R.id.list);

        TabImageParentAdapter adapter = new TabImageParentAdapter(getActivity(), list);
        listVew.setAdapter(adapter);

        listVew.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i == 0) {
                    // check if we reached the top or bottom of the list
                    View v = listVew.getChildAt(0);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        // reached the top:
                        return;
                    }
                } else if (i2 - i1 == i) {
                    View v = listVew.getChildAt(i2 - 2);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        if (isLoad == false) {
                            isLoad = true;
                            main.onMsgFromFragToMain("FRAGEMENT_IMAGE","loadMore");
                            isLoad = false;
                            return;
                        }
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
