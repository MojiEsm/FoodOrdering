package com.example.foodordering.models;

public class MonthDalesDataModel {
    String Month;
    int Sales;

    public MonthDalesDataModel(String month, int sales) {
        Month = month;
        Sales = sales;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public int getSales() {
        return Sales;
    }

    public void setSales(int sales) {
        Sales = sales;
    }
}
