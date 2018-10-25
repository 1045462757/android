package com.example.Tiga.Main;

import java.util.ArrayList;
import java.util.List;

public class VersionData {

    private String title;
    private String time;
    private String content;
    private VersionData versionData;
    public  List<VersionData> VersionDataList = new ArrayList<>();

    public VersionData(String title, String time, String content) {
        this.title = title;
        this.time = time;
        this.content = content;
    }

    public VersionData(){
        addVersionData();
    }

    public String getTitle(){
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    //添加记录
    public void addVersionData(){

        versionData = new VersionData("     1.0     ","     2018.10.24     ","     节日也要努力写代码呀!");
        VersionDataList.add(versionData);



    }
}
