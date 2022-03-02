package com.example.foodordering.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_Category_RV;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.models.CategoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CategoryActivity extends AppCompatActivity {
    private TextView txt_Title, btn_Back;
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

    private void db() {
        db = DataBaseHelper.getInstance(this);
        listData = db.categoryDao().getList();
    }

    private void designs() {
        txt_Title.setText("دسته بندی");
    }

    private void findViews() {
        txt_Title = findViewById(R.id.txt_toolbarBackTitle_Title);
        btn_Back = findViewById(R.id.btn_toolbarBackTitle_Back);
        fab_add = findViewById(R.id.fab_Category_Add);
        recyclerView = findViewById(R.id.RV_Category);
    }

    private void setListeners() {
        btn_Back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
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

}