package com.example.Tiga.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.MainActivity;
import com.example.Tiga.Main.R;

public class ModifyInformationActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_introduction;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_information);

        et_name = findViewById(R.id.et_name);
        et_sex = findViewById(R.id.et_sex);
        et_age = findViewById(R.id.et_age);
        et_introduction = findViewById(R.id.et_introduction);

        findViewById(R.id.btn_submit).setOnClickListener(this);

        getUserData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (inputIsValid()) {
                    confirmModify();
                }
                break;
        }
    }

    //获取用户信息
    private void getUserData() {
        Bundle UserData = MainActivity.UserData;
        user = new User();
        user.setAccount(UserData.getString("Account"));
        user.setName(UserData.getString("Name"));
        user.setSex(UserData.getString("Sex"));
        user.setAge(UserData.getInt("Age"));
        user.setIntroduction(UserData.getString("Introduction"));
        et_name.setText(user.getName());
        et_sex.setText(user.getSex());
        et_age.setText(String.valueOf(user.getAge()));
        et_introduction.setText(user.getIntroduction());
    }

    //修改信息
    private void modifyInformation() {
        user.setName(et_name.getText().toString());
        user.setSex(et_sex.getText().toString());
        user.setAge(Integer.parseInt(et_age.getText().toString()));
        user.setIntroduction(et_introduction.getText().toString());
        user.updateAll("Account = ?", user.getAccount());
        upDataUserData();
        Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
    }

    //判断输入是否有效
    private boolean inputIsValid() {
        if (et_name.getText().toString().equals("") || et_sex.getText().toString().equals("") ||
                et_age.getText().toString().equals("") || et_introduction.getText().toString().equals("")) {
            Toast.makeText(this, "请完善信息!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //确定修改
    private void confirmModify() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("通知:");
        builder.setMessage("你确定要修改信息吗?");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modifyInformation();
                finish();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //更新用户数据
    private void upDataUserData() {
        MainActivity.UserData.putString("Name", user.getName());
        MainActivity.UserData.putString("Sex", user.getSex());
        MainActivity.UserData.putInt("Age", user.getAge());
        MainActivity.UserData.putString("Introduction", user.getIntroduction());
    }
}
