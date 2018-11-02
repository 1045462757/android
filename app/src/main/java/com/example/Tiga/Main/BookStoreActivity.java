package com.example.Tiga.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class BookStoreActivity extends BaseActivity implements View.OnClickListener {

    private BookStoreFragment bookStoreFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private Button btn_BookStore;
    private Button btn_ShoppingCart;

    private ArrayList<Book> BooksForStore = new ArrayList<>();  //书城数据
    public static ArrayList<Book> BooksForShoppingCart = new ArrayList<>();  //购物车数据

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

        initBookStore();
        TransferData(bookStoreFragment, PackageDataForBookStore());
        TransferData(shoppingCartFragment, PackageDataForShoppingCart());

        replaceFragment(bookStoreFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_BookStore:
                TransferData(bookStoreFragment, PackageDataForBookStore());
                replaceFragment(bookStoreFragment);
                break;
            case R.id.btn_ShoppingCart:
                TransferData(shoppingCartFragment, PackageDataForShoppingCart());
                replaceFragment(shoppingCartFragment);
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_bookstore, fragment);
        fragmentTransaction.commit();
    }

    //初始化数据
    private void initBookStore() {
        Book book1 = new Book(R.drawable.book1, "漫步太阳系", "王爽", 49.00, 199);
        Book book2 = new Book(R.drawable.book2, "侏罗纪的恐龙", "格利高利.圣.保罗", 113.40, 88);
        Book book3 = new Book(R.drawable.book3, "时间简史从芝诺悖论到引力波", "朱伟勇 朱海松", 69.40, 213);
        Book book4 = new Book(R.drawable.book4, "世界奇异现象档案录", "尔东", 21.3, 324);
        Book book5 = new Book(R.drawable.book5, "生活中不可不知的冷门知识", "霁阳", 21.6, 156);
        BooksForStore.add(book1);
        BooksForStore.add(book2);
        BooksForStore.add(book3);
        BooksForStore.add(book4);
        BooksForStore.add(book5);
    }

    //向Fragment传值
    private void TransferData(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
    }

    //打包书城数据
    private Bundle PackageDataForBookStore() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("BooksForStore", BooksForStore);
        return bundle;
    }

    //打包购物车数据
    private Bundle PackageDataForShoppingCart() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("BooksForShoppingCart", BooksForShoppingCart);
        return bundle;
    }

}
