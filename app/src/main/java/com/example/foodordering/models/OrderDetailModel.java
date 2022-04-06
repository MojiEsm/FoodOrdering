package com.example.foodordering.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "orderDetailTable")
public class OrderDetailModel {

    @PrimaryKey(autoGenerate = true)
    public int orderDetail_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "amount")
    public int amount;

    @ColumnInfo(name = "code")
    public String code;

    @ColumnInfo(name = "picture")
    public String picture;

    public OrderDetailModel(int orderDetail_id, String name, int price, String category, int amount, String code, String picture) {
        this.orderDetail_id = orderDetail_id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.amount = amount;
        this.code = code;
        this.picture = picture;
    }

    @Ignore
    public OrderDetailModel(String name, int price, String category, int amount, String code, String picture) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.amount = amount;
        this.code = code;
        this.picture = picture;
    }
}
