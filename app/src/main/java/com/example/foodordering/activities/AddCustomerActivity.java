package com.example.foodordering.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodordering.R;
import com.example.foodordering.classes.Tools;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CustomersDao;
import com.example.foodordering.models.CustomerModel;
import com.google.android.material.textfield.TextInputEditText;

public class AddCustomerActivity extends AppCompatActivity {
    private Tools tools = new Tools();
    private DataBaseHelper dataBaseHelper;
    private CustomersDao customersDao;

    private CustomerModel customerModel;

    private TextView txt_Title, btn_Back, btn_Add;
    private TextInputEditText edt_FullName, edt_Address, edt_PhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        db();
        findViews();
        designs();
        setListeners();
    }

    private void db() {
        dataBaseHelper = DataBaseHelper.getInstance(this);
        customersDao = dataBaseHelper.customersDao();

        customerModel = (CustomerModel) getIntent().getSerializableExtra("objectCustomer");
    }

    private void designs() {
        getSupportActionBar().hide();
        txt_Title.setText("اضافه کردن کاربر");

        if (customerModel != null) {
            edt_FullName.setText(customerModel.fullName);
            edt_PhoneNumber.setText(customerModel.phoneNumber);
            edt_Address.setText(customerModel.address);
            btn_Add.setText("ویرایش");
        } else {
            btn_Add.setText("ثبت");
        }
    }

    private void findViews() {
        txt_Title = findViewById(R.id.txt_toolbarBackTitle_Title);
        btn_Back = findViewById(R.id.btn_toolbarBackTitle_Back);
        edt_FullName = findViewById(R.id.edt_Customer_FullName);
        edt_PhoneNumber = findViewById(R.id.edt_Customer_PhoneNumber);
        edt_Address = findViewById(R.id.edt_Customer_Address);
        btn_Add = findViewById(R.id.btn_AddCustomer_Add);
    }

    private void setListeners() {
        btn_Back.setOnClickListener(v -> {
            finishVoid();
        });
        btn_Add.setOnClickListener(v -> {
            String sFullName = edt_FullName.getText().toString().trim();
            String sNumberPhone = edt_PhoneNumber.getText().toString().trim();
            String sAddress = edt_Address.getText().toString().trim();
            if (!sFullName.equals("") && !sNumberPhone.equals("") && !sAddress.equals("")) {
                if (customerModel == null) {
                    customersDao.insert(new CustomerModel(sFullName, sNumberPhone, sAddress));
                    Toast.makeText(this, "ثبت شد!", Toast.LENGTH_SHORT).show();
                    finishVoid();

                } else {
                    int sID = customerModel.id;
                    customersDao.update(sID, sFullName, sNumberPhone, sAddress);
                    Toast.makeText(this, "ویرایش شد!", Toast.LENGTH_SHORT).show();
                    finishVoid();
                }
            } else {
                Toast.makeText(this, "لطفا فیلد ها را پر کنید.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finishVoid() {
        tools.startActivity(this, getApplication(), CustomersActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishVoid();
    }
}