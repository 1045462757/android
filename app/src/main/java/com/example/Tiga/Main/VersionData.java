package com.example.Tiga.Main;

import java.util.ArrayList;
import java.util.List;

public class VersionData {

    private String title;
    private String time;
    private String content;
    private VersionData versionData;
    public List<VersionData> VersionDataList = new ArrayList<>();

    public VersionData(String title, String time, String content) {
        this.title = title;
        this.time = time;
        this.content = content;
    }

    public VersionData() {
        addVersionData();
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    //添加记录
    public void addVersionData() {
        versionData = new VersionData("1.0", "2018.10.24", "节日也要努力写代码呀!");
        VersionDataList.add(versionData);
        versionData = new VersionData("1.1", "2018.10.31", "一周过去了，没有多少长进呀!");
        VersionDataList.add(versionData);
        versionData = new VersionData("1.2", "2018.11.01", "基本地实现了书城功能，还有很多bug，代码也不够简练，仍需多多努力!");
        VersionDataList.add(versionData);
    }
}
