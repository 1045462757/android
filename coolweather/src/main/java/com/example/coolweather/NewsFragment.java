package com.example.coolweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolweather.gson.News;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewsFragment extends Fragment {

    public SwipeRefreshLayout swipeRefresh;
    private final ArrayList<News> news = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        recyclerView = view.findViewById(R.id.RecyclerView_news);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        MainActivity activity = (MainActivity) getActivity();
        adapter = new NewsAdapter(activity, news);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(300);
        defaultItemAnimator.setRemoveDuration(300);
        recyclerView.setItemAnimator(defaultItemAnimator);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
         * 下拉更新
         */
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestNew();
            }
        });
    }

    /**
     * 从服务器上查询新闻
     */
    private void requestNew() {
        String newUrl = "http://v.juhe.cn/toutiao/index?type=&key=2be644ef94e2ad0fe29907f51f772e94";

        HttpUtil.sendOkHttpRequest(newUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        Snackbar snackbar = Snackbar.make(getView(), "无网络!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                Log.d("NewsFragment", "服务器返回的信息:" + responseText);
                final ArrayList<News> new1 = Utility.handleNewsResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (new1 != null) {
                            adapter.addData(new1);
                            recyclerView.scrollToPosition(0);
                            Snackbar snackbar = Snackbar.make(getView(), "已更新" + new1.size() + "条新闻!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            View snackView = snackbar.getView();
                            snackView.setBackgroundColor(getResources().getColor(R.color.colorSuccess));
                        } else {
                            Snackbar snackbar = Snackbar.make(getView(), "获取新闻失败!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            View snackView = snackbar.getView();
                            snackView.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }, 1);
    }
}
