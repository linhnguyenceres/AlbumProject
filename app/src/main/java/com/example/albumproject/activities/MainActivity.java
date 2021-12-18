package com.example.albumproject.activities;


import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.albumproject.R;
import com.example.albumproject.adapters.MyPagerAdapter;
import com.example.albumproject.data.ImageData;
import com.example.albumproject.fragments.FragmentImage;
import com.example.albumproject.models.FileModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    TextView txtTitle;
    View btnBack;
    View btnSearch;
    View btnCamera;
    View btnMore;
    TabLayout tabLayout;
    ViewPager viewPager;
    int REQUEST_PERMISSION = 11;
    ArrayList<ImageData> listImage;
    int limitList = 10;
    int offsetList = 0;
    boolean isMore = true;
    boolean isLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        addControl();
        initClick();
    }

    void getViews() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnBack = (View) findViewById(R.id.btnBack);
        btnSearch = (View) findViewById(R.id.btnSearch);
        btnCamera = (View) findViewById(R.id.btnCamera);
        btnMore = (View) findViewById(R.id.btnMore);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        listImage = new ArrayList<>();
        loadListImage(offsetList, limitList);
    }

    private void addControl() {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, listImage);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    void initClick() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Back", Toast.LENGTH_LONG).show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemChoose:
                Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemSetting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return true;
            default:
                return false;
        }
    }


    public void loadListImage(int skip, int limit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return;
        }
        int item = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean permission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED;
        final String[] columns = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_TAKEN
        };
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        MergeCursor cursor = new MergeCursor(new Cursor[]{
                getApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC LIMIT " + limit + " OFFSET " + skip),
        });
        if (cursor.getCount() == 0) {
            isMore = false;
            return;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String url = cursor.getString(column_index);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            String name = cursor.getString(column_index);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
            String date = cursor.getString(column_index);
            ImageData data = new ImageData(name, url, date);
            listImage.add(data);
            cursor.moveToNext();
        }
        offsetList += limit;
        if (listImage.size() % 10 == 0) {
            isLoad = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadListImage(offsetList, limitList);
            } else {
                // User refused to grant permission.
            }
        }
    }

}

