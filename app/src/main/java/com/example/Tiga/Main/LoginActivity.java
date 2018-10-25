package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private Button mBtnLogin;
    private EditText edUser;
    private EditText edPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqlogin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mBtnLogin = findViewById(R.id.btn_Login);
        edUser = findViewById(R.id.et_User);
        edPassWord = findViewById(R.id.et_PassWord);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String User = edUser.getText().toString();
                String PassWord = edPassWord.getText().toString();

                if (User.equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入QQ号码!", Toast.LENGTH_SHORT).show();
                } else if (PassWord.equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入密码!", Toast.LENGTH_SHORT).show();
                } else if (!User.equals("123456") || !PassWord.equals("tiga")) {
                    Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, QQActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("QQ号", User);
                    bundle.putCharSequence("密码", PassWord);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
