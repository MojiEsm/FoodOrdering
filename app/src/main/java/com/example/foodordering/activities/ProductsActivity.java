package com.example.foodordering.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_Products_RV;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.ProductDao;
import com.example.foodordering.models.ProductsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private ProductDao productDao;
//    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<ProductsModel> listData = new ArrayList<>();
    private Adapter_Products_RV adapterProductsRv;
    private boolean for_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        for_order = getIntent().getBooleanExtra("for_order", false);

        db();
        findViews();
        designs();
        setListeners();
        adapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_customer,menu);

        MenuItem item = menu.findItem(R.id.menu_SearchCustomer_Search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterProductsRv.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    private void db() {
        dataBaseHelper = DataBaseHelper.getInstance(this);
        listData = dataBaseHelper.productDao().getList();
    }

    private void adapter() {
        adapterProductsRv = new Adapter_Products_RV(this, listData, new Adapter_Products_RV.Listener() {
            @Override
            public void onClick(ProductsModel productsModel, int pos) {
                if (for_order) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("product_gson", new Gson().toJson(productsModel));
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else adapterProductsRv.showDialog(pos);
            }
        });
        recyclerView.setAdapter(adapterProductsRv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
    }

    private void setListeners() {
        fab.setOnClickListener(v -> {
            startActivity(new Intent(ProductsActivity.this, AddProductActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
//        btn_Back.setOnClickListener(v -> {
//            startActivity(new Intent(ProductsActivity.this, MainActivity.class));
//            finish();
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//        });

    }

    private void designs() {
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_white_24);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#37342f")));
        getSupportActionBar().setTitle("محصولات");
    }

    private void findViews() {
//        toolbar = findViewById(R.id.toolbar_ToolbarBackTitle);
        recyclerView = findViewById(R.id.RV_FoodMenu);
        fab = findViewById(R.id.fab_Food_Add);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProductsActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}