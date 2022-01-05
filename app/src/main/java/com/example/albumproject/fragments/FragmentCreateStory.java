package com.example.albumproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.albumproject.R;

public class FragmentCreateStory extends Fragment implements View.OnClickListener {


    public FragmentCreateStory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_story, container, false);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}