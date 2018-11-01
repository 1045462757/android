package com.example.Tiga.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.Tiga.Main.ActivityCollector;
import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.MainActivity;
import com.example.Tiga.Main.R;

public class ModifyPassWordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_OldPassWord;
    private EditText et_NewPassWord;
    private EditText et_RepeatNewPassWord;
    private Button btn_Submit;

    private Bundle UserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pass_word);

        et_OldPassWord = findViewById(R.id.et_OldPassWord);
        et_NewPassWord = findViewById(R.id.et_NewPassWord);
        et_RepeatNewPassWord = findViewById(R.id.et_RepeatNewPassWord);
        btn_Submit = findViewById(R.id.btn_Submit);

        btn_Submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Submit:
                if (InputIsValid()) {
                    if (PWIsValid()) {
                        ConfirmModify();
                    }
                }
                break;
        }
    }

    //判断输入是否有效(输入不能为空，密码必须一致且不能小于6位)
    private boolean InputIsValid() {
        if (et_OldPassWord.getText().toString().equals("") || et_NewPassWord.getText().toString().equals("") ||
                et_RepeatNewPassWord.getText().toString().equals("")) {
            Toast.makeText(ModifyPassWordActivity.this, "请输入完整信息!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!et_NewPassWord.getText().toString().equals(et_RepeatNewPassWord.getText().toString())) {
            Toast.makeText(this, "两次密码不一致!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_NewPassWord.getText().toString().length() < 6) {
            Toast.makeText(this, "密码长度必须大于6位!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //判断数据是否有效(旧密码是否正确)
    private boolean PWIsValid() {
        UserData = MainActivity.UserData;
        String PassWord = UserData.getString("PassWord");
        if (!et_OldPassWord.getText().toString().equals(PassWord)) {
            Toast.makeText(this, "旧密码不正确!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //执行修改密码
    private void ModifyPassWord() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        User user = new User();
        user.setAccount(UserData.getString("Account"));
        user.setPassWord(et_NewPassWord.getText().toString());
        user.updateAll("Account = ?", user.getAccount());
        editor.putString("PassWord", "");
        editor.apply();
        editor.clear();
        et_OldPassWord.getText().clear();
        et_NewPassWord.getText().clear();
        et_RepeatNewPassWord.getText().clear();
        Toast.makeText(this, "已成功修改密码,请重新登录!", Toast.LENGTH_LONG).show();
        ActivityCollector.finishAll();
        Intent intent = new Intent(ModifyPassWordActivity.this, LoginActivity.class);
        LoginActivity.CLEAR_PASSWORD = true;
        startActivity(intent);
    }

    private void ConfirmModify() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("通知:");
        builder.setMessage("你确定要修改密码吗?");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ModifyPassWord();
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
