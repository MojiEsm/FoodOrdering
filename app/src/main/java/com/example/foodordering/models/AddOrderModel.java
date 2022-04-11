package com.example.foodordering.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "addOrderTable")
public class AddOrderModel {
    @PrimaryKey(autoGenerate = true)
    public int addOrder_id;

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

    public AddOrderModel(int addOrder_id, String customerName, String uniqCode, int customerID, String orderStatus, int totalPrice, String orderDesc, String orderDate, String orderTime) {
        this.addOrder_id = addOrder_id;
        this.customerName = customerName;
        this.uniqCode = uniqCode;
        this.customerID = customerID;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderDesc = orderDesc;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
    }
    @Ignore
    public AddOrderModel(String customerName, String uniqCode, int customerID, String orderStatus, int totalPrice, String orderDesc, String orderDate, String orderTime) {
        this.customerName = customerName;
        this.uniqCode = uniqCode;
        this.customerID = customerID;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderDesc = orderDesc;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
    }
}
