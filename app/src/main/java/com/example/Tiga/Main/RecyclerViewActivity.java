package com.example.Tiga.Main;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.Tiga.Login.User;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity implements View.OnClickListener {

    private List<User> users = new ArrayList<>();
    private TextView tv_DataNum;
    private Button btn_Add;
    private Button btn_Delete;
    private UserAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        tv_DataNum = findViewById(R.id.tv_DataNum);
        btn_Add = findViewById(R.id.btn_Add);
        btn_Delete = findViewById(R.id.btn_Delete);

        btn_Add.setOnClickListener(this);
        btn_Delete.setOnClickListener(this);

        initUsers();
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserAdapter(users);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        tv_DataNum.setText("用户总数:"+String.valueOf(adapter.getItemCount()));
    }

    private void initUsers(){
        for (int i=0;i<1;i++){
            User user1 = new User("1","1","一","男",20,"111");
            User user2 = new User("2","2","二","女",21,"222");
            User user3 = new User("3","3","三","男",22,"333");
            User user4 = new User("4","4","四","女",23,"444");
            User user5 = new User("5","5","五","男",24,"555");
            users.add(user1);
            users.add(user2);
            users.add(user3);
            users.add(user4);
            users.add(user5);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Add:
                break;
            case R.id.btn_Delete:
                break;
        }
    }
}
