package com.example.albumproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


import com.example.albumproject.R;
import com.example.albumproject.customs.TouchImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageDetailActivity extends Activity {
    View btnBack;
    TouchImageView imViewedImage;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);
        getViews();
        initViews();
        initEvent();
    }

    void getViews() {
        btnBack = (View) findViewById(R.id.btnBack);
        TouchImageView imViewedImage = findViewById(R.id.imViewedImage);
        this.url = getIntent().getStringExtra("urlImage");

        File file1 = new File(url);
        if (file1.exists()) {
            String abs = file1.getAbsolutePath();
            Picasso.with(ImageDetailActivity.this).load("file://" + abs).fit().centerCrop().into(imViewedImage);
        }
    }

    void initViews() {

    }

    void initEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}


