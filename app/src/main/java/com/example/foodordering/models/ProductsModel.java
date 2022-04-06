package com.example.foodordering.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "productsTable")
public class ProductsModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int product_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "price")
    public Integer price;

    @ColumnInfo(name = "picture")
    public String picture;

    @Ignore
    public int amount = 1;

    public ProductsModel(int product_id, String name, String picture, String category, Integer price) {
        this.product_id = product_id;
        this.name = name;
        this.picture = picture;
        this.category = category;
        this.price = price;
    }

    @Ignore
    public ProductsModel(String name, String picture, String category, Integer price) {
        this.name = name;
        this.picture = picture;
        this.category = category;
        this.price = price;
    }

}
