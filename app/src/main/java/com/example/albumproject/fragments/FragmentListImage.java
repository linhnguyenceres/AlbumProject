package com.example.albumproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.albumproject.R;
import com.example.albumproject.models.FolderMainModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragmentListImage extends Fragment {


    ArrayList<FolderMainModel> list;
    Context ctx;
    public FragmentListImage(ArrayList<FolderMainModel> list,Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_image, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewListImage);

        mAdapter = new CustomAdapter(this.list);
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

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<FolderMainModel> mDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final ImageView imgView;
            private final TextView textCount;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Item " + getAdapterPosition() + " clicked.", Toast.LENGTH_SHORT).show();
                        Fragment fragment = null;
                        fragment = new FragmentDetailListImage(ctx,mDataSet.get(getAdapterPosition()));
                        replaceFragment(fragment);
                    }
                });

                textView = (TextView) v.findViewById(R.id.tvLibraryName);
                imgView = (ImageView) v.findViewById(R.id.imgLibraryItem);
                textCount = (TextView) v.findViewById(R.id.tvCount);

            }

            public TextView getTextView() {
                return textView;
            }
            public ImageView getImageView(){ return imgView; }
            public TextView getTextCount() { return textCount; }
        }

        public CustomAdapter(ArrayList<FolderMainModel> dataSet) {
            mDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_list_image, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");
            final FolderMainModel folder = mDataSet.get(position);

            Picasso.with(ctx).load("file://" + folder.getFirstPic()).fit().centerCrop().into(viewHolder.getImageView());

            viewHolder.getTextView().setText(folder.getFolderName());

            viewHolder.getTextCount().setText(String.valueOf(folder.getNumberOfPics()));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutFullScreen, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}