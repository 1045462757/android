package com.example.Tiga.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tiga.Login.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Account;
        TextView PassWord;
        TextView Name;
        TextView Sex;
        TextView Age;
        TextView Introduction;
        View UserView;

        public ViewHolder(View view) {
            super(view);
            Account = view.findViewById(R.id.tv_Account);
            PassWord = view.findViewById(R.id.tv_PassWord);
            Name = view.findViewById(R.id.tv_Name);
            Sex = view.findViewById(R.id.tv_Sex);
            Age = view.findViewById(R.id.tv_Age);
            Introduction = view.findViewById(R.id.tv_Introduction);
            UserView = view;
        }
    }

    public UserAdapter(List<User> userList) {
        users = userList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                User user = users.get(position);
                Toast.makeText(v.getContext(), user.getAccount(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                User user = users.get(position);
                Toast.makeText(v.getContext(), user.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.PassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                User user = users.get(position);
                Toast.makeText(v.getContext(), user.getPassWord(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                User user = users.get(position);
                Toast.makeText(v.getContext(), user.getSex(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                User user = users.get(position);
                Toast.makeText(v.getContext(), String.valueOf(user.getAge()), Toast.LENGTH_SHORT).show();
            }
        });
        holder.Introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                User user = users.get(position);
                Toast.makeText(v.getContext(), user.getIntroduction(), Toast.LENGTH_SHORT).show();
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
        User user = users.get(position);
        holder.Account.setText(user.getAccount());
        holder.PassWord.setText(user.getPassWord());
        holder.Name.setText(user.getName());
        holder.Sex.setText(user.getSex());
        holder.Age.setText(String.valueOf(user.getAge()));
        holder.Introduction.setText(user.getIntroduction());
    }

    public int getItemCount() {
        return users.size();
    }

    public void addItem(){
        User user = new User("0","0","新用户","x",0,"xxx");
        users.add(0,user);
        notifyItemInserted(0);
    }

    public void DeleteItem(int position){
        users.remove(position);
        notifyItemRemoved(position);
    }
}
