package com.example.albumproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.example.albumproject.R;

public class SettingActivity extends Activity {
    View btnBack;
    ConstraintLayout groupTerm;
    ConstraintLayout groupPolicy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getViews();
        initClick();
    }

    void getViews() {
        btnBack = (View) findViewById(R.id.btnBack);
        groupTerm = (ConstraintLayout) findViewById(R.id.groupTerm);
        groupPolicy = (ConstraintLayout) findViewById(R.id.groupPolicy);
    }

    void initClick() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        groupPolicy.setOnClickListener(view1 -> {
            SettingWebViewActivity settingView = new SettingWebViewActivity();
            Intent it = new Intent(SettingActivity.this,settingView.getClass()) ;
            it.putExtra("title","Chính sách bảo mật");
            it.putExtra("url","https://vi.lipsum.com/");
            startActivity(it);
        });

        groupTerm.setOnClickListener(view1 -> {
            SettingWebViewActivity settingView = new SettingWebViewActivity();
            Intent it = new Intent(SettingActivity.this,settingView.getClass()) ;
            it.putExtra("title","Điều khoản dịch vụ");
            it.putExtra("url","https://www.lipsum.com");
            startActivity(it);
        });
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }
}
