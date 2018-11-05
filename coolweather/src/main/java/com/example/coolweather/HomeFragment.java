package com.example.coolweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.findViewById(R.id.btn_selectCity).setOnClickListener(this);
        view.findViewById(R.id.btn_updatePicture).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        WeatherActivity weatherActivity = (WeatherActivity) getActivity();
        switch (v.getId()) {
            case R.id.btn_selectCity:
                weatherActivity.replaceFragment(new ChooseAreaFragment());
                break;
            case R.id.btn_updatePicture:
                if (weatherActivity != null) {
                    weatherActivity.drawerLayout.closeDrawers();
                    weatherActivity.loadBingPicture();
                }
                break;
        }
    }
}
