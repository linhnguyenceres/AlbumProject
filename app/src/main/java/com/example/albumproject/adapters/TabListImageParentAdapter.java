package com.example.albumproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.albumproject.R;
import com.example.albumproject.models.FileMainModel;

import java.util.ArrayList;

public class TabListImageParentAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater layoutInflater;
    ArrayList<FileMainModel> list;
    TabListImageAdapter adapter;


    public TabListImageParentAdapter(Context context, ArrayList<FileMainModel> list){
        this.context = context;
        this.list = list;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
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
            view = layoutInflater.inflate(R.layout.fragment_list_image_item, viewGroup, false);
        }
        TextView txtLibrary = view.findViewById(R.id.txtLibrary);
        txtLibrary.setText(list.get(i).library);

        GridView gridView;
        gridView = (GridView) view.findViewById(R.id.gridImage);
        adapter = new TabListImageAdapter(context, list.get(i).files);
        gridView.setAdapter(adapter);

        return view;
    }
}