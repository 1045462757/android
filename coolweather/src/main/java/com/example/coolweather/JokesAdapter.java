package com.example.coolweather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.ViewHolder> {

    private ArrayList<String> jokes;
    private MainActivity mainActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_newsContent;
        View itemsView;

        public ViewHolder(View view) {
            super(view);
            tv_newsContent = view.findViewById(R.id.tv_newsContent);
            itemsView = view;
        }
    }

    public JokesAdapter(MainActivity activity, ArrayList<String> jokes) {
        this.jokes = jokes;
        mainActivity = activity;
    }

    public JokesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jokes_items, parent, false);
        final JokesAdapter.ViewHolder holder = new JokesAdapter.ViewHolder(view);
        holder.itemsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return holder;
    }

    public void onBindViewHolder(JokesAdapter.ViewHolder holder, int position) {
        String item = jokes.get(position);
        holder.tv_newsContent.setText(item);
    }

    public int getItemCount() {
        return jokes.size();
    }

    public void addData(String joke) {
        jokes.add(0, joke);
        notifyItemInserted(0);
    }
}
