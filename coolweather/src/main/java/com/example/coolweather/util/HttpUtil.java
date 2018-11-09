package com.example.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback, int num) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        for (int i = 0; i < num; i++) {
            client.newCall(request).enqueue(callback);
        }
    }
}
