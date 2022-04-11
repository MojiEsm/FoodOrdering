package com.example.foodordering.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_AddOrder_RV;
import com.example.foodordering.classes.Tools;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.AddOrderDao;
import com.example.foodordering.database.dao.OrderDetailDao;
import com.example.foodordering.models.AddOrderModel;
import com.example.foodordering.models.CustomerModel;
import com.example.foodordering.models.OrderDetailModel;
import com.example.foodordering.models.ProductsModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.date.PersianDateFixedLeapYear;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class AddOrder extends AppCompatActivity {

    private Tools tools = new Tools();

    private DataBaseHelper dataBaseHelper;
    private AddOrderDao addOrderDao;
    private OrderDetailDao orderDetailDao;

    private Adapter_AddOrder_RV adapterAddOrderRv;
    private ArrayList<ProductsModel> listData = new ArrayList<>();

    private CustomerModel customerModel;

    private TextView btn_back, txt_Title, txt_date, txt_time, txt_TotalPrice, btn_AddOrder;
    private TextView btn_ChooseCustomer, btn_ChooseProduct;

    private RecyclerView recyclerView;

    ProductsModel productsModel;

    private int hour, minute;

    private String year, month, day;

    private String code = String.valueOf(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        db();
        findViews();
        designs();
        setListeners();
        adapterRV();
    }

    private void db() {
        dataBaseHelper = DataBaseHelper.getInstance(this);
        addOrderDao = dataBaseHelper.addOrderDao();
        orderDetailDao = dataBaseHelper.orderDetailDao();
    }


    private void designs() {
        txt_Title.setText("سفارش گیری");
        getSupportActionBar().hide();

        // Date
        PersianCalendar persianCalendar = new PersianCalendar();
        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        txt_time.setText(formatterTime.format(calendar.getTime()));
        txt_date.setText(persianCalendar.getPersianShortDate());

    }


    private void findViews() {
        btn_back = findViewById(R.id.btn_toolbarBackTitle_Back);
        txt_Title = findViewById(R.id.txt_toolbarBackTitle_Title);
        txt_date = findViewById(R.id.txt_AddOrder_Date);
        txt_time = findViewById(R.id.txt_AddOrder_Time);
        txt_TotalPrice = findViewById(R.id.txt_Addorder_Price);
        btn_ChooseCustomer = findViewById(R.id.btn_AddOrder_ChooseCustomer);
        btn_ChooseProduct = findViewById(R.id.btn_AddOrder_ChooseProduct);
        btn_AddOrder = findViewById(R.id.btn_AddOrder_AddOrder);
        recyclerView = findViewById(R.id.rv_AddOrder);

    }

    private void setListeners() {
        btn_AddOrder.setOnClickListener(v -> {
            if (btn_ChooseCustomer.getText().toString().equals("مشتری") || listData.size() == 0) {
                if (btn_ChooseCustomer.getText().toString().equals("مشتری"))
                    Toast.makeText(this, "لطفا مشتری را انتخاب کنید.", Toast.LENGTH_SHORT).show();
                else if (listData.size() == 0)
                    Toast.makeText(this, "لطفا محصول را انتخاب کنید.", Toast.LENGTH_SHORT).show();
            } else {
                String customerName = btn_ChooseCustomer.getText().toString().trim();
                int totalPrice = Integer.valueOf(txt_TotalPrice.getText().toString());
                addOrderDao.insert(new AddOrderModel(customerName, code, customerModel.id, "1", totalPrice, "orderDesc", txt_date.getText().toString(), txt_time.getText().toString()));
                for (int i = 0; i < listData.size(); i++) {
                    orderDetailDao.insert(new OrderDetailModel(listData.get(i).name, listData.get(i).price, listData.get(i).category, listData.get(i).amount, code, listData.get(i).picture));
                }
            }
        });

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
            PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                    .setPositiveButtonString("باشه")
                    .setNegativeButton("بیخیال")
                    .setTodayButton("امروز")
                    .setTodayButtonVisible(true)
                    .setMinYear(PersianDatePickerDialog.THIS_YEAR)
                    .setInitDate(PersianDatePickerDialog.THIS_YEAR, PersianDatePickerDialog.THIS_MONTH, PersianDatePickerDialog.THIS_DAY)
                    .setActionTextColor(Color.GRAY)
//                    .setTypeFace(typeface)
                    .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                    .setShowInBottomSheet(true)
                    .setListener(new PersianPickerListener() {
                        @Override
                        public void onDateSelected(PersianPickerDate persianPickerDate) {
//                            Log.d("TAG", "onDateSelected: " + persianPickerDate.getTimestamp());//675930448000
//                            Log.d("TAG", "onDateSelected: " + persianPickerDate.getGregorianDate());//Mon Jun 03 10:57:28 GMT+04:30 1991
//                            Log.d("TAG", "onDateSelected: " + persianPickerDate.getPersianLongDate());// دوشنبه  13  خرداد  1370
//                            Log.d("TAG", "onDateSelected: " + persianPickerDate.getPersianMonthName());//خرداد
//                            Log.d("TAG", "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(persianPickerDate.getPersianYear()));//true
//                            Toast.makeText(getApplicationContext(), persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay(), Toast.LENGTH_SHORT).show();
                            txt_date.setText(persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay());
                        }

                        @Override
                        public void onDismissed() {
                            txt_date.setText(PersianDatePickerDialog.THIS_YEAR + "/" + PersianDatePickerDialog.THIS_MONTH + "/" + PersianDatePickerDialog.THIS_DAY);
                        }
                    });

            picker.show();
        });

        btn_back.setOnClickListener(v -> {
            tools.startActivity(this, getApplication(), MainActivity.class);
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
                customerModel = new Gson().fromJson(getCustomer, CustomerModel.class);
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
        tools.startActivity(this, getApplication(), MainActivity.class);
    }


}