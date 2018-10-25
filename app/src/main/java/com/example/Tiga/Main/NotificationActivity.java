package com.example.Tiga.Main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class NotificationActivity extends BaseActivity {

    Button Send_Notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Send_Notice = findViewById(R.id.send_notice);
        Send_Notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    public void sendNotification() {

        //Android 8.0 版本以上
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            //设置通知渠道
            String channelId = "channel_1";
            String channelName = "渠道一";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String Description = "(这条渠道很重要哦!)这是渠道一的描述";
            NotificationChannel channel = new NotificationChannel(channelId,channelName,importance);
            channel.setDescription(Description);
            channel.enableLights(true);
            channel.enableVibration(true);
            NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

            //设置通知内容
            Notification.Builder builder = new Notification.Builder(this,channelId);
            builder.setSmallIcon(R.mipmap.icon)
                    .setContentTitle("tiga的消息哟")
                    .setContentText("通知终于成功了呢!!!")
                    .setAutoCancel(true);

            //通知Action
            Intent intent = new Intent(NotificationActivity.this,DetailActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
            builder.setContentIntent(pendingIntent);

            //发送通知
            notificationManager.notify(1,builder.build());
        }

    }

}
