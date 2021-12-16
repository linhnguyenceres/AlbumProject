package com.example.albumproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.albumproject.R;
import com.example.albumproject.adapters.SearchItemAdapter;
import com.example.albumproject.models.FileModel;

import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends Activity {
    View btnBack;
    ListView list;
    EditText editText;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getViews();
        initViews();
        initEvent();
    }

    void getViews() {
        btnBack = (View) findViewById(R.id.btnBack);
        list = (ListView) findViewById(R.id.list);
        editText = (EditText) findViewById(R.id.inputSearch);
    }

    void initViews(){
        SearchItemAdapter adapter = new SearchItemAdapter(this, FileModel.fakeData());
        list.setAdapter(adapter);
    }

    void initEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
