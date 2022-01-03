package com.example.albumproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.albumproject.R;
import com.example.albumproject.data.ImageData;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class TabListImageAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater layoutInflater;
    ArrayList<ImageData> listImage;


    public TabListImageAdapter(Context context, ArrayList<ImageData> listImage){
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
        File file1 = new  File(listImage.get(i).url);
        if(file1.exists()){
            String abs = file1.getAbsolutePath();
            Picasso.with(context).load("file://" + abs).fit().centerCrop().into(image);
        };
        return view;
    }
}
