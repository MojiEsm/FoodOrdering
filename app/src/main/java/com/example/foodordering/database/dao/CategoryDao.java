package com.example.foodordering.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodordering.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CategoryDao {

    @Query("Select * from categoryTable")
    List<CategoryModel> getList();

    @Query("Select name from categoryTable")
    List<String> getName();

    @Insert
    void insert(CategoryModel categoryModel);

    @Delete
    void delete(CategoryModel categoryModel);

    @Update
    void update(CategoryModel categoryModel);


}
