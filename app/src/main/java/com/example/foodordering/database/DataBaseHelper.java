package com.example.foodordering.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodordering.database.dao.AddOrderDao;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.database.dao.CustomersDao;
import com.example.foodordering.database.dao.OrderDetailDao;
import com.example.foodordering.database.dao.ProductDao;
import com.example.foodordering.models.AddOrderModel;
import com.example.foodordering.models.CategoryModel;
import com.example.foodordering.models.CustomerModel;
import com.example.foodordering.models.OrderDetailModel;
import com.example.foodordering.models.ProductsModel;

@Database(entities = {CategoryModel.class, CustomerModel.class, ProductsModel.class, AddOrderModel.class, OrderDetailModel.class}, exportSchema = false, version = 1)

public abstract class DataBaseHelper extends RoomDatabase {
    private static final String DB_NAME = "db_name";
    private static DataBaseHelper instance;


    public static synchronized DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DataBaseHelper.class,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


    public abstract CategoryDao categoryDao();

    public abstract CustomersDao customersDao();

    public abstract ProductDao productDao();

    public abstract AddOrderDao addOrderDao();

    public abstract OrderDetailDao orderDetailDao();
}
