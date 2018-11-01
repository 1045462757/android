package com.example.Tiga.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class BookStoreActivity extends BaseActivity implements View.OnClickListener {

    private BookStoreFragment bookStoreFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private Button btn_BookStore;
    private Button btn_ShoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_store);

        btn_BookStore = findViewById(R.id.btn_BookStore);
        btn_ShoppingCart = findViewById(R.id.btn_ShoppingCart);

        btn_BookStore.setOnClickListener(this);
        btn_ShoppingCart.setOnClickListener(this);

        bookStoreFragment = new BookStoreFragment();
        shoppingCartFragment = new ShoppingCartFragment();
        replaceFragment(bookStoreFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_BookStore:
                replaceFragment(bookStoreFragment);
                break;
            case R.id.btn_ShoppingCart:
                replaceFragment(shoppingCartFragment);
                break;
        }
    }

    //获取购物车信息
    private Bundle GerShoppingCart() {
        Bundle bundle = new Bundle();
        ArrayList<Book> books;
        books = BookStoreAdapter.ShoppingCart;
        bundle.putParcelableArrayList("ShoppingCart", books);
        return bundle;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_bookstore, fragment);
        fragmentTransaction.commit();
    }

}
