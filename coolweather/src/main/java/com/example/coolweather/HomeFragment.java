package com.example.coolweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private final ArrayList<String> items = new ArrayList<>(Arrays.asList("选择城市", "更新每日一图", "更新随机图片", "设置", "关于", "天气api控制台"));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.findViewById(R.id.btn_selectCity).setOnClickListener(this);
        view.findViewById(R.id.btn_updatePicture).setOnClickListener(this);
        view.findViewById(R.id.btn_randomPicture).setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        HomeAdapter adapter = new HomeAdapter(items);
        recyclerView.setAdapter(adapter);
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
                    weatherActivity.loadBingBackgroundPicture(WeatherActivity.requestBingPicture);
                }
                break;
            case R.id.btn_randomPicture:
                if (weatherActivity != null) {
                    weatherActivity.drawerLayout.closeDrawers();
                    weatherActivity.loadBingBackgroundPicture(WeatherActivity.requestRandomPicture);
                }
                break;
        }
    }
}
