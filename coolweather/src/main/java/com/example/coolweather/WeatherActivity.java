package com.example.coolweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.service.AutoUpdateService;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    public DrawerLayout drawerLayout;

    public SwipeRefreshLayout swipeRefresh;

    private ImageView iv_bingPicture;

    private ScrollView weatherLayout;

    private TextView tv_titleCity;

    private TextView tv_titleUpdateTime;

    private TextView tv_degreeText;

    private TextView tv_weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView tv_aqiText;

    private TextView tv_pm25Text;

    private TextView tv_comfortText;

    private TextView tv_carWashText;

    private TextView tv_sportText;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        drawerLayout = findViewById(R.id.drawerLayout);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        iv_bingPicture = findViewById(R.id.iv_bingPicture);
        weatherLayout = findViewById(R.id.weatherLayout);
        tv_titleCity = findViewById(R.id.tv_city);
        tv_titleUpdateTime = findViewById(R.id.tv_updateTime);
        tv_degreeText = findViewById(R.id.tv_degree);
        tv_weatherInfoText = findViewById(R.id.tv_weatherInfo);
        forecastLayout = findViewById(R.id.forecastLayout);
        tv_aqiText = findViewById(R.id.tv_aqi);
        tv_pm25Text = findViewById(R.id.tv_pm25);
        tv_comfortText = findViewById(R.id.tv_comfort);
        tv_carWashText = findViewById(R.id.tv_wash);
        tv_sportText = findViewById(R.id.tv_sport);

        findViewById(R.id.btn_nav).setOnClickListener(this);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sharedPreferences.getString("weather", null);

        /*
         * 加载必应每日一图
         */
        String bingPicture = sharedPreferences.getString("bingPicture", null);
        if (bingPicture != null) {
            Glide.with(this).load(bingPicture).into(iv_bingPicture);
        } else {
            loadBingPicture();
        }

        /*
         * 加载天气信息
         */
        if (weatherString != null) {
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        } else {
            //无缓存时去服务器查询天气
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }

        /*
         * 下拉更新
         */
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String weatherString = sharedPreferences.getString("weather", null);
                Weather weather = Utility.handleWeatherResponse(weatherString);
                if (weather != null && "ok".equals(weather.status)) {
                    String weatherId = weather.basic.weatherId;
                    requestWeather(weatherId);
                }
            }
        });

        replaceFragment(new HomeFragment());
    }

    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=cbeebc08bf234a9092d7206d868d3587";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        Snackbar snackbar = Snackbar.make(weatherLayout, "无网络!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                            Snackbar snackbar = Snackbar.make(weatherLayout, "天气更新成功!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            View snackView = snackbar.getView();
                            snackView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            Snackbar snackbar = Snackbar.make(weatherLayout, "获取天气信息失败!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            View snackView = snackbar.getView();
                            snackView.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 处理并展示Weather实体类中的数据
     */
    private void showWeatherInfo(Weather weather) {
        if (weather != null && "ok".equals(weather.status)) {
            //启动后台服务
            Intent intent = new Intent(this, AutoUpdateService.class);
            startService(intent);
            String cityName = weather.basic.cityName;
            String updateTime = weather.basic.update.updateTime.split(" ")[1];
            String degree = weather.now.temperature + "℃";
            String weatherInfo = weather.now.more.info;
            tv_titleCity.setText(cityName);
            tv_titleUpdateTime.setText(updateTime);
            tv_degreeText.setText(degree);
            tv_weatherInfoText.setText(weatherInfo);
            forecastLayout.removeAllViews();
            for (Forecast forecast : weather.forecastList) {
                View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
                TextView tv_data = view.findViewById(R.id.tv_data);
                TextView tv_info = view.findViewById(R.id.tv_info);
                TextView tv_max = view.findViewById(R.id.tv_max);
                TextView tv_min = view.findViewById(R.id.tv_min);
                tv_data.setText(forecast.date);
                tv_info.setText(forecast.more.info);
                tv_max.setText(forecast.temperature.max);
                tv_min.setText(forecast.temperature.min);
                forecastLayout.addView(view);
            }
            if (weather.aqi != null) {
                tv_aqiText.setText(weather.aqi.city.aqi);
                tv_pm25Text.setText(weather.aqi.city.pm25);
            }
            String comfort = "舒适度:" + weather.suggestion.comfort.info;
            String carWash = "洗车指数:" + weather.suggestion.carWash.info;
            String sport = "运动建议:" + weather.suggestion.sport.info;
            tv_comfortText.setText(comfort);
            tv_carWashText.setText(carWash);
            tv_sportText.setText(sport);
            weatherLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 从服务器上获取必应每日一图
     */
    public void loadBingPicture() {
        showProgressDialog();
        String requestBingPicture = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPicture, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Snackbar snackbar = Snackbar.make(weatherLayout, "无网络!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPicture = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bingPicture", bingPicture);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Snackbar snackbar = Snackbar.make(weatherLayout, "必应每日一图更新成功!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Glide.with(WeatherActivity.this).load(bingPicture).into(iv_bingPicture);
                    }
                });
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nav:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }
}
