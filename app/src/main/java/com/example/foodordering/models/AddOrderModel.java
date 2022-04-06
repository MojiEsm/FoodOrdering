package com.example.foodordering.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "addOrderTable")
public class AddOrderModel {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "customerName")
    public String customerName;

    @ColumnInfo(name = "uniqCode")
    public String uniqCode;

    @ColumnInfo(name = "customerID")
    public int customerID;

    @ColumnInfo(name = "orderStatus")
    public String orderStatus;

    @ColumnInfo(name = "totalPrice")
    public int totalPrice;

    @ColumnInfo(name = "orderDesc")
    public String orderDesc;

    @ColumnInfo(name = "orderDate")
    public String orderDate;

    @ColumnInfo(name = "orderTime")
    public String orderTime;
}
