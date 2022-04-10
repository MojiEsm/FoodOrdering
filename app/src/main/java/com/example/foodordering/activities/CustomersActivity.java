package com.example.foodordering.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_Customer_RV;
import com.example.foodordering.classes.Tools;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.models.CustomerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends AppCompatActivity {
    private Tools tools = new Tools();
    private RecyclerView recyclerView;
    private FloatingActionButton fab_Add;
    private Adapter_Customer_RV adapterCustomerRv;
    private List<CustomerModel> listData = new ArrayList<>();
    private DataBaseHelper dataBaseHelper;
    private boolean for_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        for_order = getIntent().getBooleanExtra("for_order", false);

        db();
        findViews();
        designs();
        setListeners();
        rvAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_customer, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_SearchCustomer_Search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterCustomerRv.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    private void db() {
        dataBaseHelper = DataBaseHelper.getInstance(this);
        listData = dataBaseHelper.customersDao().getAll();
    }

    private void rvAdapter() {
        adapterCustomerRv = new Adapter_Customer_RV(this, listData, new Adapter_Customer_RV.Listener() {
            @Override
            public void onClick(CustomerModel customerModel, int pos) {
                if (for_order) {
                    Intent intent = new Intent();
                    intent.putExtra("customer_gson", new Gson().toJson(customerModel));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else adapterCustomerRv.showDialog(pos);
            }
        });

        recyclerView.setAdapter(adapterCustomerRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setListeners() {
        fab_Add.setOnClickListener(v -> {
            tools.startActivity(this, getApplication(), AddCustomerActivity.class);
        });

    }

    private void findViews() {
        recyclerView = findViewById(R.id.RV_Customers);
        fab_Add = findViewById(R.id.fab_Customers_Add);
    }

    private void designs() {
        getSupportActionBar().setTitle("لیست مشتری");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_white_24);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#37342f")));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!for_order) {
                    tools.startActivity(this, getApplication(), MainActivity.class);
                } else {
                    tools.startActivity(this, getApplication(), AddOrder.class);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tools.startActivity(this, getApplication(), MainActivity.class);
    }
}