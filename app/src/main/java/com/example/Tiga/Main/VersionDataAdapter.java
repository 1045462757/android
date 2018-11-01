package com.example.Tiga.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class VersionDataAdapter extends ArrayAdapter<VersionData> {

    private int resourceId;

    public VersionDataAdapter(Context context, int textViewResourceId, List<VersionData> versionData) {
        super(context, textViewResourceId, versionData);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        VersionData versionData = getItem(position);  //获取当前VersionData实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_time = view.findViewById(R.id.tv_time);
        TextView tv_content = view.findViewById(R.id.tv_content);

        tv_title.setText(versionData.getTitle());
        tv_time.setText(versionData.getTime());
        tv_content.setText(versionData.getContent());
        return view;

    }
}
