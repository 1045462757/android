package com.example.Tiga.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<RecyclerViewActivity.User> users;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_photo;
        TextView Account;
        TextView Name;
        TextView Sex;
        TextView Age;
        View UserView;

        public ViewHolder(View view) {
            super(view);
            iv_photo = view.findViewById(R.id.iv_photo);
            Account = view.findViewById(R.id.tv_Account);
            Name = view.findViewById(R.id.tv_Name);
            Sex = view.findViewById(R.id.tv_Sex);
            Age = view.findViewById(R.id.tv_Age);
            UserView = view;
        }
    }

    public UserAdapter(List<RecyclerViewActivity.User> userList) {
        users = userList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.UserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RecyclerViewActivity.User user = users.get(position);
                Toast.makeText(v.getContext(), user.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        RecyclerViewActivity.User user = users.get(position);
        holder.iv_photo.setImageResource(user.getPhotoId());
        holder.Account.setText(user.getAccount());
        holder.Name.setText(user.getName());
        holder.Sex.setText(user.getSex());
        holder.Age.setText(user.getAge());
    }

    public int getItemCount() {
        return users.size();
    }

    public void addData(int position) {
        RecyclerViewActivity.User user = new RecyclerViewActivity.User(R.mipmap.file, "1045462757", "黄一", "男", "20");
        users.add(position, user);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

}
