package com.example.albumproject.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumproject.R;
import com.example.albumproject.models.FileModel;
import com.example.albumproject.models.FolderMainModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragmentDetailListImage extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected FragmentDetailListImage.CustomDetailAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;
    FolderMainModel item;
    Context ctx;

    ArrayList<FileModel> list;

    public FragmentDetailListImage(Context ctx, FolderMainModel item) {
        this.item = item;
        this.ctx = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();

        this.list = this.getAllImagesByFolder(this.item.getPath());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_list_image, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewDetailListImage);

        mAdapter = new FragmentDetailListImage.CustomDetailAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }

    //fake data
    private void initDataset() {
        mDataset = new String[16];
        mDataset[0] = "Age Calculator";
        mDataset[1] = "Anti Rabies Vaccine Schedule";
        mDataset[2] = "Body Mass Index";
        mDataset[3] = "Creatinine Clearance";
        mDataset[4] = "Fluid Resuscitation in Burns";
        mDataset[5] = "Gestational Age from LMP";
        mDataset[6] = "Gestational Age from scan";
        mDataset[7] = "Glomerular Filtration Rate";
        mDataset[8] = "Temperature conversion";
        mDataset[9] = "Arm Fat Index";
        mDataset[10] = "Basal Metabolic Rate";
        mDataset[11] = "Body Adiposity Index";
        mDataset[12] = "Body Surface Area";
        mDataset[13] = "Fractional Excretion of Sodium";
        mDataset[14] = "Mean Arterial Pressure";
        mDataset[15] = "Plasma Osmolality";
    }

    public class CustomDetailAdapter extends RecyclerView.Adapter<FragmentDetailListImage.CustomDetailAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<FileModel> mDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imgView;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Item " + getAdapterPosition() + " clicked.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    }
                });

                imgView = (ImageView) v.findViewById(R.id.imgLibraryItem);
            }

            public ImageView getImgView(){ return this.imgView; }

        }

        public CustomDetailAdapter(ArrayList<FileModel> dataSet) {
            mDataSet = dataSet;
        }

        @Override
        public FragmentDetailListImage.CustomDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_detail_list_image, viewGroup, false);

            return new FragmentDetailListImage.CustomDetailAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(FragmentDetailListImage.CustomDetailAdapter.ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            final FileModel folder = mDataSet.get(position);

            Picasso.with(ctx).load("file://" + folder.url).fit().centerCrop().into(viewHolder.getImgView());
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
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
        Cursor cursor = this.ctx.getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
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

}