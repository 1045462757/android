package com.example.Tiga.Main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QQActivity extends BaseActivity {

    private TextView tv;
    private Button btn_Message;
    private Button btn_Contact;
    private Button btn_Fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        tv = findViewById(R.id.tv_qq);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String User = bundle.getString("QQ号");
        String PassWord = bundle.getString("密码");
        tv.setText("QQ号:"+User+"  密码:"+PassWord);

        btn_Message = findViewById(R.id.btn_Message);
        btn_Contact = findViewById(R.id.btn_Contact);
        btn_Fun = findViewById(R.id.btn_Fun);

        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (v.getId()) {
                    case R.id.btn_Message:
                        MessageFragment messageFragment = new MessageFragment();
                        ft.replace(R.id.fragment, messageFragment);
                        ft.commit();
                        break;
                    case R.id.btn_Contact:
                        ContactFragment contactFragment = new ContactFragment();
                        ft.replace(R.id.fragment, contactFragment);
                        ft.commit();
                        break;
                    case R.id.btn_Fun:
                        FunFragment funFragment = new FunFragment();
                        ft.replace(R.id.fragment, funFragment);
                        ft.commit();
                        break;
                    default:
                        break;
                }
            }
        };
        btn_Message.setOnClickListener(l);
        btn_Contact.setOnClickListener(l);
        btn_Fun.setOnClickListener(l);
    }
}
