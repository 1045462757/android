package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class myStudyActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_study);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_1:
                intent.setClass(myStudyActivity.this, WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_2:
                break;
            case R.id.btn_3:
                intent.setClass(myStudyActivity.this, OkHttpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_4:
                intent.setClass(myStudyActivity.this, ParseXmlActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_5:
                intent.setClass(myStudyActivity.this, CallActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_6:
                intent.setClass(myStudyActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_7:
                intent.setClass(myStudyActivity.this, NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_8:
                break;
        }
    }
}
