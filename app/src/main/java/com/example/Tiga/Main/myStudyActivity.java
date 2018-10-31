package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class myStudyActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;
    private Button mBtn6;
    private Button mBtn7;
    private Button mBtn8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_study);

        mBtn1 = findViewById(R.id.btn_1);
        mBtn2 = findViewById(R.id.btn_2);
        mBtn3 = findViewById(R.id.btn_3);
        mBtn4 = findViewById(R.id.btn_4);
        mBtn5 = findViewById(R.id.btn_5);
        mBtn6 = findViewById(R.id.btn_6);
        mBtn7 = findViewById(R.id.btn_7);
        mBtn8 = findViewById(R.id.btn_8);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
        mBtn7.setOnClickListener(this);
        mBtn8.setOnClickListener(this);

    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_1:
                intent.setClass(myStudyActivity.this,WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_2:
                intent.setClass(myStudyActivity.this, BookActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_3:
                intent.setClass(myStudyActivity.this,OkHttpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_4:
                intent.setClass(myStudyActivity.this,ParseXmlActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_5:
                intent.setClass(myStudyActivity.this, CallActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_6:
                intent.setClass(myStudyActivity.this,RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_7:
                intent.setClass(myStudyActivity.this, NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_8:
                intent.setClass(myStudyActivity.this, DialogActivity.class);
                startActivity(intent);
                break;
        }
    }
}
