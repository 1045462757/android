package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private Spinner sp_Name;

    private String[] Name = {"","1045462757", "2020118011"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_Title = findViewById(R.id.tv_Title);
        sp_Name = findViewById(R.id.sp_Name);
        tv_Title.setOnClickListener(this);
        tv_Title.setOnLongClickListener(this);
        sp_Name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "你选择了"+Name[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Name);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp_Name.setPrompt("账号");
        sp_Name.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
