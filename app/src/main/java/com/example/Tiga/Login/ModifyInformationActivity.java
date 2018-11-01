package com.example.Tiga.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.MainActivity;
import com.example.Tiga.Main.R;

public class ModifyInformationActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_Name;
    private EditText et_Sex;
    private EditText et_Age;
    private EditText et_Introduction;
    private Button btn_Submit;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_information);

        et_Name = findViewById(R.id.et_Name);
        et_Sex = findViewById(R.id.et_Sex);
        et_Age = findViewById(R.id.et_Age);
        et_Introduction = findViewById(R.id.et_Introduction);
        btn_Submit = findViewById(R.id.btn_Submit);

        btn_Submit.setOnClickListener(this);
        GetUserData();
        SetInformation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Submit:
                if (InputIsValid()) {
                    ConfirmModify();
                }
                break;
        }
    }

    //获取信息
    private void GetUserData() {
        Bundle UserData = MainActivity.UserData;
        user = new User();
        user.setAccount(UserData.getString("Account"));
        user.setName(UserData.getString("Name"));
        user.setSex(UserData.getString("Sex"));
        user.setAge(UserData.getInt("Age"));
        user.setIntroduction(UserData.getString("Introduction"));
    }


    //设置信息
    private void SetInformation() {
        et_Name.setText(user.getName());
        et_Sex.setText(user.getSex());
        et_Age.setText(String.valueOf(user.getAge()));
        et_Introduction.setText(user.getIntroduction());
    }


    //修改信息
    private void ModifyInformation() {
        user.setName(et_Name.getText().toString());
        user.setSex(et_Sex.getText().toString());
        user.setAge(Integer.parseInt(et_Age.getText().toString()));
        user.setIntroduction(et_Introduction.getText().toString());
        user.updateAll("Account = ?",user.getAccount());
        UpDataUserData();
        Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
    }

    //判断输入是否有效
    private boolean InputIsValid() {
        if (et_Name.getText().toString().equals("") || et_Sex.getText().toString().equals("") ||
                et_Age.getText().toString().equals("") || et_Introduction.getText().toString().equals("")) {
            Toast.makeText(this, "请完善信息!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void ConfirmModify() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("通知:");
        builder.setMessage("你确定要修改信息吗?");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ModifyInformation();
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
    private void UpDataUserData(){
        MainActivity.UserData.putString("Name",user.getName());
        MainActivity.UserData.putString("Sex",user.getSex());
        MainActivity.UserData.putInt("Age",user.getAge());
        MainActivity.UserData.putString("Introduction",user.getIntroduction());
    }
}
