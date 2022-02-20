package com.example.albumproject.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.albumproject.BuildConfig;
import com.example.albumproject.R;
import com.example.albumproject.customs.TouchImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ImageDetailActivity extends Activity {
    View btnBack;
    TouchImageView imViewedImage;
    String url;
    View btnShare;
    View btnEdit;
    View btnDelete;
    String urlPath;
    private static Context mContext;



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
        imViewedImage = findViewById(R.id.imViewedImage);
        this.url = getIntent().getStringExtra("urlImage");
        btnShare = (View) findViewById(R.id.btnShare);
        btnEdit = (View) findViewById(R.id.btnEdit);
        btnDelete = (View) findViewById(R.id.btnDelete);

        File file1 = new File(url);
        if (file1.exists()) {
            String abs = file1.getAbsolutePath();
            Picasso.with(ImageDetailActivity.this).load("file://" + abs).fit().centerCrop().into(imViewedImage);
            urlPath = "file://" + abs;
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

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImageDetailActivity.this, EditImageActivity.class));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertView("Bạn muốn xóa ảnh?", null, "Xác nhận xóa ảnh");
            }
        });
    }

    private void alertView(String message, Drawable icon, String title) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title)
                .setIcon(icon)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
//                        String path = urlPath;
//                        File file = new File(path);
//                        if (ContextCompat.checkSelfPermission(ImageDetailActivity.this
//                                , Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                            file.delete();
//                            if (file.delete()) {
//                                Log.e("filedel", "file Deleted :");
//                            } else {
//                                Log.e("filedel", "file  not Deleted :");
//                            }
//                        }

//                        File file = new File(url);
//                        if(file.exists()){
//                            deleteFile(url);
//                            Log.e("filedel", "file Deleted :");
//                        }else{
//                            Log.e("filedel", "file  not Deleted :");
//                        }

                        File file = new File(url);
//                        Log.e("filePath", file.getAbsolutePath());
//                        if (file.exists()) {
//                            file.delete();
//                            deleteDirectory(file);
//                            if (file.delete()) {
//                                Log.e("filedel", "file Deleted :");
//                            } else {
//                                Log.e("filedel", "file  not Deleted :");
//                            }
//                        } else {
//                            Log.e("filedel", "file  not Exists :");
//                        }

//                        if(deleteDirectory(file)){
//                            Log.e("filedel", "file  not Deleted :");
//                        }else{
//                            Log.e("filedel", "file  not Exists :");
//                        }

                        final String where = MediaStore.MediaColumns.DATA + "=?";
                        final String[] selectionArgs = new String[]{
                                file.getAbsolutePath()
                        };
                        final ContentResolver contentResolver = getContentResolver();
                        final Uri filesUri = MediaStore.Files.getContentUri("external");
                        contentResolver.delete(filesUri, where, selectionArgs);
                    }
                }).show();
    }

    static public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    private void image() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        BitmapDrawable drawable = (BitmapDrawable) imViewedImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File f = new File(getExternalCacheDir() + "/" + getResources().getString(R.string.app_name) + ".png");
        Intent shareint;

        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            shareint = new Intent(Intent.ACTION_SEND);
            shareint.setType("image/*");
            shareint.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            shareint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        startActivity(Intent.createChooser(shareint, "share image"));
    }

}


