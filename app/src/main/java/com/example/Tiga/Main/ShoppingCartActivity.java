package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingCartActivity extends BaseActivity {

    private ArrayList<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bookList = bundle.getParcelableArrayList("ShoppingCart");

        RecyclerView recyclerView = findViewById(R.id.rv_ShoppingCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        BookStoreAdapter adapter = new BookStoreAdapter(bookList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}
