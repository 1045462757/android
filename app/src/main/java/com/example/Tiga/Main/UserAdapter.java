package com.example.Tiga.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tiga.Login.User;

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
        holder.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RecyclerViewActivity.User user = users.get(position);
                Toast.makeText(v.getContext(), String.valueOf(user.getPhotoId()), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RecyclerViewActivity.User user = users.get(position);
                Toast.makeText(v.getContext(), user.getAccount(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RecyclerViewActivity.User user = users.get(position);
                Toast.makeText(v.getContext(), user.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RecyclerViewActivity.User user = users.get(position);
                Toast.makeText(v.getContext(), user.getSex(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RecyclerViewActivity.User user = users.get(position);
                Toast.makeText(v.getContext(), user.getAge(), Toast.LENGTH_SHORT).show();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("通知:");
                builder.setMessage("你确定要删除数据吗?");
                builder.setCancelable(true);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(), "暂时删除不了哦!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true;
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

}
