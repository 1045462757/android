package com.example.Tiga.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookStoreFragment extends Fragment {

    List<Book> books = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookstore, null);
        initBookStore();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tv_BookNum = getActivity().findViewById(R.id.tv_BookNum);

        tv_BookNum.setText("书籍数目:" + books.size());
        RecyclerView rv_BookStore = getActivity().findViewById(R.id.rv_BookStore);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_BookStore.setLayoutManager(linearLayoutManager);
        BookStoreAdapter bookStoreAdapter = new BookStoreAdapter(books);
        rv_BookStore.setAdapter(bookStoreAdapter);
        rv_BookStore.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    //初始化数据
    private void initBookStore() {
        Book book1 = new Book(R.drawable.book1, "漫步太阳系", "王爽", 49.00, 199);
        Book book2 = new Book(R.drawable.book2, "侏罗纪的恐龙", "格利高利.圣.保罗", 113.40, 88);
        Book book3 = new Book(R.drawable.book3, "时间简史从芝诺悖论到引力波", "朱伟勇 朱海松", 69.40, 213);
        Book book4 = new Book(R.drawable.book4, "世界奇异现象档案录", "尔东", 21.3, 324);
        Book book5 = new Book(R.drawable.book5, "生活中不可不知的冷门知识", "霁阳", 21.6, 156);
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
    }
}
