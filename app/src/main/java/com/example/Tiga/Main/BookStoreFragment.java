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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BookStoreFragment extends Fragment {

    private ArrayList<Book> books = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookstore, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tv_BookNum = getActivity().findViewById(R.id.tv_BookNum);

        GetDataForBookStore();

        tv_BookNum.setText("书籍数目:" + books.size());
        RecyclerView rv_BookStore = getActivity().findViewById(R.id.rv_BookStore);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_BookStore.setLayoutManager(linearLayoutManager);
        BookStoreAdapter bookStoreAdapter = new BookStoreAdapter(books);
        rv_BookStore.setAdapter(bookStoreAdapter);
        rv_BookStore.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    //接受书城数据
    private void GetDataForBookStore() {
        Bundle bundle = getArguments();
        books = bundle.getParcelableArrayList("BooksForStore");
    }
}
