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

    FragmentImage tab;
    FragmentListImage tab1;
    FragmentCreateStory tab2;
    FragmentSetting tab3;

    public MyPagerAdapter(Context ctx, @NonNull FragmentManager fm, int behavior,
                          ArrayList<FileMainModel> listImageData,
                          ArrayList<FileMainModel> listLibraryImage,
                          ArrayList<FolderMainModel> listFolderImage) {
        super(fm, behavior);
        this.ctx = ctx;
        this.listImageData = listImageData;
        this.listLibraryImage = listLibraryImage;
        this.listFolderImage = listFolderImage;

        tab = new FragmentImage(listImageData);
        tab1 = new FragmentListImage(listFolderImage, ctx);
        tab2 = new FragmentCreateStory();
        tab3 = new FragmentSetting();
    }

    public void setNewData(ArrayList<FileMainModel> listImageData,
                           ArrayList<FileMainModel> listLibraryImage,
                           ArrayList<FolderMainModel> listFolderImage) {
        this.listImageData = listImageData;
        this.listLibraryImage = listLibraryImage;
        this.listFolderImage = listFolderImage;
        tab.changeData(this.listImageData);
        tab1.changeData(this.listFolderImage);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return tab1;
            case 2:
                return tab2;
            case 3:
                return tab3;
            default:
                return tab;
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