package com.example.Tiga.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv_User;

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        lv_User = findViewById(R.id.lv_User);

        findViewById(R.id.btn_Query).setOnClickListener(this);
        findViewById(R.id.btn_Delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Query:
                Query();
                break;
            case R.id.btn_Delete:
                if (users == null || users.isEmpty()) {
                    Toast.makeText(this, "暂无用户数据!", Toast.LENGTH_SHORT).show();
                } else {
                    ConfirmDelete();
                }
                break;
        }
    }

    //查询用户
    private void Query() {
        users = DataSupport.findAll(User.class);
        int Num = users.size();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        lv_User.setAdapter(adapter);
        if (Num == 0) {
            Toast.makeText(this, "无数据!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "成功查询到" + Num + "条数据!", Toast.LENGTH_SHORT).show();
        }
    }

    //删除用户
    private void Delete() {
        User user = DataSupport.findLast(User.class);
        if (user != null) {
            user.delete();
            Query();
            Toast.makeText(this, "已成功删除最后一条数据!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "无数据!", Toast.LENGTH_SHORT).show();
        }
    }

    //确认删除
    private void ConfirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("通知:");
        builder.setMessage("你确定要删除数据吗?");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Delete();
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
