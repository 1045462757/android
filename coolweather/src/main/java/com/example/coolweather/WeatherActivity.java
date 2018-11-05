package com.example.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

public class WeatherActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    private Button btn_nav;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        drawerLayout = findViewById(R.id.drawerLayout);
        btn_nav = findViewById(R.id.btn_nav);
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

        btn_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sharedPreferences.getString("weather", null);

        /**
         * 加载必应每日一图
         */
        String bingPicture = sharedPreferences.getString("bingPicture", null);
        if (bingPicture != null) {
            Glide.with(this).load(bingPicture).into(iv_bingPicture);
        } else {
            loadBingPicture();
        }

        /**
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
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String weatherString = sharedPreferences.getString("weather", null);
                Weather weather = Utility.handleWeatherResponse(weatherString);
                String weatherId = weather.basic.weatherId;
                requestWeather(weatherId);
                loadBingPicture();
            }
        });
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
                        Toast.makeText(WeatherActivity.this, "无网络!", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
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
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败!", Toast.LENGTH_SHORT).show();
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
        } else {
            Toast.makeText(this, "启动后台服务更新失败!", Toast.LENGTH_SHORT).show();
        }
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
//        Toast.makeText(this, "更新天气成功！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 从服务器上获取必应每日一图
     */
    private void loadBingPicture() {
        String requestBingPicture = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPicture, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(WeatherActivity.this, "无网络,更新必应每日一图失败！", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(WeatherActivity.this, "成功更新必应每日一图!", Toast.LENGTH_SHORT).show();
                        Glide.with(WeatherActivity.this).load(bingPicture).into(iv_bingPicture);
                    }
                });
            }
        });
    }
}
