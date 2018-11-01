package com.example.Tiga.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tiga.Login.ModifyInformationActivity;
import com.example.Tiga.Login.ModifyPassWordActivity;
import com.example.Tiga.Login.User;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnMain;
    private Button mBtnAbout;
    private Button mBtnSetting;
    private FloatingActionButton btn_FloatingButton;
    private DrawerLayout drawerLayout;

    //抽屉界面
    private TextView tv_Name;
    private TextView tv_Sex;
    private TextView tv_Age;
    private TextView tv_Introduction;
    private Button btn_ModifyInformation;
    private Button btn_ModifyPassWord;
    private Button btn_logOut;

    private MainFragment mainFragment;
    private AboutFragment aboutFragment;
    private SettingFragment settingFragment;

    public static Bundle UserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置返回按钮为抽屉栏
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }

        mBtnMain = findViewById(R.id.btn_Main);
        mBtnAbout = findViewById(R.id.btn_About);
        mBtnSetting = findViewById(R.id.btn_Setting);
        btn_FloatingButton = findViewById(R.id.btn_FloatingButton);
        drawerLayout = findViewById(R.id.DrawerLayout_Main);


        tv_Name = findViewById(R.id.tv_Name);
        tv_Sex = findViewById(R.id.tv_Sex);
        tv_Age = findViewById(R.id.tv_Age);
        tv_Introduction = findViewById(R.id.tv_Introduction);
        btn_ModifyInformation = findViewById(R.id.btn_ModifyInformation);
        btn_ModifyPassWord = findViewById(R.id.btn_ModifyPassWord);
        btn_logOut = findViewById(R.id.btn_LogOut);

        mBtnMain.setOnClickListener(this);
        mBtnAbout.setOnClickListener(this);
        mBtnSetting.setOnClickListener(this);
        btn_ModifyInformation.setOnClickListener(this);
        btn_ModifyPassWord.setOnClickListener(this);
        btn_logOut.setOnClickListener(this);
        btn_FloatingButton.setOnClickListener(this);

        mainFragment = new MainFragment();
        aboutFragment = new AboutFragment();
        settingFragment = new SettingFragment();

        replaceFragment(mainFragment);

        UserData = GetUserData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetUserData2();
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_Main:
                replaceFragment(mainFragment);
                break;
            case R.id.btn_About:
                replaceFragment(aboutFragment);
                break;
            case R.id.btn_Setting:
                TransferData(settingFragment, UserData);
                replaceFragment(settingFragment);
                break;
            case R.id.btn_ModifyInformation:
                intent = new Intent();
                intent.setClass(MainActivity.this, ModifyInformationActivity.class);
                intent.putExtras(UserData);
                startActivity(intent);
                break;
            case R.id.btn_ModifyPassWord:
                intent = new Intent();
                intent.setClass(MainActivity.this, ModifyPassWordActivity.class);
                intent.putExtras(UserData);
                startActivity(intent);
                break;
            case R.id.btn_LogOut:
                OffLine();
                break;
            case R.id.btn_FloatingButton:
                Toast.makeText(this, "这是悬浮按钮哟!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //切换Fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, fragment);
        fragmentTransaction.commit();
    }

    //向Fragment传值
    private void TransferData(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
    }

    //获取用户数据
    private Bundle GetUserData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return bundle;
    }

    //菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //菜单响应事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                break;
            case R.id.notification:
                Toast.makeText(this, "暂时没有通知哟!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_Exit:
                ConfirmExit();
                break;
            case R.id.about:
                Toast.makeText(this, "tiga", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                GetUserData2();
                break;
        }
        return true;
    }

    //退出
    private void ConfirmExit() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("通知:");
        builder.setMessage("你确定要退出吗?");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //获取用户信息
    private void GetUserData2() {
        User user = new User();
        user.setName(UserData.getString("Name"));
        user.setSex(UserData.getString("Sex"));
        user.setAge(UserData.getInt("Age"));
        user.setIntroduction(UserData.getString("Introduction"));
        tv_Name.setText(user.getName());
        tv_Sex.setText(user.getSex());
        tv_Age.setText(String.valueOf(user.getAge()));
        tv_Introduction.setText(user.getIntroduction());
    }

    //下线
    private void OffLine() {
        Intent intent = new Intent();
        intent.setAction("com.example.OFFLINE");
        this.sendBroadcast(intent);
    }

}
