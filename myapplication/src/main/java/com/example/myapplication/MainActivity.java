package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_Title = findViewById(R.id.tv_Title);
        tv_Title.setOnClickListener(this);
        tv_Title.setOnLongClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_Title:
                Toast.makeText(this, "点击了标题!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Title:
                Toast.makeText(this, "长按了标题!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
