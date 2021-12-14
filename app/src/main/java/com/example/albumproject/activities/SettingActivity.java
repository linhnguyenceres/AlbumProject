package com.example.albumproject.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.albumproject.R;

public class SettingActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        ActionBar action1 = getSupportActionBar();
        action1.setTitle("Cài đặt");
        action1.setDisplayHomeAsUpEnabled(true);

    }
}
