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
import com.example.foodordering.adapters.Adapter_Category_RV;
import com.example.foodordering.classes.Tools;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.models.CategoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private Tools tools = new Tools();

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

        fab_add.setOnClickListener(v -> {
            tools.startActivity(this, getApplication(), AddCategoryActivity.class);
        });
    }

    private void adapter() {
        adapterCategoryRv = new Adapter_Category_RV(this, listData);
        recyclerView.setAdapter(adapterCategoryRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                tools.startActivity(this, getApplication(), MainActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tools.startActivity(this, getApplication(), MainActivity.class);
    }
}