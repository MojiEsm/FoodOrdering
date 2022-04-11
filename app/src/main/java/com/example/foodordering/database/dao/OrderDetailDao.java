package com.example.foodordering.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodordering.models.OrderDetailModel;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Query("select * from orderDetailTable")
    List<OrderDetailModel> getList();

    @Insert
    void insert(OrderDetailModel orderDetailModel);

    @Delete
    void delete(OrderDetailModel orderDetailModel);


}
