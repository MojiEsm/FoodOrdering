package com.example.foodordering.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_Customer_RV;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.models.CustomerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fab_Add;
    private Adapter_Customer_RV adapterCustomerRv;
    private List<CustomerModel> listData = new ArrayList<>();
    private DataBaseHelper dataBaseHelper;
    private TextView txt_Title, btn_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        db();
        findViews();
        designs();
        setListeners();
        rvAdapter();
    }

    private void db() {
        dataBaseHelper = DataBaseHelper.getInstance(this);
        listData = dataBaseHelper.customersDao().getAll();
    }

    private void rvAdapter() {
        adapterCustomerRv = new Adapter_Customer_RV(this, listData);
        recyclerView.setAdapter(adapterCustomerRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setListeners() {
        fab_Add.setOnClickListener(v -> {
            startActivity(new Intent(CustomersActivity.this, AddCustomerActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        btn_Back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void findViews() {
        recyclerView = findViewById(R.id.RV_Customers);
        fab_Add = findViewById(R.id.fab_Customers_Add);
        txt_Title = findViewById(R.id.txt_toolbarBackTitle_Title);
        btn_Back = findViewById(R.id.btn_toolbarBackTitle_Back);
    }

    private void designs() {
        txt_Title.setText("لیست مشتری");
    }
}