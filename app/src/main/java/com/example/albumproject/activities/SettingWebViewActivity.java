package com.example.albumproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albumproject.R;

public class SettingWebViewActivity extends Activity {
    TextView txtTitle;
    View btnClose;
    WebView webView;
    String title;
    String url;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_webview);
        getViews();
        initClick();
    }

    void getViews() {
        this.title = getIntent().getStringExtra("title");
        this.url = getIntent().getStringExtra("url");
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(title);
        btnClose = (View) findViewById(R.id.btnClose);
        webView = (WebView) findViewById(R.id.wv);
        webView.loadUrl(url);
    }

    void initClick() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
