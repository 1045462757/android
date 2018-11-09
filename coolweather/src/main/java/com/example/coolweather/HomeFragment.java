package com.example.coolweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView_drawer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MainActivity activity = (MainActivity) getActivity();
        HomeAdapter adapter = new HomeAdapter(activity);
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.tv_version).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_version:
                Snackbar snackbar = Snackbar.make(v, "已是最新版本哦!", Snackbar.LENGTH_SHORT);
                View snackView = snackbar.getView();
                snackView.setBackgroundColor(v.getResources().getColor(R.color.colorTip));
                snackbar.show();
                break;
        }
    }
}
