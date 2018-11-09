package com.example.coolweather;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final ArrayList<String> items = new ArrayList<>(Arrays.asList("选择城市", "更新每日一图", "更新随机图片", "设置", "关于", "天气api控制台", "点睛数据api", "聚合数据api"));
    private MainActivity mainActivity;
    private int[] icons = {R.drawable.select, R.drawable.update, R.drawable.update, R.drawable.setting, R.drawable.about, R.drawable.about, R.drawable.about, R.drawable.about};

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        TextView tv_items;
        View itemsView;

        public ViewHolder(View view) {
            super(view);
            iv_icon = view.findViewById(R.id.iv_icon);
            tv_items = view.findViewById(R.id.tv_items);
            itemsView = view;
        }
    }

    public HomeAdapter(MainActivity activity) {
        mainActivity = activity;
    }

    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.itemsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent();
                Uri content_url;
                switch (position) {
                    case 0:
                        mainActivity.replaceFragment(MainActivity.chooseAreaFragment);
                        break;
                    case 1:
                        mainActivity.drawerLayout.closeDrawers();
                        mainActivity.loadBingBackgroundPicture(MainActivity.requestBingPicture);
                        break;
                    case 2:
                        mainActivity.drawerLayout.closeDrawers();
                        mainActivity.loadBingBackgroundPicture(MainActivity.requestRandomPicture);
                        break;
                    case 3:
                        break;
                    case 4:
                        Snackbar snackbar = Snackbar.make(v, "tiga版权所有!", Snackbar.LENGTH_SHORT);
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorTip));
                        snackbar.show();
                        break;
                    case 5:
                        intent.setAction("android.intent.action.VIEW");
                        content_url = Uri.parse("https://console.heweather.com/my/service");
                        intent.setData(content_url);
                        v.getContext().startActivity(intent);
                        break;
                    case 6:
                        intent.setAction("android.intent.action.VIEW");
                        content_url = Uri.parse("https://djapi.cn/home/");
                        intent.setData(content_url);
                        v.getContext().startActivity(intent);
                        break;
                    case 7:
                        intent.setAction("android.intent.action.VIEW");
                        content_url = Uri.parse("https://www.juhe.cn/account");
                        intent.setData(content_url);
                        v.getContext().startActivity(intent);
                        break;
                }
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = items.get(position);
        holder.iv_icon.setImageResource(icons[position]);
        holder.tv_items.setText(item);
    }

    public int getItemCount() {
        return items.size();
    }
}