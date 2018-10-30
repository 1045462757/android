package com.example.Tiga.Main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
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
        switch (v.getId()) {
            case R.id.btn_Send:
                sendRequestWithOkHttp();
                break;
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.100.247.208:8080/xml/user.xml").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String ResponseData = response.body().string();
                    parseWithPull(ResponseData);
                    showResponse(ResponseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_Data.setText(response);
            }
        });
    }

    private void parseWithPull(String xmlData) {
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String Account = "";
            String Name = "";
            String Sex = "";
            String Age = "";
            while (eventType != xmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("Account".equals(nodeName)) {
                            Account = xmlPullParser.nextText();
                        } else if ("Name".equals(nodeName)) {
                            Name = xmlPullParser.nextText();
                        } else if ("Sex".equals(nodeName)) {
                            Sex = xmlPullParser.nextText();
                        } else if ("Age".equals(nodeName)) {
                            Age = xmlPullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("User".equals(nodeName)) {
                            Log.d("OkHttpActivity", "Account is "+Account);
                            Log.d("OkHttpActivity", "Name is "+Name);
                            Log.d("OkHttpActivity", "Sex is "+Sex);
                            Log.d("OkHttpActivity", "Age is "+Age);
                            Log.d("OkHttpActivity", "一条数据已解析完毕");
                        } else if ("Users".equals(nodeName)) {
                            Log.d("OkHttpActivity", "全部数据已解析完毕，解析地址为:http://10.100.247.208:8080/xml/user.xml");
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
