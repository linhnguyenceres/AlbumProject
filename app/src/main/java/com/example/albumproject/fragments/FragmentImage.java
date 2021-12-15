package com.example.albumproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.albumproject.R;
import com.example.albumproject.adapters.TabImageAdapter;

public class FragmentImage extends Fragment {

    Context context;

    int[] myImages = {R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example,
            R.mipmap.ic_example};

    public FragmentImage() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        GridView gridView;
        gridView = (GridView) rootView.findViewById(R.id.gridImage);
        gridView.setAdapter(new TabImageAdapter(getActivity(), myImages));

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
