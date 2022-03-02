package com.example.foodordering.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "productsTable")
public class ProductsModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "img")
    public String img;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "price")
    public Integer price;

    public ProductsModel(int id, String name, String img, String category, Integer price) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.category = category;
        this.price = price;
    }

    @Ignore
    public ProductsModel(String name, String img, String category, Integer price) {
        this.name = name;
        this.img = img;
        this.category = category;
        this.price = price;
    }
    //    public int getPrice() {
//        return Price;
//    }
//
//    public void setPrice(int price) {
//        Price = price;
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public String getIMG() {
//        return IMG;
//    }
//
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public void setIMG(String IMG) {
//        this.IMG = IMG;
//    }
//
//    public int getID() {
//        return id;
//    }
//
//    public void setID(int id) {
//        this.id = id;
//    }
//
//    public String getCategory() {
//        return Category;
//    }
//
//    public void setCategory(String category) {
//        Category = category;
//    }
}
