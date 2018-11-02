package com.example.Tiga.Main;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity implements View.OnClickListener {

    private List<RecyclerViewActivity.User> users = new ArrayList<>();
    private TextView tv_DataNum;
    private Button btn_Add;
    private Button btn_Delete;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        tv_DataNum = findViewById(R.id.tv_DataNum);
        btn_Add = findViewById(R.id.btn_Add);
        btn_Delete = findViewById(R.id.btn_Delete);

        btn_Add.setOnClickListener(this);
        btn_Delete.setOnClickListener(this);

        //初始化数据
        initUsers();

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserAdapter(users);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(defaultItemAnimator);
        tv_DataNum.setText("用户总数:" + String.valueOf(adapter.getItemCount()));
    }

    private void initUsers() {
        for (int i = 0; i < 3; i++) {
            RecyclerViewActivity.User user1 = new User(R.mipmap.file, "1045462757", "黄一", "男", "20");
            RecyclerViewActivity.User user2 = new User(R.mipmap.call, "2020118011", "陆一", "女", "20");
            RecyclerViewActivity.User user3 = new User(R.mipmap.home, "15696136261", "张三", "男", "21");
            RecyclerViewActivity.User user4 = new User(R.mipmap.phone, "17623096116", "李四", "女", "22");
            RecyclerViewActivity.User user5 = new User(R.mipmap.map, "13330210791", "王二", "男", "23");
            users.add(user1);
            users.add(user2);
            users.add(user3);
            users.add(user4);
            users.add(user5);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Add:
                adapter.addData(1);
                break;
            case R.id.btn_Delete:
                adapter.removeData(1);
                break;
        }
    }

    static class User {

        private int PhotoId;
        private String Account;
        private String Name;
        private String Sex;
        private String Age;

        public User() {
        }

        public User(int photoId, String account, String name, String sex, String age) {
            PhotoId = photoId;
            Account = account;
            Name = name;
            Sex = sex;
            Age = age;
        }

        public int getPhotoId() {
            return PhotoId;
        }

        public void setPhotoId(int photoId) {
            PhotoId = photoId;
        }

        public String getAccount() {
            return Account;
        }

        public void setAccount(String account) {
            Account = account;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        @Override
        public String toString() {
            return "Account:" + getAccount() + "  Name:" + getName() + "  Sex:" + getSex() + "  Age:" + getAge();
        }
    }
}
