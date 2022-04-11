package com.example.foodordering.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodordering.models.AddOrderModel;

import java.util.List;

@Dao
public interface AddOrderDao {
    @Query("select * from addOrderTable")
    List<AddOrderModel> getList();

    @Insert
    void insert(AddOrderModel addOrderModel);

    @Delete
    void delete(AddOrderModel addOrderModel);

}
