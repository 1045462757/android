package com.example.Tiga.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.MainActivity;
import com.example.Tiga.Main.R;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_account;
    private EditText et_passWord;
    private CheckBox cb_rememberPassWord;

    public static boolean CLEAR_PASSWORD = false;

    private User user;
    private String account;
    private String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_account = findViewById(R.id.et_account);
        et_passWord = findViewById(R.id.et_passWord);
        cb_rememberPassWord = findViewById(R.id.cb_rememberPassWord);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_forgetPassWord).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);

        et_passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_passWord.getText().clear();
                }
            }
        });

        refreshData(CLEAR_PASSWORD);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (inputIsValid()) {
                    if (dataIsValid()) {
                        isRememberPassWord();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtras(packageData());
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            case R.id.btn_forgetPassWord:
                Toast.makeText(this, "暂时没有办法帮你哦!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    //判断输入是否有效(输入不能为空)
    private boolean inputIsValid() {
        account = et_account.getText().toString();
        passWord = et_passWord.getText().toString();
        if (account.equals("")) {
            Toast.makeText(LoginActivity.this, "请输入账号!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passWord.equals("")) {
            Toast.makeText(LoginActivity.this, "请输入密码!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //判断数据是否有效(账号，密码是否正确)
    private boolean dataIsValid() {
        List<User> users = DataSupport.where("Account = ?", account).find(User.class);
        if (users == null || users.isEmpty()) {
            Toast.makeText(this, "该账号不存在!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            user = new User();
            user.setPassWord(users.get(0).getPassWord());
            if (passWord.equals(user.getPassWord())) {
                user.setAccount(users.get(0).getAccount());
                user.setName(users.get(0).getName());
                user.setSex(users.get(0).getSex());
                user.setAge(users.get(0).getAge());
                user.setIntroduction(users.get(0).getIntroduction());
                Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "密码错误!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    //判断是否记住密码
    private void isRememberPassWord() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (cb_rememberPassWord.isChecked()) {
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
    private void refreshData(boolean ClearPassWord) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = sharedPreferences.getBoolean("RememberPwd", false);
        String Account = sharedPreferences.getString("Account", "");
        et_account.setText(Account);
        if (isRemember) {
            String PassWord = sharedPreferences.getString("PassWord", "");
            if (ClearPassWord) {
                et_passWord.setText("");
                LoginActivity.CLEAR_PASSWORD = false;
            } else {
                et_passWord.setText(PassWord);
            }
            cb_rememberPassWord.setChecked(true);
        }
    }

    //打包用户数据
    private Bundle packageData() {
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
