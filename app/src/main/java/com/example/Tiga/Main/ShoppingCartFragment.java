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

public class ShoppingCartFragment extends Fragment {

    List<Book> books = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tv_BookNum = getActivity().findViewById(R.id.tv_BookNum);

        books = BookStoreAdapter.ShoppingCart;
        tv_BookNum.setText("书籍数目:" + books.size());
        RecyclerView rv_ShoppingCart = getActivity().findViewById(R.id.rv_ShoppingCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_ShoppingCart.setLayoutManager(linearLayoutManager);
        BookStoreAdapter adapter = new BookStoreAdapter(books);
        rv_ShoppingCart.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rv_ShoppingCart.setAdapter(adapter);
    }
}
