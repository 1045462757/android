package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Call2Activity extends BaseActivity {

    private TextView tv_Phone;
    private TextView tv_Phone1;
    private TextView tv_Phone2;

    private TextView tv_Sms;
    private TextView tv_Sms1;
    private TextView tv_Sms2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call2);

        tv_Phone = findViewById(R.id.tv_phone);
        tv_Phone1 = findViewById(R.id.tv_phone1);
        tv_Phone2 = findViewById(R.id.tv_phone2);

        tv_Sms = findViewById(R.id.tv_sms);
        tv_Sms1 = findViewById(R.id.tv_sms1);
        tv_Sms2 = findViewById(R.id.tv_sms2);

        tv_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String Phone = tv_Phone.getText().toString();
                intent.putExtra("Phone",Phone);
                setResult(1,intent);
                finish();
            }
        });

        tv_Phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String Phone = tv_Phone1.getText().toString();
                intent.putExtra("Phone",Phone);
                setResult(1,intent);
                finish();
            }
        });

        tv_Phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String Phone = tv_Phone2.getText().toString();
                intent.putExtra("Phone",Phone);
                setResult(1,intent);
                finish();
            }
        });

        tv_Sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String Phone = tv_Sms.getText().toString();
                intent.putExtra("Sms",Phone);
                setResult(2,intent);
                finish();
            }
        });

        tv_Sms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String Phone = tv_Sms1.getText().toString();
                intent.putExtra("Sms",Phone);
                setResult(2,intent);
                finish();
            }
        });

        tv_Sms2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String Phone = tv_Sms2.getText().toString();
                intent.putExtra("Sms",Phone);
                setResult(2,intent);
                finish();
            }
        });

    }
}
