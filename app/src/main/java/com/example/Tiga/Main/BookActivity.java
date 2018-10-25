package com.example.Tiga.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

public class BookActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_Save;
    private Button btn_Query;
    private Button btn_Delete;
    private EditText et_BookName;
    private EditText et_BookAuthor;
    private EditText et_BookPages;
    private EditText et_BookPrices;
    private ListView lv_Book;

    private Book book;
    private List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        btn_Save = findViewById(R.id.btn_Save);
        btn_Query = findViewById(R.id.btn_Query);
        btn_Delete = findViewById(R.id.btn_Delete);
        lv_Book = findViewById(R.id.lv_book);
        et_BookName = findViewById(R.id.et_BookName);
        et_BookAuthor = findViewById(R.id.et_BookAuthor);
        et_BookPages = findViewById(R.id.et_BookPages);
        et_BookPrices = findViewById(R.id.et_BookPrice);

        btn_Save.setOnClickListener(this);
        btn_Query.setOnClickListener(this);
        btn_Delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Save:
                Save();
                break;
            case R.id.btn_Query:
                Query();
                break;
            case R.id.btn_Delete:
                ConfirmDelete();
                break;
        }
    }

    private void Save() {
        if (et_BookName.getText().toString().equals("") || et_BookAuthor.getText().toString().equals("") || et_BookPages.getText().toString().equals("")
                || et_BookPrices.getText().toString().equals("")) {
            Toast.makeText(this, "请完善信息!", Toast.LENGTH_SHORT).show();
        } else {
            String BookName = et_BookName.getText().toString();
            String BookAuthor = et_BookAuthor.getText().toString();
            int BookPages = Integer.parseInt(et_BookPages.getText().toString());
            double BookPrice = Double.parseDouble(et_BookPrices.getText().toString());
            book = new Book();
            book.setAuthor(BookAuthor);
            book.setName(BookName);
            book.setPages(BookPages);
            book.setPrice(BookPrice);
            book.save();
            et_BookName.getText().clear();
            et_BookAuthor.getText().clear();
            et_BookPages.getText().clear();
            et_BookPrices.getText().clear();
            Toast.makeText(this, "已成功保存一条数据!", Toast.LENGTH_SHORT).show();
        }
    }


    private void Query() {
        books = DataSupport.findAll(Book.class);
        int nums = books.size();
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, books);
        lv_Book.setAdapter(adapter);
        if (nums == 0) {
            Toast.makeText(this, "无数据!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "成功查询到" + nums + "条数据!", Toast.LENGTH_SHORT).show();
        }
    }

    private void Delete() {
        book = DataSupport.findLast(Book.class);
        if (book != null) {
            book.delete();
            Query();
            Toast.makeText(this, "已成功删除最后一条数据!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "无数据!", Toast.LENGTH_SHORT).show();
        }
    }

    private void ConfirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("通知:");
        builder.setMessage("你确定要删除数据吗?");
        builder.setCancelable(true);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Delete();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
