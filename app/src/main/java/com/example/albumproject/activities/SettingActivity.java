package com.example.albumproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.example.albumproject.R;

public class SettingActivity extends Activity {
    View btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        ActionBar action1 = getSupportActionBar();
//        action1.setTitle(getString(R.string.setting));
//        action1.setDisplayHomeAsUpEnabled(true);
        getViews();
        initClick();
    }

    void getViews() {
        btnBack = (View) findViewById(R.id.btnBack);
    }

    void initClick() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }
}
