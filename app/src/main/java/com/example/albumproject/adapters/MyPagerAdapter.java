package com.example.albumproject.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.albumproject.fragments.FragmentCreateStory;
import com.example.albumproject.fragments.FragmentImage;
import com.example.albumproject.fragments.FragmentListImage;
import com.example.albumproject.models.FileMainModel;
import com.example.albumproject.fragments.FragmentSetting;
import com.example.albumproject.models.FolderMainModel;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FileMainModel> listImageData;
    ArrayList<FileMainModel> listLibraryImage;
    ArrayList<FolderMainModel> listFolderImage;
    Context ctx;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public MyPagerAdapter(Context ctx,@NonNull FragmentManager fm, int behavior,
                          ArrayList<FileMainModel> listImageData,
                          ArrayList<FileMainModel> listLibraryImage,
                          ArrayList<FolderMainModel> listFolderImage) {
        super(fm, behavior);
        this.ctx = ctx;
        this.listImageData = listImageData;
        this.listLibraryImage = listLibraryImage;
        this.listFolderImage = listFolderImage;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new FragmentListImage(listFolderImage,ctx);
//            case 2:
//                return new FragmentCreateStory();
            case 2:
                return new FragmentSetting();
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
//            case 2:
//                title = "Tạo story";
//                break;
            case 2:
                title = "Cài đặt";
                break;
        }
        return title;
    }

}