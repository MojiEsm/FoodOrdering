package com.example.foodordering.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodordering.models.CustomerModel;

import java.util.List;

@Dao
public interface CustomersDao {

    @Query("select * from customerTable")
    List<CustomerModel> getAll();

    @Insert
    void insert(CustomerModel customerModel);

    @Delete
    void delete(CustomerModel customerModel);

    @Query("Update customerTable set phoneNumber = :sPhoneNumber , fullName = :sName  ,address = :sAddress where ID = :sID")
    void update(int sID, String sName, String sPhoneNumber, String sAddress);
}
