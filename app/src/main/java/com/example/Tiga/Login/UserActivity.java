package com.example.Tiga.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.Tiga.Main.BaseActivity;
import com.example.Tiga.Main.R;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

public class UserActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_Query;
    private Button btn_Delete;
    private ListView lv_User;

    private User user;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btn_Query = findViewById(R.id.btn_Query);
        btn_Delete = findViewById(R.id.btn_Delete);
        lv_User = findViewById(R.id.lv_User);

        btn_Query.setOnClickListener(this);
        btn_Delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Query:
                Query();
                break;
            case R.id.btn_Delete:
                ConfirmDelete();
                break;
        }
    }

    private void Query() {
        users = DataSupport.findAll(User.class);
        int nums = users.size();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        lv_User.setAdapter(adapter);
        if (nums == 0) {
            Toast.makeText(this, "无数据!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "成功查询到" + nums + "条数据!", Toast.LENGTH_SHORT).show();
        }
    }

    private void Delete() {
        user = DataSupport.findLast(User.class);
        if (user != null) {
            user.delete();
            Query();
            Toast.makeText(this, "已成功删除最后一条数据!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "无数据!", Toast.LENGTH_SHORT).show();
        }
    }

    private void ConfirmDelete(){
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
