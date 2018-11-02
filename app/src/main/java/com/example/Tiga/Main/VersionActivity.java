package com.example.Tiga.Main;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class VersionActivity extends BaseActivity {


    private List<VersionData> VersionDataList = new VersionData().VersionDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        VersionDataAdapter versionDataAdapter = new VersionDataAdapter(VersionActivity.this, R.layout.version_item, VersionDataList);
        ListView listView = findViewById(R.id.lv_version);
        listView.setAdapter(versionDataAdapter);
    }


}
