package com.example.Tiga.Main;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ParseXmlActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_PullParseLocal;
    private Button btn_PullParseHttp;
    private Button btn_SaxParseLocal;
    private Button btn_SaxParseHttp;
    private Button btn_JsonObjectParseHttp;
    private TextView tv_Parse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_xml);

        btn_PullParseLocal = findViewById(R.id.btn_PullParseLocal);
        btn_PullParseHttp = findViewById(R.id.btn_PullParseHttp);
        btn_SaxParseLocal = findViewById(R.id.btn_SaxParseLocal);
        btn_SaxParseHttp = findViewById(R.id.btn_SaxParseHttp);
        btn_JsonObjectParseHttp = findViewById(R.id.btn_JsonObjectParseHttp);
        tv_Parse = findViewById(R.id.tv_parse);

        btn_PullParseLocal.setOnClickListener(this);
        btn_PullParseHttp.setOnClickListener(this);
        btn_SaxParseLocal.setOnClickListener(this);
        btn_SaxParseHttp.setOnClickListener(this);
        btn_JsonObjectParseHttp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String ParseData;
        switch (v.getId()) {
            case R.id.btn_PullParseLocal:
                ParseData = PullParseLocal().toString();
                tv_Parse.setText(ParseData);
                break;
            case R.id.btn_PullParseHttp:
                GetXmlDataForPull();
                break;
            case R.id.btn_SaxParseLocal:
                break;
            case R.id.btn_SaxParseHttp:
                GetXmlDataForSax();
                break;
            case R.id.btn_JsonObjectParseHttp:
                GetJsonDataForJsonObject();
                break;
        }
    }

    class User {

        private int PhotoId;
        private String Account;
        private String Name;
        private String Sex;
        private String Age;

        public User() {
        }

        public User(int photoId, String account, String name, String sex, String age) {
            PhotoId = photoId;
            Account = account;
            Name = name;
            Sex = sex;
            Age = age;
        }

        public int getPhotoId() {
            return PhotoId;
        }

        public void setPhotoId(int photoId) {
            PhotoId = photoId;
        }

        public String getAccount() {
            return Account;
        }

        public void setAccount(String account) {
            Account = account;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        @Override
        public String toString() {
            return "Account:" + getAccount() + "  Name:" + getName() + "  Sex:" + getSex() + "  Age:" + getAge();
        }
    }

    class ContentHandler extends DefaultHandler {

        private String nodeName;
        private StringBuilder Account;
        private StringBuilder Name;
        private StringBuilder Sex;
        private StringBuilder Age;

        @Override
        public void startDocument() throws SAXException {
            Account = new StringBuilder();
            Name = new StringBuilder();
            Sex = new StringBuilder();
            Age = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            nodeName = localName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if ("Account".equals(nodeName)) {
                Account.append(ch, start, length);
            } else if ("Name".equals(nodeName)) {
                Name.append(ch, start, length);
            } else if ("Sex".equals(nodeName)) {
                Sex.append(ch, start, length);
            } else if ("Age".equals(nodeName)) {
                Age.append(ch, start, length);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("User".equals(localName)) {
                Log.d("ParseXmlActivity", "Account is " + Account.toString().trim());
                Log.d("ParseXmlActivity", "Name is " + Name.toString().trim());
                Log.d("ParseXmlActivity", "Sex is " + Sex.toString().trim());
                Log.d("ParseXmlActivity", "Age is " + Age.toString().trim());
                Account.setLength(0);
                Name.setLength(0);
                Sex.setLength(0);
                Age.setLength(0);
            } else if ("Users".equals(localName)) {
                Log.d("ParesXmlActivity", "全部数据已解析完毕,解析地址为:http://10.100.247.208:8080/xml/user.xml,解析方法为Sax");
            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }

    //Pull解析本地xml文件
    private ArrayList<User> PullParseLocal() {
        ArrayList<User> users = new ArrayList<>();
        try {
            XmlResourceParser parser = this.getResources().getXml(R.xml.user);  //获取本地xml文件
            User user = new User();
            int eventType = parser.getEventType();
            while (eventType != parser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("Account".equals(nodeName)) {
                            user.setAccount(parser.nextText());
                            Log.d("ParseXmlActivity", "Account is " + user.getAccount());
                        } else if ("Name".equals(nodeName)) {
                            user.setName(parser.nextText());
                            Log.d("ParseXmlActivity", "Name is " + user.getName());
                        } else if ("Sex".equals(nodeName)) {
                            user.setSex(parser.nextText());
                            Log.d("ParseXmlActivity", "Sex is " + user.getSex());
                        } else if ("Age".equals(nodeName)) {
                            user.setAge(parser.nextText());
                            Log.d("ParseXmlActivity", "Age is " + user.getAge());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("User".equals(nodeName)) {
                            Log.d("ParesXmlActivity", "一条数据已解析完毕");
                            users.add(user);
                            user = new User();
                        } else if ("Users".equals(nodeName)) {
                            Log.d("ParesXmlActivity", "全部数据已解析完毕,解析地址为本地文件,解析方法为Pull");
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return users;
        }

    }

    //获取网络xml文件采用Pull解析
    private void GetXmlDataForPull() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.100.247.208:8080/xml/user.xml").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String ResponseData = response.body().string();
                    PullParseHttp(ResponseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Pull解析网络xml文件
    private void PullParseHttp(String xmlData) {
        ArrayList<User> users = new ArrayList<>();
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            User user = new User();
            int eventType = xmlPullParser.getEventType();
            while (eventType != xmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("Account".equals(nodeName)) {
                            user.setAccount(xmlPullParser.nextText());
                            Log.d("ParseXmlActivity", "Account is " + user.getAccount());
                        } else if ("Name".equals(nodeName)) {
                            user.setName(xmlPullParser.nextText());
                            Log.d("ParseXmlActivity", "Name is " + user.getName());
                        } else if ("Sex".equals(nodeName)) {
                            user.setSex(xmlPullParser.nextText());
                            Log.d("ParseXmlActivity", "Sex is " + user.getSex());
                        } else if ("Age".equals(nodeName)) {
                            user.setAge(xmlPullParser.nextText());
                            Log.d("ParseXmlActivity", "Age is " + user.getAge());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("User".equals(nodeName)) {
                            Log.d("ParesXmlActivity", "一条数据已解析完毕");
                            users.add(user);
                            user = new User();
                        } else if ("Users".equals(nodeName)) {
                            Log.d("ParesXmlActivity", "全部数据已解析完毕,解析地址为:http://10.100.247.208:8080/xml/user.xml,解析方法为Pull");
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
        } finally {
            tv_Parse.setText(users.toString());
        }
    }

    //Sax解析本地xml文件
    private ArrayList<User> SaxParseLocal() {
        ArrayList<User> users = new ArrayList<>();
        return users;
    }

    //获取网络xml文件采用Sax解析
    private void GetXmlDataForSax() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.100.247.208:8080/xml/user.xml").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String ResponseData = response.body().string();
                    SaxParseHttp(ResponseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Sax解析网络xml文件
    private void SaxParseHttp(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler contentHandler = new ContentHandler();
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取网络json文件采用JSONObject解析
    private void GetJsonDataForJsonObject() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.100.247.208:8080/xml/user.json").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String ResponseData = response.body().string();
                    JsonObjectParseHttp(ResponseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //JSONObject解析网络json文件
    private void JsonObjectParseHttp(String jsonData) {
        ArrayList<User> users = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = new User();
                user.setAccount(jsonObject.getString("Account"));
                user.setName(jsonObject.getString("Name"));
                user.setSex(jsonObject.getString("Sex"));
                user.setAge(jsonObject.getString("Age"));
                users.add(user);
                Log.d("ParseXmlActivity", "Account is " + user.getAccount());
                Log.d("ParseXmlActivity", "Name is " + user.getName());
                Log.d("ParseXmlActivity", "Sex is " + user.getSex());
                Log.d("ParseXmlActivity", "Age is " + user.getAge());
            }
            Log.d("ParesXmlActivity", "全部数据已解析完毕,解析地址为:http://10.100.247.208:8080/xml/user.json,解析方法为JSONObject");
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            tv_Parse.setText(users.toString());
        }
    }

}
