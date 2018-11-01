package com.example.Tiga.Main;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Book extends DataSupport implements Parcelable {

    private int cover;

    private int id;

    private String author;

    private double price;

    private int pages;

    private String name;

    public Book() {
    }

    public Book(int cover, String name, String author, double price, int pages) {
        this.cover = cover;
        this.name = name;
        this.author = author;
        this.price = price;
        this.pages = pages;
    }

    public Book(Parcel in) {
        cover = in.readInt();
        id = in.readInt();
        author = in.readString();
        price = in.readDouble();
        pages = in.readInt();
        name = in.readString();
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",name:" + getName() + ",author:" + getAuthor() + ",pages:" + getPages() + ",price:" + getPrice();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cover);
        dest.writeInt(id);
        dest.writeString(author);
        dest.writeDouble(price);
        dest.writeInt(pages);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
