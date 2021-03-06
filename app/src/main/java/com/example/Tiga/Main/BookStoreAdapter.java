package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookStoreAdapter extends RecyclerView.Adapter<BookStoreAdapter.ViewHolder> {

    private List<Book> books;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookstore_item, parent, false);
        final BookStoreAdapter.ViewHolder holder = new BookStoreAdapter.ViewHolder(view);
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

    public BookStoreAdapter(List<Book> bookList) {
        books = bookList;
    }

}
