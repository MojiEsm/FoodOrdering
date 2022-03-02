package com.example.foodordering.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodordering.models.ProductsModel;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("Select * From productsTable")
    List<ProductsModel> getList();

    @Insert
    void insert(ProductsModel productsModel);

    @Delete
    void delete(ProductsModel productsModel);

    @Query("Update productsTable set name =:sName , img =:sImage , category=:sCategory , price =:sPrice where id =:sID")
    void update(int sID, String sName, String sImage, String sCategory, Integer sPrice);
}
