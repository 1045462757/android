package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.Tiga.Login.ModifyPassWordActivity;
import com.example.Tiga.Login.PersonalInformationActivity;
import com.example.Tiga.Login.UserActivity;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private Button btn_ManageUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_ManageUser = getActivity().findViewById(R.id.btn_ManageUser);

        btn_ManageUser.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_ManageUser:
                intent.setClass(getActivity(), UserActivity.class);
                startActivity(intent);
                break;
        }
    }
}
