package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment implements View.OnClickListener {

    public MainFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,null );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btn_QqLogin = getActivity().findViewById(R.id.btn_QqLogin);
        Button btn_MyStudy = getActivity().findViewById(R.id.btn_MyStudy);

        btn_QqLogin.setOnClickListener(this);
        btn_MyStudy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_QqLogin:
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_MyStudy:
                intent.setClass(getActivity(), myStudyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
