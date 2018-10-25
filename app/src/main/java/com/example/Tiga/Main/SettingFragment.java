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

public class SettingFragment extends Fragment implements View.OnClickListener {

    private TextView tv_UserName;
    private Button btn_PersonalInformation;
    private Button btn_ModifyPassWord;
    private Button btn_LogOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tv_UserName = getActivity().findViewById(R.id.tv_UserName);
        btn_PersonalInformation = getActivity().findViewById(R.id.btn_PersonalInformation);
        btn_ModifyPassWord = getActivity().findViewById(R.id.btn_ModifyPassWord);
        btn_LogOut = getActivity().findViewById(R.id.btn_LogOut);

        btn_PersonalInformation.setOnClickListener(this);
        btn_ModifyPassWord.setOnClickListener(this);
        btn_LogOut.setOnClickListener(this);

        SetName();
    }

    @Override
    public void onResume() {
        super.onResume();
        SetName();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_PersonalInformation:
                intent.setClass(getActivity(), PersonalInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ModifyPassWord:
                intent.setClass(getActivity(), ModifyPassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_LogOut:
                OffLine();
                break;
        }
    }

    //下线
    private void OffLine() {
        Intent intent = new Intent();
        intent.setAction("com.example.OFFLINE");
        getActivity().sendBroadcast(intent);
    }

    //设置用户名
    private void SetName(){
        Bundle UserData = MainActivity.UserData;
        String Name = UserData.getString("Name");
        String text = "用户:";
        Name = text + Name;
        tv_UserName.setText(Name);
    }
}
