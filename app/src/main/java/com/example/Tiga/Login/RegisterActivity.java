package com.example.Tiga.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.R;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_Account;
    private EditText et_PassWord;
    private EditText et_PassWord2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_Account = findViewById(R.id.et_Account);
        et_PassWord = findViewById(R.id.et_PassWord);
        et_PassWord2 = findViewById(R.id.et_PassWord2);

        findViewById(R.id.btn_Register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Register:
                if (InputIsValid()) {
                    if (UserIsValid()) {
                        Register();
                    }
                }
                break;
        }
    }

    //判断输入是否有效(输入不能为空，密码必须一致且不能小于6位)
    private boolean InputIsValid() {
        if (et_Account.getText().toString().equals("") || et_PassWord.getText().toString().equals("")
                || et_PassWord2.getText().toString().equals("")) {
            Toast.makeText(this, "请完善注册信息!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!et_PassWord.getText().toString().equals(et_PassWord2.getText().toString())) {
            Toast.makeText(this, "两次密码不一致!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_PassWord.getText().toString().length() < 6) {
            Toast.makeText(this, "密码长度必须大于6位!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //判断数据是否有效(账号是否已存在)
    private boolean UserIsValid() {
        String Account = et_Account.getText().toString();
        List<User> users = DataSupport.where("Account = ?", Account).find(User.class);
        if (users == null || users.isEmpty()) {
            return true;
        } else {
            Toast.makeText(this, "该账号已存在!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //注册
    private void Register() {
        User user = new User();
        user.setAccount(et_Account.getText().toString());
        user.setPassWord(et_PassWord.getText().toString());
        user.save();
        et_Account.getText().clear();
        et_PassWord.getText().clear();
        et_PassWord2.getText().clear();
        List<User> users = DataSupport.where("Account = ?", user.getAccount()).find(User.class);
        if (users == null || users.isEmpty()) {
            Toast.makeText(this, "注册失败!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
        }
    }
}
