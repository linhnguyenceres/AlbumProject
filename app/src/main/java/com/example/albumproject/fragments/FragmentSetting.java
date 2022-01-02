package com.example.albumproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.albumproject.R;
import com.example.albumproject.activities.SettingWebViewActivity;

public class FragmentSetting extends Fragment implements View.OnClickListener {
    ConstraintLayout groupTerm;
    ConstraintLayout groupPolicy;

    public FragmentSetting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        groupTerm = (ConstraintLayout) rootView.findViewById(R.id.groupTerm);
        groupPolicy = (ConstraintLayout) rootView.findViewById(R.id.groupPolicy);

        groupPolicy.setOnClickListener(this);
        groupTerm.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.groupPolicy:
                SettingWebViewActivity settingView = new SettingWebViewActivity();
                Intent it = new Intent(getActivity(), settingView.getClass());
                it.putExtra("title", "Chính sách bảo mật");
                it.putExtra("url", "https://vi.lipsum.com/");
                startActivity(it);
                break;
            case R.id.groupTerm:
                SettingWebViewActivity settingView1 = new SettingWebViewActivity();
                Intent it1 = new Intent(getActivity(), settingView1.getClass());
                it1.putExtra("title", "Điều khoản dịch vụ");
                it1.putExtra("url", "https://www.lipsum.com");
                startActivity(it1);
                break;
        }
    }
}
