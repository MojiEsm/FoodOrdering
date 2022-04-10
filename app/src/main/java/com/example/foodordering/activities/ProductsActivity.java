package com.example.foodordering.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_ProductCategory_RV;
import com.example.foodordering.adapters.Adapter_Products_RV;
import com.example.foodordering.classes.Tools;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.models.ProductsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private Tools tools = new Tools();
    private DataBaseHelper dataBaseHelper;

    private RecyclerView rv_Products, rv_Category;
    private FloatingActionButton fab;

    private List<ProductsModel> listDataProduct = new ArrayList<>();
    private List<String> listDataCategoryArray = new ArrayList<>();

    private Adapter_Products_RV adapterProductsRv;
    private Adapter_ProductCategory_RV adapterProductCategoryRv;

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

    private void db() {
        dataBaseHelper = DataBaseHelper.getInstance(this);
        listDataProduct = dataBaseHelper.productDao().getList();
        listDataCategoryArray = dataBaseHelper.categoryDao().getName();
        listDataCategoryArray.add(0, "همه");
    }

    private void designs() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_white_24);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#37342f")));
        getSupportActionBar().setTitle("محصولات");
    }

    private void findViews() {
        rv_Products = findViewById(R.id.RV_FoodMenu);
        rv_Category = findViewById(R.id.RV_Product_Category);
        fab = findViewById(R.id.fab_Food_Add);
    }

    private void setListeners() {
        fab.setOnClickListener(v -> {
            tools.startActivity(this, getApplication(), AddProductActivity.class);
        });
    }

    private void adapter() {
        // Product Adapter
        adapterProductsRv = new Adapter_Products_RV(this, listDataProduct, new Adapter_Products_RV.Listener() {
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
        rv_Products.setAdapter(adapterProductsRv);
        rv_Products.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));


        // Category Adapter
        adapterProductCategoryRv = new Adapter_ProductCategory_RV(this, listDataCategoryArray, new Adapter_ProductCategory_RV.Listeners() {
            @Override
            public void onClick(String category, int pos) {
                for (int i = 0; i < listDataCategoryArray.size(); i++) {
                    if (category == listDataCategoryArray.get(0)) {
                        Log.e("TAG", "onClick: " + category);
                        listDataProduct.clear();
                        listDataProduct.addAll(dataBaseHelper.productDao().getList());
                        adapterProductsRv.notifyDataSetChanged();
                    } else if (category == listDataCategoryArray.get(i)) {
                        adapterProductsRv.getFilter().filter(category);
                    }
                }
            }
        });

        rv_Category.setAdapter(adapterProductCategoryRv);
        rv_Category.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
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
                adapterProductsRv.getFilter().filter(s);
                return false;
            }
        });
        return true;
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