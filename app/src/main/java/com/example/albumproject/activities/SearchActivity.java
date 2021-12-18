package com.example.albumproject.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumproject.R;
import com.example.albumproject.adapters.SearchItemAdapter;
import com.example.albumproject.models.FileModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends Activity {
    View btnBack;
    ListView list;
    EditText editText;
    int REQUEST_PERMISSION = 11;
    int limitList = 10;
    int offsetList = 0;
    boolean isMore = true;
    boolean isLoad = false;
    ArrayList<FileModel> listImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

    void initViews() {
        listImage = new ArrayList<>();
        loadListImage(offsetList, limitList);
        SearchItemAdapter adapter = new SearchItemAdapter(this, listImage);
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

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i == 0) {
                    // check if we reached the top or bottom of the list
                    View v = list.getChildAt(0);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        // reached the top:
                        return;
                    }
                } else if (i2 - i1 == i) {
                    View v = list.getChildAt(i2 - 2);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        if (isLoad == false) {
                            isLoad = true;
                            loadListImage(offsetList, limitList);
//                            isLoad = false;
                            return;
                        }
                    }
                }
            }
        });
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
//                getApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, null),
//                getApplication().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, null),
                getApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC LIMIT " + limit + " OFFSET " + skip),
//                getApplication().getContentResolver().query(MediaStore.Video.Media.INTERNAL_CONTENT_URI, columns, null, null, null)
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
            FileModel data = new FileModel(name, url, date);
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

