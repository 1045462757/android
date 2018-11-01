package com.example.Tiga.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookStoreAdapter extends RecyclerView.Adapter<BookStoreAdapter.ViewHolder> {

    private List<Book> books;
    public static ArrayList<Book> ShoppingCart = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookstore_item, parent, false);
        final BookStoreAdapter.ViewHolder holder = new BookStoreAdapter.ViewHolder(view);
        holder.BookView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("通知:");
                builder.setMessage("要加入购物车吗?");
                builder.setCancelable(true);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = holder.getAdapterPosition();
                        Book book = books.get(position);
                        ShoppingCart.add(book);
                        Toast.makeText(v.getContext(), "已成功添加至购物车!", Toast.LENGTH_SHORT).show();
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

    public BookStoreAdapter(List<Book> bookList) {
        books = bookList;
    }
}
