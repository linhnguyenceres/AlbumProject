package com.example.albumproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albumproject.R;
import com.example.albumproject.models.FileModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class SearchItemAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater layoutInflater;
    ArrayList<FileModel> files;

    public SearchItemAdapter(Context context, ArrayList<FileModel> files) {
        this.context = context;
        this.files = files;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return files.size();
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
            view = layoutInflater.inflate(R.layout.item_search, viewGroup, false);
        }
        ImageView image = view.findViewById(R.id.image);

        FileModel file = files.get(i);
        File imgFile = new File(file.url);
        if (imgFile.exists()) {
            String abs = imgFile.getAbsolutePath();
            Picasso.with(context).load("file://" + abs).fit().centerCrop().into(image);
        }
        TextView txtName = view.findViewById(R.id.txtName);
        txtName.setText(file.name);
//        TextView txtDate = view.findViewById(R.id.txtDate);
//        txtDate.setText(file.date);

        return view;
    }
}
