package com.example.albumproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumproject.R;
import com.example.albumproject.models.FileModel;
import com.example.albumproject.models.FolderMainModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailListImageActivity extends Activity {
    RecyclerView recyclerViewDetailListImage;
    private Context mContext;
    protected DetailAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    ArrayList<FileModel> list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list_image);
        getViews();
    }

    void getViews() {
        recyclerViewDetailListImage = findViewById(R.id.recyclerViewDetailListImage);

        Intent i = getIntent();
        FolderMainModel model = (FolderMainModel) i.getSerializableExtra("listDataFolderImage");
        if(model != null) {
            this.list = this.getAllImagesByFolder(model.getPath());
            mAdapter = new DetailAdapter(list);
            recyclerViewDetailListImage.setAdapter(mAdapter);
            mLayoutManager = new GridLayoutManager(this, 2);
            recyclerViewDetailListImage.setLayoutManager(mLayoutManager);
        }
    }


    public ArrayList<FileModel> getAllImagesByFolder(String path){
        ArrayList<FileModel> images = new ArrayList<>();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_TAKEN
        };;
        Cursor cursor = this.getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
        try {
            cursor.moveToFirst();
            do{
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String url = cursor.getString(column_index);
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                String name = cursor.getString(column_index);
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
                Long date = cursor.getLong(column_index);
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
                Long dateAdd = cursor.getLong(column_index);
                FileModel data = new FileModel(name, url, dateAdd);
                images.add(data);
                cursor.moveToNext();

            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
        private static final String TAG = "DetailAdapter";

        private ArrayList<FileModel> mDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imgView;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Item " + getAdapterPosition() + " clicked.", Toast.LENGTH_SHORT).show();
                    }
                });

                imgView = (ImageView) v.findViewById(R.id.imgDetailLibraryItem);
            }

            public ImageView getImgView(){ return this.imgView; }

        }

        public DetailAdapter(ArrayList<FileModel> dataSet) {
            mDataSet = dataSet;
        }

        @Override
        public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_detail_list_image, viewGroup, false);

            return new DetailAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DetailAdapter.ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            final FileModel folder = mDataSet.get(position);

            Picasso.with(mContext).load("file://" + folder.url).fit().centerCrop().into(viewHolder.getImgView());
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }
}