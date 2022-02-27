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

    private void adapter() {
        adapterCategoryRv = new Adapter_Category_RV(this, listData);
        recyclerView.setAdapter(adapterCategoryRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.RIGHT:
                        Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT).show();
                        adapterCategoryRv.notifyDataSetChanged();
                        break;
                    case ItemTouchHelper.LEFT:
                        AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                        builder.setTitle("حذف");
                        builder.setMessage("آیا مایل به حذف لیست هستید؟");
                        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CategoryModel categoryModel = listData.get(position);
                                db.categoryDao().delete(categoryModel);
                                listData.remove(position);
                                adapterCategoryRv.notifyItemRemoved(position);
                                adapterCategoryRv.notifyItemRangeRemoved(position, listData.size());
                                adapterCategoryRv.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "لیست حذف شد.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        adapterCategoryRv.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(CustomersActivity.this, R.color.textColor))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeLeftLabel("حذف")
                        .setSwipeLeftLabelColor(ContextCompat.getColor(CategoryActivity.this, R.color.textColor))
                        .setSwipeLeftLabelTextSize(TypedValue.COMPLEX_UNIT_SP, 16)
//                        .setSwipeLeftLabelTypeface(Typeface.defaultFromStyle(R.font.iransans))
//                        .addSwipeRightBackgroundColor(ContextCompat.getColor(CustomersActivity.this, R.color.textColor))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                        .setSwipeRightLabelTextSize(TypedValue.COMPLEX_UNIT_SP, 16)
//                        .setSwipeRightLabelTypeface(Typeface.defaultFromStyle(R.font.iransans))
                        .addSwipeRightLabel("ویرایش")
                        .setSwipeRightLabelColor(ContextCompat.getColor(CategoryActivity.this, R.color.textColor))
                        .setIconHorizontalMargin(TypedValue.COMPLEX_UNIT_DIP, 10)
                        .create()
                        .decorate();
            }
        }).attachToRecyclerView(recyclerView);
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

    private void designs() {
        txt_Title.setText("دسته بندی");
    }

    private void findViews() {
        txt_Title = findViewById(R.id.txt_toolbarBackTitle_Title);
        btn_Back = findViewById(R.id.btn_toolbarBackTitle_Back);
        fab_add = findViewById(R.id.fab_Category_Add);
        recyclerView = findViewById(R.id.RV_Category);
    }
}