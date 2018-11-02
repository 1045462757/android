package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExploreActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        findViewById(R.id.btn_BookStore).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_BookStore:
                Intent intent = new Intent();
                intent.setClass(ExploreActivity.this, BookStoreActivity.class);
                startActivity(intent);
                break;
        }
    }
}
