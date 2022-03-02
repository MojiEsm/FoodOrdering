package com.example.foodordering.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_Products_RV;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.ProductDao;
import com.example.foodordering.models.ProductsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private DataBaseHelper  dataBaseHelper;
    private ProductDao productDao;
    private TextView txt_Title, btn_Back;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<ProductsModel> listData = new ArrayList<>();
    private Adapter_Products_RV adapterFoodMenuRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        dataBaseHelper = DataBaseHelper.getInstance(this);
        listData = dataBaseHelper.productDao().getList();
        findViews();
        designs();
        setListeners();
        adapter();
    }


    private void adapter() {
        adapterFoodMenuRv = new Adapter_Products_RV(this, listData);
        recyclerView.setAdapter(adapterFoodMenuRv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
    }

    private void setListeners() {
        fab.setOnClickListener(v -> {
            startActivity(new Intent(ProductsActivity.this, AddProductActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        btn_Back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

    }

    private void designs() {
        txt_Title.setText("محصولات");

    }

    private void findViews() {
        recyclerView = findViewById(R.id.RV_FoodMenu);
        fab = findViewById(R.id.fab_Food_Add);
        txt_Title = findViewById(R.id.txt_toolbarBackTitle_Title);
        btn_Back = findViewById(R.id.btn_toolbarBackTitle_Back);
    }
}