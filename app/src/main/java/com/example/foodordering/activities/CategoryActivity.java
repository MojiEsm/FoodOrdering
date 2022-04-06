package com.example.foodordering.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_Category_RV;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.models.CategoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private FloatingActionButton fab_add;
    private RecyclerView recyclerView;
    private Adapter_Category_RV adapterCategoryRv;
    private DataBaseHelper db;
    private CategoryDao dao;
    private List<CategoryModel> listData = new ArrayList<>();
    private List<String> ltData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        db();
        findViews();
        designs();
        setListeners();
        adapter();
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
                adapterCategoryRv.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    private void db() {
        db = DataBaseHelper.getInstance(this);
        listData = db.categoryDao().getList();
    }

    private void designs() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_white_24);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#37342f")));
        getSupportActionBar().setTitle("دسته بندی");
    }

    private void findViews() {
        fab_add = findViewById(R.id.fab_Category_Add);
        recyclerView = findViewById(R.id.RV_Category);
    }

    private void setListeners() {
//        btn_Back.setOnClickListener(v -> {
//            startActivity(new Intent(CategoryActivity.this,MainActivity.class));
//            finish();
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//        });
        fab_add.setOnClickListener(v -> {
            startActivity(new Intent(CategoryActivity.this, AddCategoryActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void adapter() {
        adapterCategoryRv = new Adapter_Category_RV(this, listData);
        recyclerView.setAdapter(adapterCategoryRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}