package com.example.albumproject.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.example.albumproject.R;
import com.example.albumproject.customs.TouchImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

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
                pickImage();
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
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        File file = new File(url);
                        final String where = MediaStore.MediaColumns.DATA + "=?";
                        final String[] selectionArgs = new String[]{
                                file.getAbsolutePath()
                        };
                        final ContentResolver contentResolver = getContentResolver();
                        final Uri filesUri = MediaStore.Files.getContentUri("external");
                        contentResolver.delete(filesUri, where, selectionArgs);
                        ImageDetailActivity.super.onBackPressed();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title)
                .setIcon(icon)
                .setMessage(message)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
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

        startActivity(Intent.createChooser(shareint, "Chia sẻ hình ảnh"));
    }

    private void pickImage(){
        Intent intent = new Intent(ImageDetailActivity.this, DsPhotoEditorActivity.class);
        intent.setData(Uri.parse(urlPath));
        intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Images");
        intent.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR, Color.parseColor("#FF6200EE"));
        intent.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR, Color.parseColor("#FFFFFF"));
        intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, new int[]{
                DsPhotoEditorActivity.TOOL_WARMTH, DsPhotoEditorActivity.TOOL_PIXELATE
        });
        startActivity(intent);
    }
}


