package com.example.Tiga.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.Tiga.Login.LoginActivity;

public class BaseActivity extends AppCompatActivity {

    private OffLineReceiver offLineReceiver;  //下线广播接收器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    protected void onPause() {
        super.onPause();
        if (offLineReceiver != null) {
            unregisterReceiver(offLineReceiver);
            offLineReceiver = null;
        }
    }

    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.OFFLINE");
        offLineReceiver = new OffLineReceiver();
        registerReceiver(offLineReceiver, intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class OffLineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("通知:");
            builder.setMessage("你确定要下线吗?");
            builder.setCancelable(false);
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();
                    Intent intent = new Intent();
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }

}
