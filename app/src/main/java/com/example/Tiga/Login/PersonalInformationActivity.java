package com.example.Tiga.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.MainActivity;
import com.example.Tiga.Main.R;

public class PersonalInformationActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_Name;
    private TextView tv_Sex;
    private TextView tv_Age;
    private TextView tv_Introduction;
    private Button btn_Modify;

    private Bundle UserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        tv_Name = findViewById(R.id.tv_Name);
        tv_Sex = findViewById(R.id.tv_Sex);
        tv_Age = findViewById(R.id.tv_Age);
        tv_Introduction = findViewById(R.id.tv_Introduction);
        btn_Modify = findViewById(R.id.btn_Modify);

        btn_Modify.setOnClickListener(this);

        GetUserData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetUserData();
    }

    //获取用户信息
    private void GetUserData() {
        UserData = MainActivity.UserData;
        User user = new User();
        user.setName(UserData.getString("Name"));
        user.setSex(UserData.getString("Sex"));
        user.setAge(UserData.getInt("Age"));
        user.setIntroduction(UserData.getString("Introduction"));
        tv_Name.setText(user.getName());
        tv_Sex.setText(user.getSex());
        tv_Age.setText(String.valueOf(user.getAge()));
        tv_Introduction.setText(user.getIntroduction());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_Modify:
                intent.setClass(PersonalInformationActivity.this,ModifyInformationActivity.class);
                intent.putExtras(UserData);
                startActivity(intent);
                break;
        }
    }
}
