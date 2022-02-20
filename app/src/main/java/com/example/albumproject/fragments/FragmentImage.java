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
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
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
import com.example.albumproject.models.FolderMainModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentImage extends Fragment {

    Context context;
    MainActivity main;
    boolean isLoad = false;
    final Handler handler = new Handler(Looper.getMainLooper());
    ListView listView;

//    int[] myImages = {R.mipmap.ic_example,
//            R.mipmap.ic_example,
//            R.mipmap.ic_example,
//            R.mipmap.ic_example,
//            R.mipmap.ic_example,
//            R.mipmap.ic_example};

    ArrayList<FileMainModel> list;

//    Bundle bundle;

    public FragmentImage(ArrayList<FileMainModel> list) {
        this.list = list;
    }

    public FragmentImage() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity) getActivity();
    }

    public void changeData(ArrayList<FileMainModel> list){
        this.list = list;
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);

        if(list != null){
            TabImageParentAdapter adapter = new TabImageParentAdapter(getActivity(), list);
            listView.setAdapter(adapter);
        }


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            Timer timer = new Timer();
            final long DELAY = 1000; // Milliseconds

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//                if (isLoad == false) {
//
//                    if (i == 0 && i1 <= 1) {
//                        // check if we reached the top or bottom of the list
//                        View v = listView.getChildAt(0);
//                        int offset = (v == null) ? 0 : v.getBottom();
//                        int height = absListView.getHeight();
//                        if (offset == height) {
//                            isLoad = true;
//                            timer = new Timer();
//                            timer.schedule(
//                                    new TimerTask() {
//                                        @Override
//                                        public void run() {
//                                            handler.post(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    main.onMsgFromFragToMain("FRAGMENT_IMAGE", "loadMore");
//                                                    isLoad = false;
//                                                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
//                                                }
//                                            });
//                                        }
//                                    },
//                                    DELAY
//                            );
//                        }
//                    } else if (i2 - i1 == i) {
//                        View v = listView.getChildAt(i2 - 1);
//                        int offset = (v == null) ? 0 : v.getBottom();
//                        int height = absListView.getHeight();
//                        if (offset == height) {
//                            if (isLoad == false) {
//                                isLoad = true;
//                                timer = new Timer();
//                                timer.schedule(
//                                        new TimerTask() {
//                                            @Override
//                                            public void run() {
//                                                handler.post(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        main.onMsgFromFragToMain("FRAGMENT_IMAGE", "loadMore");
//                                                        isLoad = false;
//                                                        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
//                                                    }
//                                                });
//                                            }
//                                        },
//                                        DELAY
//                                );
//                            }
//                        }
//                    }
//                }
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
