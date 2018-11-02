package com.example.Tiga.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<Book> books;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookstore_item, parent, false);
        final ShoppingCartAdapter.ViewHolder holder = new ShoppingCartAdapter.ViewHolder(view);
        holder.BookView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("通知:");
                builder.setMessage("要移除这本书吗?");
                builder.setCancelable(true);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = holder.getAdapterPosition();
                        Book book = books.get(position);
                        BookStoreActivity.BooksForShoppingCart.remove(book);
                        Toast.makeText(v.getContext(), "已移除!", Toast.LENGTH_SHORT).show();
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
        holder.BookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Book book = books.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("Book", book);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(v.getContext(), BookDetailsActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.Cover.setImageResource(book.getCover());
        holder.Name.setText("书名:" + book.getName());
        holder.Author.setText("作者:" + book.getAuthor());
        holder.Prices.setText("价格:" + String.valueOf(book.getPrice()));
        holder.Pages.setText("页数:" + String.valueOf(book.getPages()));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView Cover;
        TextView Name;
        TextView Author;
        TextView Prices;
        TextView Pages;
        View BookView;

        public ViewHolder(View view) {
            super(view);
            Cover = view.findViewById(R.id.iv_BookCover);
            Name = view.findViewById(R.id.tv_BookName);
            Author = view.findViewById(R.id.tv_BookAuthor);
            Prices = view.findViewById(R.id.tv_BookPrices);
            Pages = view.findViewById(R.id.tv_BookPages);
            BookView = view;
        }

    }

    public ShoppingCartAdapter(List<Book> bookList) {
        books = bookList;
    }

    public void RemoveBook(int position) {
        books.remove(position);
        notifyItemRemoved(position);
    }

}
