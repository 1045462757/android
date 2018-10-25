package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.Tiga.Login.UserActivity;

public class AboutFragment extends Fragment implements View.OnClickListener {

    public AboutFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,null );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btn_Version = getActivity().findViewById(R.id.btn_Version);
        Button btn_UserList = getActivity().findViewById(R.id.btn_UserList);
        btn_Version.setOnClickListener(this);
        btn_UserList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_Version:
                intent.setClass(getActivity(), VersionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_UserList:
                intent.setClass(getActivity(), UserActivity.class);
                startActivity(intent);
                break;
        }
    }
}
