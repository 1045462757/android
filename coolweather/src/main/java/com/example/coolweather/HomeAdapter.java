package com.example.coolweather;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<String> items;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_items;
        View itemsView;

        public ViewHolder(View view) {
            super(view);
            tv_items = view.findViewById(R.id.tv_items);
            itemsView = view;
        }
    }

    public HomeAdapter(ArrayList<String> list) {
        items = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
                        Snackbar snackbar = Snackbar.make(v, "选择城市尚在开发中,敬请期待!", Snackbar.LENGTH_SHORT);
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(v.getResources().getColor(R.color.colorAccent));
                        snackbar.show();
                        break;
                    case 1:
                        intent.setAction("android.intent.action.VIEW");
                        content_url = Uri.parse("http://guolin.tech/api/bing_pic");
                        intent.setData(content_url);
                        v.getContext().startActivity(intent);
                        break;
                    case 2:
                        intent.setAction("android.intent.action.VIEW");
                        content_url = Uri.parse("http://img.xjh.me/random_img.php?return=url?ctype=acg");
                        intent.setData(content_url);
                        v.getContext().startActivity(intent);
                        break;
                    case 3:
                        Snackbar snackbar3 = Snackbar.make(v, "设置尚在开发中,敬请期待!", Snackbar.LENGTH_SHORT);
                        View snackView3 = snackbar3.getView();
                        snackView3.setBackgroundColor(v.getResources().getColor(R.color.colorAccent));
                        snackbar3.show();
                        break;
                    case 4:
                        Snackbar snackbar4 = Snackbar.make(v, "黄一版权所有!", Snackbar.LENGTH_SHORT);
                        View snackView4 = snackbar4.getView();
                        snackView4.setBackgroundColor(v.getResources().getColor(R.color.colorTip));
                        snackbar4.show();
                        break;
                    case 5:
                        intent.setAction("android.intent.action.VIEW");
                        content_url = Uri.parse("https://console.heweather.com/my/service");
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
        holder.tv_items.setText(item);
    }

    public int getItemCount() {
        return items.size();
    }
}