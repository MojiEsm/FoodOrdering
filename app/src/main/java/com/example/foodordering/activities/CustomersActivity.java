package com.example.foodordering.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Typeface;
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
import com.example.foodordering.adapters.Adapter_Customer_RV;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.models.CustomerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

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
                        Toast.makeText(getApplicationContext(), "RIGHT", Toast.LENGTH_SHORT).show();
                        adapterCustomerRv.notifyDataSetChanged();
                        break;
                    case ItemTouchHelper.LEFT:
                        AlertDialog.Builder builder = new AlertDialog.Builder(CustomersActivity.this);
                        builder.setTitle("حذف");
                        builder.setMessage("آیا مایل به حذف مشتری هستید؟");
                        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CustomerModel customerModel = listData.get(position);
                                dataBaseHelper.customersDao().delete(customerModel);
                                listData.remove(position);
                                adapterCustomerRv.notifyItemRemoved(position);
                                adapterCustomerRv.notifyItemRangeRemoved(position, listData.size());
                                adapterCustomerRv.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "مشتری حذف شد.", Toast.LENGTH_SHORT).show();
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
                        adapterCustomerRv.notifyDataSetChanged();
                        break;
                }
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(CustomersActivity.this, R.color.textColor))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeLeftLabel("حذف")
                        .setSwipeLeftLabelColor(ContextCompat.getColor(CustomersActivity.this, R.color.textColor))
                        .setSwipeLeftLabelTextSize(TypedValue.COMPLEX_UNIT_SP, 16)
//                        .setSwipeLeftLabelTypeface(Typeface.defaultFromStyle(R.font.iransans))
//                        .addSwipeRightBackgroundColor(ContextCompat.getColor(CustomersActivity.this, R.color.textColor))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                        .setSwipeRightLabelTextSize(TypedValue.COMPLEX_UNIT_SP, 16)
//                        .setSwipeRightLabelTypeface(Typeface.defaultFromStyle(R.font.iransans))
                        .addSwipeRightLabel("ویرایش")
                        .setSwipeRightLabelColor(ContextCompat.getColor(CustomersActivity.this, R.color.textColor))
                        .setIconHorizontalMargin(TypedValue.COMPLEX_UNIT_DIP, 10)
                        .create()
                        .decorate();
            }
        }).attachToRecyclerView(recyclerView);
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