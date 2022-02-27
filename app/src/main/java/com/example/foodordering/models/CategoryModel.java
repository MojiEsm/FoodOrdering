package com.example.foodordering.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categoryTable")
public class CategoryModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;


    public CategoryModel(int id, String name) {
        this.id = id;
        this.name = name;
    }


    @Ignore
    public CategoryModel(String name) {
        this.name = name;
    }

}
