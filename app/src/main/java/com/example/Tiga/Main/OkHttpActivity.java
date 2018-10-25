package com.example.Tiga.Main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_Send;
    private TextView tv_Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        btn_Send = findViewById(R.id.btn_Send);
        tv_Data = findViewById(R.id.tv_Data);

        btn_Send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Send:
                sendRequestWithOkHttp();
                break;
        }
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://www.baidu.com").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String responsedata = response.body().string();
                    showResponse(responsedata);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_Data.setText(response);
            }
        });
    }
}
