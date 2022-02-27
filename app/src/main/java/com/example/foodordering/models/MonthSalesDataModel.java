package com.example.foodordering.models;

public class MonthSalesDataModel {
    String Month;
    int Sales;

    public MonthSalesDataModel(String month, int sales) {
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
