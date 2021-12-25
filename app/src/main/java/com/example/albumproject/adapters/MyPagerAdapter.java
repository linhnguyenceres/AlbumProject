package com.example.albumproject.adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.albumproject.R;
import com.example.albumproject.data.ImageData;
import com.example.albumproject.fragments.FragmentImage;
import com.example.albumproject.fragments.FragmentListImage;
import com.example.albumproject.fragments.FragmentOnline;
import com.example.albumproject.models.FileModel;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<ImageData> listImageData;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<ImageData> listImageData) {
        super(fm, behavior);
        this.listImageData = listImageData;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentImage(listImageData);
            case 1:
                return new FragmentListImage();
            case 2:
                return new FragmentOnline();
            default:
                return new FragmentImage(listImageData);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Ảnh";
                break;
            case 1:
                title = "Tập ảnh";
                break;
            case 2:
                title = "Trực tuyến";
                break;
        }
        return title;
    }

}