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
//                try {
//                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                    sharingIntent.setType("image/*");
//                    sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(urlPath));
//                    startActivity(Intent.createChooser(sharingIntent, "Share Image:"));
//                } catch (Exception e) {
//                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e("ERROR", e.getMessage());
//                }

//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("image/*");
////                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
////                url.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
////                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
////                try {
////                    f.createNewFile();
////                    FileOutputStream fo = new FileOutputStream(f);
////                    fo.write(bytes.toByteArray());
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(urlPath));
//                startActivity(Intent.createChooser(share, "Share Image"));
                //                File file = new File(url);
//                Uri uri;// w w w.j  a v a 2  s. co m
//                if (Build.VERSION.SDK_INT >= 24) {
//                    uri = FileProvider.getUriForFile(mContext,
//                            BuildConfig.APPLICATION_ID + ".provider", file);
//                } else {
//                    uri = Uri.fromFile(file);
//                }

                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    File file1 = new File(url);
                    String abs = file1.getAbsolutePath();
                    Uri uri = Uri.parse("file:/" + abs);
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.setType("image/*");
                    startActivity(Intent.createChooser(intent, "Share image"));
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
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

}


