package com.example.foodordering.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "customerTable")
public class CustomerModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "phoneNumber")
    public String phoneNumber;

    @ColumnInfo(name = "fullName")
    public String fullName;

    @ColumnInfo(name = "address")
    public String address;

    public CustomerModel(int id, String fullName, String phoneNumber, String address) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.address = address;
    }

    @Ignore
    public CustomerModel(String fullName, String phoneNumber, String address) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.address = address;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getPhoneNumber() {
//        return PhoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        PhoneNumber = phoneNumber;
//    }
//
//    public String getFullName() {
//        return FullName;
//    }
//
//    public void setFullName(String fullName) {
//        FullName = fullName;
//    }
//
//    public String getAddress() {
//        return Address;
//    }
//
//    public void setAddress(String address) {
//        Address = address;
//    }
}
