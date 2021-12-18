package com.example.albumproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albumproject.R;
import com.example.albumproject.models.FileModel;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
        image.setTag(file.url);
        new LoadImage().execute(image);
//        File imgFile = new File(file.url);
//        if(imgFile.exists()){
//            try {
//                image.setImageURI(Uri.fromFile(imgFile));
//            }catch (Exception e){
//                int a = 1;
//            }
//        }
        TextView txtName = view.findViewById(R.id.txtName);
        txtName.setText(file.name);
        TextView txtDate = view.findViewById(R.id.txtDate);
        txtDate.setText(file.date);

        return view;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private ImageView imv;
        private String path;


        @Override
        protected Bitmap doInBackground(Object... params) {
            imv = (ImageView) params[0];

            path = imv.getTag().toString();

            Bitmap bitmap = null;
            File file = new File(path);

            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null && imv != null) {
                imv.setVisibility(View.VISIBLE);
                imv.setImageBitmap(result);
            } else {
                imv.setVisibility(View.GONE);
            }
        }
    }

}
