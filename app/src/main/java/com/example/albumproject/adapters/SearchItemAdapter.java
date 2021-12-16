package com.example.albumproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albumproject.R;
import com.example.albumproject.models.FileModel;

public class SearchItemAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater layoutInflater;
    FileModel[] files;

    public SearchItemAdapter(Context context, FileModel[] files){
        this.context = context;
        this.files = files;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return files.length;
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
            view = layoutInflater.inflate(R.layout.item_search, viewGroup, false);
        }
        ImageView image= view.findViewById(R.id.image);
        image.setBackgroundResource(files[i].url);
        TextView txtName = view.findViewById(R.id.txtName);
        txtName.setText(files[i].name);
        TextView txtDate = view.findViewById(R.id.txtDate);
        txtDate.setText(files[i].date);
        return view;
    }
}
