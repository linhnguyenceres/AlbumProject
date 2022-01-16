package com.example.albumproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


import com.example.albumproject.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageDetailActivity extends Activity {
    View btnBack;
    Button zoomInButton;
    Button zoomOutButton;
    ImageView imgPicture;
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
        zoomInButton = (Button) findViewById(R.id.zoomInButton);
        zoomOutButton = (Button) findViewById(R.id.zoomOutButton);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
        this.url = getIntent().getStringExtra("urlImage");

        File file1 = new File(url);
        if (file1.exists()) {
            String abs = file1.getAbsolutePath();
            Picasso.with(ImageDetailActivity.this).load("file://" + abs).fit().centerCrop().into(imgPicture);
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
        zoomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPicture.startAnimation(AnimationUtils.loadAnimation(ImageDetailActivity.this, R.anim.zoom_in));
            }
        });
        zoomOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPicture.startAnimation(AnimationUtils.loadAnimation(ImageDetailActivity.this, R.anim.zoom_out));
            }
        });

    }
}


