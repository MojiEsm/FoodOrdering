package com.example.foodordering.activities;

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
import com.example.foodordering.adapters.AdapterOrders;
import com.example.foodordering.classes.Tools;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.AddOrderDao;
import com.example.foodordering.models.AddOrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;

    private List<AddOrderModel> listData = new ArrayList<>();
    private AdapterOrders adapterOrders;

    private Tools tools = new Tools();

    private RecyclerView rv_AddOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        db();
        bindViews();
        designs();
        adapters();
        setListeners();
    }

    private void db() {
        dataBaseHelper = DataBaseHelper.getInstance(this);
    }

    private void designs() {
        getSupportActionBar().setTitle("سفارشات");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_white_24);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#37342f")));
    }

    private void bindViews() {
        rv_AddOrders = findViewById(R.id.RV_Orders);
    }

    private void adapters() {
        listData.addAll(dataBaseHelper.addOrderDao().getList());
        adapterOrders = new AdapterOrders(this, listData, new AdapterOrders.Listeners() {
            @Override
            public void deleteItem(AddOrderModel addOrderModel, int pos) {

            }

            @Override
            public void root(AddOrderModel addOrderModel, int pos) {

            }
        });

        rv_AddOrders.setAdapter(adapterOrders);
        rv_AddOrders.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void setListeners() {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_customer, menu);

        MenuItem item = menu.findItem(R.id.menu_SearchCustomer_Search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tools.startActivity(this, getApplication(), MainActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                tools.startActivity(this, getApplication(), MainActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
}