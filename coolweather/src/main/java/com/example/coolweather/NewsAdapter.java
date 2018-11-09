package com.example.coolweather;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coolweather.gson.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> news;
    private MainActivity mainActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_newsTitle;
        TextView tv_newsDate;
        TextView tv_newsCategory;
        TextView tv_newsAuthorName;
        TextView tv_newsUrl;
        View itemsView;

        public ViewHolder(View view) {
            super(view);
            tv_newsTitle = view.findViewById(R.id.tv_newsTitle);
            tv_newsDate = view.findViewById(R.id.tv_newsDate);
            tv_newsCategory = view.findViewById(R.id.tv_newsCategory);
            tv_newsAuthorName = view.findViewById(R.id.tv_newsAuthorName);
            tv_newsUrl = view.findViewById(R.id.tv_newsUrl);
            itemsView = view;
        }
    }

    public NewsAdapter(MainActivity activity, ArrayList<News> news) {
        this.news = news;
        mainActivity = activity;
    }

    public NewsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items, parent, false);
        final NewsAdapter.ViewHolder holder = new NewsAdapter.ViewHolder(view);
        holder.itemsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "新闻详情还在开发中呀!", Snackbar.LENGTH_SHORT);
                snackbar.show();
                View snackView = snackbar.getView();
                snackView.setBackgroundColor(v.getResources().getColor(R.color.colorTip));
            }
        });
        holder.tv_newsUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_newsUrl.setTextColor(v.getResources().getColor(R.color.colorWarning));
                Intent intent = new Intent();
                Uri content_url;
                String newsUrl = holder.tv_newsUrl.getText().toString();
                intent.setAction("android.intent.action.VIEW");
                content_url = Uri.parse(newsUrl);
                intent.setData(content_url);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        News news1 = news.get(position);
        holder.tv_newsTitle.setText(news1.title);
        holder.tv_newsDate.setText(news1.date);
        holder.tv_newsCategory.setText(news1.category);
        holder.tv_newsAuthorName.setText(news1.author_name);
        holder.tv_newsUrl.setText(news1.url);
    }

    public int getItemCount() {
        return news.size();
    }

    public void addData(ArrayList<News> new1) {
        ArrayList<News> new2 = new ArrayList<>();
        for (News new3 : new1) {
            new2.add(new3);
        }

        for (int i = 0; i < new2.size(); i++) {
            news.add(0, new2.get(i));
            notifyItemInserted(0);
        }
    }
}