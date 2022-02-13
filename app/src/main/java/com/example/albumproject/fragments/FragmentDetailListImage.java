package com.example.albumproject.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumproject.R;

public class FragmentDetailListImage extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected FragmentDetailListImage.CustomDetailAdapter mAdapter;
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
        View rootView = inflater.inflate(R.layout.fragment_detail_list_image, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewDetailListImage);

        mAdapter = new FragmentDetailListImage.CustomDetailAdapter(mDataset);
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

        private String[] mDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Item " + getAdapterPosition() + " clicked.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    }
                });
            }
        }

        public CustomDetailAdapter(String[] dataSet) {
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
        }

        @Override
        public int getItemCount() {
            return mDataSet.length;
        }
    }

}