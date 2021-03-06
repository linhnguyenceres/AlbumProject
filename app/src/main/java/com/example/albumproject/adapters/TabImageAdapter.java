package com.example.albumproject.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albumproject.R;
import com.example.albumproject.activities.ImageDetailActivity;
import com.example.albumproject.activities.MainActivity;
import com.example.albumproject.activities.SearchActivity;
import com.example.albumproject.activities.SettingWebViewActivity;
import com.example.albumproject.data.ImageData;
import com.example.albumproject.models.FileModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class TabImageAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater layoutInflater;
    ArrayList<ImageData> listImage;
//    private Animator mCurrentAnimator;
//
//    private int mShortAnimationDuration;


    public TabImageAdapter(Context context, ArrayList<ImageData> listImage) {
        this.context = context;
        this.listImage = listImage;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_tab_image, viewGroup, false);
        }
        ImageView image = view.findViewById(R.id.imgPicture);



        File file1 = new File(listImage.get(i).url);
        if (file1.exists()) {
//            Bitmap bmImg = BitmapFactory.decodeFile(listImage.get(i).url);
//            image.setImageBitmap(Bitmap.createScaledBitmap(bmImg, 200, 200, false));
            String abs = file1.getAbsolutePath();
            Picasso.with(context).load("file://" + abs).fit().centerCrop().into(image);
        }

        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent imageDetailActivity = new Intent(context, ImageDetailActivity.class);
                imageDetailActivity.putExtra("urlImage", listImage.get(i).url);
                context.startActivity(imageDetailActivity);
            }
        });

        return view;


    }


}

