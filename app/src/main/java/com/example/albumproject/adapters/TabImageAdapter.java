package com.example.albumproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.albumproject.R;


public class TabImageAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater layoutInflater;
    int[] images;

    public TabImageAdapter(Context context, int[] images){
        this.context = context;
        this.images = images;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
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
        if(view == null){
            view = layoutInflater.inflate(R.layout.item_tab_image, viewGroup, false);
        }

        ImageView imageView = view.findViewById(R.id.imgPicture);
        imageView.setBackgroundResource(images[i]);
        return view;
    }
}
