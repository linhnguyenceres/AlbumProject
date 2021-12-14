package com.example.albumproject.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.albumproject.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends Activity {
    TextView txtTitle;
    View btnBack;
    View btnSearch;
    View btnCamera;
    View btnMore;
    TabLayout tabLayout;
    ViewPager viewPager;

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
    }

    private void addControl() {
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        FragmentManager manager = getSupportFragmentManager();
//        PagerAdapter adapter = new PagerAdapter(manager);
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setTabsFromPagerAdapter(adapter);//deprecated
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
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
                Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_LONG).show();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_LONG).show();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
//                Toast.makeText(MainActivity.this, "More", Toast.LENGTH_LONG).show();
            }
        });
    }

}