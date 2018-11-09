package com.example.coolweather;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.coolweather.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //必应每日一图api
    public static String requestBingPicture = "http://guolin.tech/api/bing_pic";
    //随机图片api
    public static String requestRandomPicture = "http://img.xjh.me/random_img.php?return=url";

    private ImageView iv_backgroundPicture;
    public DrawerLayout drawerLayout;
    private ProgressDialog progressDialog;

    public static WeatherFragment weatherFragment = new WeatherFragment();
    public static NewsFragment newsFragment = new NewsFragment();
    public static JokesFragment jokesFragment = new JokesFragment();
    public static HomeFragment homeFragment = new HomeFragment();
    public static ChooseAreaFragment chooseAreaFragment = new ChooseAreaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        initViews();

        iv_backgroundPicture = findViewById(R.id.iv_backgroundPicture);
        drawerLayout = findViewById(R.id.drawerLayout_main);

        //抽屉栏
        replaceFragment(homeFragment);
    }

    /**
     * 设置Toolbar属性
     */
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.list);
        }
    }

    /**
     * 引入菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 菜单点击事件
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_Exit:
                ConfirmExit();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    /**
     * 退出
     */
    private void ConfirmExit() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("通知:");
        builder.setMessage("你确定要退出吗?");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    /**
     * 初始化顶部Tab导航栏
     */
    private void initViews() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab one = tabLayout.getTabAt(0);
        TabLayout.Tab two = tabLayout.getTabAt(1);
        TabLayout.Tab three = tabLayout.getTabAt(2);
    }

    /**
     * 切换Fragment
     */
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_drawer, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 从服务器上获取背景图片
     */
    public void loadBingBackgroundPicture(final String api) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Snackbar snackbar = Snackbar.make(drawerLayout, "无网络!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPicture = response.body().string();
//                Log.d("MainActivity", "服务器返回的信息:" + bingPicture);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                editor.putString("bingPicture", bingPicture);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Snackbar snackbar = Snackbar.make(drawerLayout, "背景图片更新成功!", Snackbar.LENGTH_SHORT);
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(getResources().getColor(R.color.colorSuccess));
                        String uri = "";
                        if (!api.equals(requestBingPicture)) {
                            uri = "http:";
                        }
                        Glide.with(MainActivity.this).load(uri + bingPicture).into(iv_backgroundPicture);
                        snackbar.show();
                    }
                });
            }
        }, 1);
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
