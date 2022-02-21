package com.example.albumproject.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumproject.R;

public class FragmentCreateStory extends Fragment {

    private static final String TAG = "FragmentCreateStory";

    protected RecyclerView mRecyclerView;
    protected FragmentCreateStory.CustomCreateStoryAdapter mAdapter;
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

        mAdapter = new FragmentCreateStory.CustomCreateStoryAdapter(mDataset);
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

    public class CustomCreateStoryAdapter extends RecyclerView.Adapter<FragmentCreateStory.CustomCreateStoryAdapter.ViewHolder> {
        private static final String TAG = "CustomCreateStoryAdapter";

        private String[] mDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getActivity(), "Item " + getAdapterPosition() + " clicked.", Toast.LENGTH_SHORT).show();
//                        Fragment fragment = null;
//                        fragment = new FragmentDetailListImage(getActivity());
//                        replaceFragment(fragment);
                    }
                });

                textView = (TextView) v.findViewById(R.id.tvLibraryName);
            }

            public TextView getTextView() {
                return textView;
            }
        }

        public CustomCreateStoryAdapter(String[] dataSet) {
            mDataSet = dataSet;
        }

        @Override
        public FragmentCreateStory.CustomCreateStoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_list_image, viewGroup, false);

            return new FragmentCreateStory.CustomCreateStoryAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(FragmentCreateStory.CustomCreateStoryAdapter.ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            viewHolder.getTextView().setText(mDataSet[position]);
        }

        @Override
        public int getItemCount() {
            return mDataSet.length;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutFullScreen, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}