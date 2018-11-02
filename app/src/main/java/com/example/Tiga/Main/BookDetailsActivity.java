package com.example.Tiga.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Book book;
    private ImageView Cover;
    private TextView Name;
    private TextView Author;
    private TextView Prices;
    private TextView Pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Cover = findViewById(R.id.iv_BookCover);
        Name = findViewById(R.id.tv_BookName);
        Author = findViewById(R.id.tv_BookAuthor);
        Prices = findViewById(R.id.tv_BookPrices);
        Pages = findViewById(R.id.tv_BookPages);

        findViewById(R.id.btn_AddShoppingCart).setOnClickListener(this);

        GetBookForStore();
        FillData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AddShoppingCart:
                AddShoppingCart();
                Toast.makeText(this, "已成功添加到购物车中！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //将目标书籍添加到购物车中
    private void AddShoppingCart() {
        BookStoreActivity.BooksForShoppingCart.add(book);
    }

    //获取目标书籍数据
    private void GetBookForStore() {
        Intent intent = getIntent();
        book = intent.getExtras().getParcelable("Book");
    }

    //填充目标书籍数据
    private void FillData() {
        Cover.setImageResource(book.getCover());
        Name.setText(String.format(getResources().getString(R.string.bookName), book.getName()));
        Author.setText(String.format(getResources().getString(R.string.bookAuthor), book.getAuthor()));
        Prices.setText(String.format(getResources().getString(R.string.bookPrices), book.getPrice()));
        Pages.setText(String.format(getResources().getString(R.string.bookPages), book.getPages()));
    }
}
