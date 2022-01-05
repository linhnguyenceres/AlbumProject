package com.example.albumproject.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.albumproject.fragments.FragmentCreateStory;
import com.example.albumproject.fragments.FragmentImage;
import com.example.albumproject.fragments.FragmentListImage;
import com.example.albumproject.models.FileMainModel;
import com.example.albumproject.fragments.FragmentSetting;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FileMainModel> listImageData;
    ArrayList<FileMainModel> listLibraryImage;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior,
                          ArrayList<FileMainModel> listImageData,
                          ArrayList<FileMainModel> listLibraryImage) {
        super(fm, behavior);
        this.listImageData = listImageData;
        this.listLibraryImage = listLibraryImage;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new FragmentListImage(listLibraryImage);
            case 2:
                return new FragmentCreateStory();
            case 3:
                return new FragmentSetting();
            default:
                return new FragmentImage(listImageData);
        }
    }

    @Override
    public int getCount() {
        return 4;
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
                title = "Tạo story";
                break;
            case 3:
                title = "Cài đặt";
                break;
        }
        return title;
    }

}