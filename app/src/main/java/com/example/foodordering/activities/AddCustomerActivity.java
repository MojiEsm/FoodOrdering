package com.example.foodordering.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodordering.R;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CustomersDao;
import com.example.foodordering.models.CustomerModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddCustomerActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private CustomersDao customersDao;
    private List<CustomerModel> listData = new ArrayList<>();
    private TextView txt_Title, btn_Back, btn_Add;
    private TextInputEditText edt_FullName, edt_Address, edt_PhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        dataBaseHelper = DataBaseHelper.getInstance(this);
        customersDao = dataBaseHelper.customersDao();

        findViews();
        designs();
        setListeners();
    }

    private void setListeners() {
        btn_Back.setOnClickListener(v -> {
            startActivity(new Intent(AddCustomerActivity.this, CustomersActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        btn_Add.setOnClickListener(v -> {

            if (!edt_FullName.getText().toString().equals("") && !edt_PhoneNumber.getText().toString().equals("") && !edt_Address.getText().toString().equals("")) {
                customersDao.insert(new CustomerModel(edt_FullName.getText().toString(), edt_PhoneNumber.getText().toString(), edt_Address.getText().toString()));
                Toast.makeText(this, "ثبت شد!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AddCustomerActivity.this, CustomersActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            } else {
                Toast.makeText(this, "لطفا فیلد ها را پر کنید.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void findViews() {
        txt_Title = findViewById(R.id.txt_toolbarBackTitle_Title);
        btn_Back = findViewById(R.id.btn_toolbarBackTitle_Back);
        edt_FullName = findViewById(R.id.edt_Customer_FullName);
        edt_PhoneNumber = findViewById(R.id.edt_Customer_PhoneNumber);
        edt_Address = findViewById(R.id.edt_Customer_Address);
        btn_Add = findViewById(R.id.btn_AddCustomer_Add);
    }

    private void designs() {
        txt_Title.setText("اضافه کردن کاربر");
    }
}