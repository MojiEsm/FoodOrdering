package com.example.foodordering.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_AddOrder_RV;
import com.example.foodordering.models.CustomerModel;
import com.example.foodordering.models.ProductsModel;
import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddOrder extends AppCompatActivity {
    private Adapter_AddOrder_RV adapterAddOrderRv;
    private ArrayList<ProductsModel> listData = new ArrayList<>();

    private TextView btn_back, txt_Title, txt_date, txt_time, txt_TotalPrice;
    private TextView btn_ChooseCustomer, btn_ChooseProduct;

    private RecyclerView recyclerView;

    ProductsModel productsModel;

    private int hour, minute;

    private String code = String.valueOf(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        findViews();
        designs();
        setListeners();
        adapterRV();
    }


    private void designs() {
        txt_Title.setText("سفارش گیری");
        getSupportActionBar().hide();

        // Date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
//        txt_date.setText(formatterDate.format(calendar.getTime()));
        txt_date.setText(getDateToday());
        txt_time.setText(formatterTime.format(calendar.getTime()));
    }


    private void findViews() {
        btn_back = findViewById(R.id.btn_toolbarBackTitle_Back);
        txt_Title = findViewById(R.id.txt_toolbarBackTitle_Title);
        txt_date = findViewById(R.id.txt_AddOrder_Date);
        txt_time = findViewById(R.id.txt_AddOrder_Time);
        txt_TotalPrice = findViewById(R.id.txt_Addorder_Price);
        btn_ChooseCustomer = findViewById(R.id.btn_AddOrder_ChooseCustomer);
        btn_ChooseProduct = findViewById(R.id.btn_AddOrder_ChooseProduct);
        recyclerView = findViewById(R.id.rv_AddOrder);

    }

    private void setListeners() {
        txt_time.setOnClickListener(v -> {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                    hour = selectHour;
                    minute = selectMinute;
                    txt_time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

                }
            };
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

            timePickerDialog.setTitle("انتخاب زمان");
            timePickerDialog.show();
        });

        txt_date.setOnClickListener(v -> {
            initDatePicker();
        });

        btn_back.setOnClickListener(v -> {
            startActivity(new Intent(AddOrder.this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        btn_ChooseCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(AddOrder.this, CustomersActivity.class);
            intent.putExtra("for_order", true);
            startActivityForResult(intent, 1);
        });

        btn_ChooseProduct.setOnClickListener(v -> {
            Intent intent = new Intent(AddOrder.this, ProductsActivity.class);
            intent.putExtra("for_order", true);
            startActivityForResult(intent, 2);
        });

    }

    private String getDateToday() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                txt_date.setText(date);

            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1) {
            return "JAN";
        }
        if (month == 2) {
            return "FEB";
        }
        if (month == 3) {
            return "MAR";
        }
        if (month == 4) {
            return "APR";
        }
        if (month == 5) {
            return "MAY";
        }
        if (month == 6) {
            return "JUN";
        }
        if (month == 7) {
            return "JUL";
        }
        if (month == 8) {
            return "AUG";
        }
        if (month == 9) {
            return "SEP";
        }
        if (month == 10) {
            return "OCT";
        }
        if (month == 11) {
            return "NOV";
        }
        if (month == 12) {
            return "DEC";
        }
        return "JAN";
    }


    private void adapterRV() {
        adapterAddOrderRv = new Adapter_AddOrder_RV(this, listData, new Adapter_AddOrder_RV.Listeners() {
            @Override
            public void btnMinus(ProductsModel productsModel, int pos) {
                if (listData.get(pos).amount >= 1) {
                    listData.get(pos).amount = listData.get(pos).amount - 1;
                    adapterAddOrderRv.notifyDataSetChanged();
                    initCounter();
                }
                if (productsModel.amount == 0) {
                    listData.remove(productsModel);
                    adapterAddOrderRv.notifyDataSetChanged();
                    initCounter();
                }
            }

            @Override
            public void btnPlus(ProductsModel productsModel, int pos) {
                listData.get(pos).amount = listData.get(pos).amount + 1;
                adapterAddOrderRv.notifyDataSetChanged();
                initCounter();
            }
        });
        recyclerView.setAdapter(adapterAddOrderRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String getCustomer = data.getExtras().getString("customer_gson");
                CustomerModel customerModel = new Gson().fromJson(getCustomer, CustomerModel.class);
                btn_ChooseCustomer.setText(customerModel.fullName);
                if (customerModel != null) {
                    btn_ChooseCustomer.setText(customerModel.fullName);
                } else {
                    btn_ChooseCustomer.setText("انتخاب مشتری");
                }
            }
            if (resultCode == RESULT_CANCELED) {
                btn_ChooseCustomer.setText("انتخاب مشتری");
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String getProduct = data.getExtras().getString("product_gson");
                productsModel = new Gson().fromJson(getProduct, ProductsModel.class);
                insertToOrderList(productsModel);
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private void insertToOrderList(ProductsModel product) {
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).product_id == product.product_id) {
                listData.get(i).amount = listData.get(i).amount + 1;
                Log.e("Mahdi", "insertToOrderList: " + listData.get(i).product_id + " == " + product.product_id);
                adapterAddOrderRv.notifyDataSetChanged();
                initCounter();
                return;
            }
        }
        listData.add(product);
        adapterAddOrderRv.notifyDataSetChanged();
        initCounter();
    }


    private void initCounter() {
//        if (listData.size() > 0) {
//            card_number.setVisibility(View.VISIBLE);
//            number_order.setText(listData.size() + "");
//        } else {
//            card_number.setVisibility(View.GONE);
//        }
//        total.setText(Tools.getForamtPrice(getTotalPrice() + ""));
        txt_TotalPrice.setText(getTotalPrice() + "");
    }


    private Integer getTotalPrice() {
        int p = 0;
        for (int i = 0; i < listData.size(); i++) {
            p = p + (listData.get(i).price) * listData.get(i).amount;
        }
        return p;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddOrder.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}