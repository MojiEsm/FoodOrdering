package com.example.foodordering.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "categoryTable")
public class CategoryModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "image")
    public String image;

    public CategoryModel(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    @Ignore

    public CategoryModel(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
