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

    private EditText et_account;
    private EditText et_passWord;
    private EditText et_repeatPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_account = findViewById(R.id.et_account);
        et_passWord = findViewById(R.id.et_passWord);
        et_repeatPassWord = findViewById(R.id.et_repeatPassWord);

        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (inputIsValid()) {
                    if (idIsValid()) {
                        register();
                    }
                }
                break;
        }
    }

    //判断输入是否有效(输入不能为空，密码必须一致且不能小于6位)
    private boolean inputIsValid() {
        if (et_account.getText().toString().equals("") || et_passWord.getText().toString().equals("")
                || et_repeatPassWord.getText().toString().equals("")) {
            Toast.makeText(this, "请完善注册信息!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!et_passWord.getText().toString().equals(et_repeatPassWord.getText().toString())) {
            Toast.makeText(this, "两次密码不一致!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_passWord.getText().toString().length() < 6) {
            Toast.makeText(this, "密码长度必须大于6位!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //判断数据是否有效(账号是否已存在)
    private boolean idIsValid() {
        String Account = et_account.getText().toString();
        List<User> users = DataSupport.where("Account = ?", Account).find(User.class);
        if (users == null || users.isEmpty()) {
            return true;
        } else {
            Toast.makeText(this, "该账号已存在!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //注册
    private void register() {
        User user = new User();
        user.setAccount(et_account.getText().toString());
        user.setPassWord(et_passWord.getText().toString());
        user.save();
        et_account.getText().clear();
        et_passWord.getText().clear();
        et_repeatPassWord.getText().clear();
        List<User> users = DataSupport.where("Account = ?", user.getAccount()).find(User.class);
        if (users == null || users.isEmpty()) {
            Toast.makeText(this, "注册失败!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
