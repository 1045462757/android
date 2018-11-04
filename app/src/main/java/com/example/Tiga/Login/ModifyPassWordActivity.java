package com.example.Tiga.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Tiga.Main.ActivityCollector;
import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.MainActivity;
import com.example.Tiga.Main.R;

public class ModifyPassWordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_oldPassWord;
    private EditText et_newPassWord;
    private EditText et_repeatNewPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pass_word);

        et_oldPassWord = findViewById(R.id.et_oldPassWord);
        et_newPassWord = findViewById(R.id.et_newPassWord);
        et_repeatNewPassWord = findViewById(R.id.et_repeatNewPassWord);

        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (inputIsValid()) {
                    if (oldPassWordIsValid()) {
                        confirmModify();
                    }
                }
                break;
        }
    }

    //判断输入是否有效(输入不能为空，密码必须一致且不能小于6位)
    private boolean inputIsValid() {
        if (et_oldPassWord.getText().toString().equals("") || et_newPassWord.getText().toString().equals("") ||
                et_repeatNewPassWord.getText().toString().equals("")) {
            Toast.makeText(ModifyPassWordActivity.this, "请输入完整信息!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!et_newPassWord.getText().toString().equals(et_repeatNewPassWord.getText().toString())) {
            Toast.makeText(this, "两次密码不一致!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_newPassWord.getText().toString().length() < 6) {
            Toast.makeText(this, "密码长度必须大于6位!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //判断数据是否有效(旧密码是否正确)
    private boolean oldPassWordIsValid() {
        String passWord = MainActivity.UserData.getString("PassWord");
        if (!et_oldPassWord.getText().toString().equals(passWord)) {
            Toast.makeText(this, "旧密码不正确!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //执行修改密码
    private void modifyPassWord() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        User user = new User();
        user.setAccount(MainActivity.UserData.getString("Account"));
        user.setPassWord(et_newPassWord.getText().toString());
        user.updateAll("Account = ?", user.getAccount());
        editor.putString("PassWord", "");
        editor.apply();
        editor.clear();
        et_oldPassWord.getText().clear();
        et_newPassWord.getText().clear();
        et_repeatNewPassWord.getText().clear();
        Toast.makeText(this, "已成功修改密码,请重新登录!", Toast.LENGTH_LONG).show();
        ActivityCollector.finishAll();
        Intent intent = new Intent(ModifyPassWordActivity.this, LoginActivity.class);
        LoginActivity.CLEAR_PASSWORD = true;
        startActivity(intent);
    }

    //确认修改
    private void confirmModify() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("通知:");
        builder.setMessage("你确定要修改密码吗?");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modifyPassWord();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

}
