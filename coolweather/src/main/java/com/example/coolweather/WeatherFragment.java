package com.example.coolweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherFragment extends Fragment {

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
    public SwipeRefreshLayout swipeRefresh;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        tv_titleCity = view.findViewById(R.id.tv_city);
        tv_titleUpdateTime = view.findViewById(R.id.tv_updateTime);
        tv_degreeText = view.findViewById(R.id.tv_degree);
        tv_weatherInfoText = view.findViewById(R.id.tv_weatherInfo);
        forecastLayout = view.findViewById(R.id.forecastLayout);
        tv_aqiText = view.findViewById(R.id.tv_aqi);
        tv_pm25Text = view.findViewById(R.id.tv_pm25);
        tv_comfortText = view.findViewById(R.id.tv_comfort);
        tv_carWashText = view.findViewById(R.id.tv_wash);
        tv_sportText = view.findViewById(R.id.tv_sport);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String weatherString = sharedPreferences.getString("weather", null);

        /*
         * 加载天气信息
         */
        if (weatherString != null) {
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        }

        /*
         * 下拉更新
         */
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Weather weather = Utility.handleWeatherResponse(weatherString);
                if (weather != null && "ok".equals(weather.status)) {
                    String weatherId = weather.basic.weatherId;
                    requestWeather(weatherId);
                }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        Snackbar snackbar = Snackbar.make(getView(), "无网络!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d("WeatherFragment", "服务器返回的信息:" + responseText);
                final Weather weather = Utility.handleWeatherResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                            Snackbar snackbar = Snackbar.make(getView(), "天气更新成功!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            View snackView = snackbar.getView();
                            snackView.setBackgroundColor(getResources().getColor(R.color.colorSuccess));
                        } else {
                            Snackbar snackbar = Snackbar.make(getView(), "获取天气信息失败!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            View snackView = snackbar.getView();
                            snackView.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }, 1);
    }

    /**
     * 处理并展示Weather实体类中的数据
     */
    private void showWeatherInfo(Weather weather) {
        if (weather != null && "ok".equals(weather.status)) {
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
                View view = LayoutInflater.from(getContext()).inflate(R.layout.forecast_item, forecastLayout, false);
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
        }
    }
}
