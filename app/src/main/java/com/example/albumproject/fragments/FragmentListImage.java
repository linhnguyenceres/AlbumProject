package com.example.albumproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.albumproject.R;
import com.example.albumproject.activities.MainActivity;
import com.example.albumproject.adapters.TabImageParentAdapter;
import com.example.albumproject.adapters.TabListImageParentAdapter;
import com.example.albumproject.models.FileMainModel;

import java.util.ArrayList;

public class FragmentListImage extends Fragment {

    Context context;
    MainActivity main;
    boolean isLoad = false;


    ArrayList<FileMainModel> list;

//    Bundle bundle;

    public FragmentListImage(ArrayList<FileMainModel> list) {
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

        View rootView = inflater.inflate(R.layout.fragment_list_image, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        TabListImageParentAdapter adapter = new TabListImageParentAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i == 0) {
                    // check if we reached the top or bottom of the list
                    View v = listView.getChildAt(0);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        // reached the top:
                        return;
                    }
                } else if (i2 - i1 == i) {
                    View v = listView.getChildAt(i2 - 2);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        if (isLoad == false) {
                            isLoad = true;
                            main.onMsgFromFragToMain("FRAGMENT_IMAGE", "loadMore");
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
