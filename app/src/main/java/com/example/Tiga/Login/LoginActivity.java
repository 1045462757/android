package com.example.Tiga.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.MainActivity;
import com.example.Tiga.Main.R;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_Login;
    private EditText et_Account;
    private EditText et_PassWord;
    private CheckBox cb_RememberPwd;
    private Button btn_ForgetPassWord;
    private Button btn_Register;

    private User user;

    public static boolean CLEAR_PASSWORD = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_Login = findViewById(R.id.btn_Login);
        et_Account = findViewById(R.id.et_Account);
        et_PassWord = findViewById(R.id.et_PassWord);
        btn_ForgetPassWord = findViewById(R.id.btn_ForgetPassWord);
        cb_RememberPwd = findViewById(R.id.cb_RememberPassword);
        btn_Register = findViewById(R.id.btn_Register);

        btn_Login.setOnClickListener(this);
        btn_ForgetPassWord.setOnClickListener(this);
        btn_Register.setOnClickListener(this);

        et_PassWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_PassWord.getText().clear();
                } else {

                }
            }
        });

        RefreshData(CLEAR_PASSWORD);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Login:
                if (InputIsValid()) {
                    if (DataIsValid()) {
                        IsRememberMe();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtras(PackageData());
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            case R.id.btn_ForgetPassWord:
                Toast.makeText(this, "暂时没有办法帮你哦!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_Register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    //判断输入是否有效(输入不能为空)
    private boolean InputIsValid() {
        if (et_Account.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "请输入账号!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_PassWord.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "请输入密码!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //判断数据是否有效(账号，密码是否正确)
    private boolean DataIsValid() {
        String Account = et_Account.getText().toString();
        String PassWord = et_PassWord.getText().toString();
        List<User> users = DataSupport.where("Account = ?", Account).find(User.class);
        if (users == null || users.isEmpty()) {
            Toast.makeText(this, "该账号不存在!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            user = new User();
            for (User user1 : users) {
                user.setAccount(user1.getAccount());
                user.setPassWord(user1.getPassWord());
                user.setName(user1.getName());
                user.setSex(user1.getSex());
                user.setAge(user1.getAge());
                user.setIntroduction(user1.getIntroduction());
            }
            if (PassWord.equals(user.getPassWord())) {
                Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "密码错误!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    //判断是否记住密码
    private void IsRememberMe() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (cb_RememberPwd.isChecked()) {
            editor.putString("Account", user.getAccount());
            editor.putString("PassWord", user.getPassWord());
            editor.putBoolean("RememberPwd", true);
            editor.apply();
            editor.clear();
        } else {
            editor.putBoolean("RememberPwd", false);
            editor.apply();
            editor.clear();
        }
    }

    //复现登录信息
    private void RefreshData(boolean ClearPassWord) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = sharedPreferences.getBoolean("RememberPwd", false);
        String Account = sharedPreferences.getString("Account", "");
        et_Account.setText(Account);
        if (isRemember) {
            String PassWord = sharedPreferences.getString("PassWord", "");
            if (ClearPassWord == true) {
                et_PassWord.setText("");
                LoginActivity.CLEAR_PASSWORD = false;
            } else {
                et_PassWord.setText(PassWord);
            }
            cb_RememberPwd.setChecked(true);
        }
    }

    //打包用户数据
    private Bundle PackageData() {
        Bundle bundle = new Bundle();
        bundle.putString("Account", user.getAccount());
        bundle.putString("PassWord", user.getPassWord());
        bundle.putString("Name", user.getName());
        bundle.putString("Sex", user.getSex());
        bundle.putInt("Age", user.getAge());
        bundle.putString("Introduction", user.getIntroduction());
        return bundle;
    }

}
