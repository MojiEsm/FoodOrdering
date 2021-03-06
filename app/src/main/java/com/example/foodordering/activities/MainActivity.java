package com.example.foodordering.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.foodordering.R;
import com.example.foodordering.classes.Tools;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.database.dao.CustomersDao;
import com.example.foodordering.database.dao.ProductDao;
import com.example.foodordering.models.MonthSalesDataModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Tools tools = new Tools();

    private DataBaseHelper dataBaseHelper;
    private CategoryDao categoryDao;
    private CustomersDao customersDao;
    private ProductDao productDao;

    private BarChart barChart;
    private ArrayList<BarEntry> barEntryArrayList;
    private ArrayList<String> labelNames;
    private ArrayList<MonthSalesDataModel> monthSalesDataModels = new ArrayList<>();

    private CardView btn_Menu, btn_Customers, btn_Category;

    private TextView txt_CustomerNo, txt_ProductNo, txt_CategoryNo;
    private TextView btn_AddOrder, btn_Orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db();
        findViews();
        design();
        setListeners();
        fillMonthSale();
        barChartAdapter();
    }

    private void db() {
        dataBaseHelper = DataBaseHelper.getInstance(this);
        customersDao = dataBaseHelper.customersDao();
        categoryDao = dataBaseHelper.categoryDao();
        productDao = dataBaseHelper.productDao();
    }


    private void design() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.bgStatus));
        getSupportActionBar().hide();

        //State Selector
        txt_CategoryNo.setText(String.valueOf(categoryDao.getList().size()));
        txt_CustomerNo.setText(String.valueOf(customersDao.getAll().size()));
        txt_ProductNo.setText(String.valueOf(productDao.getList().size()));
    }

    private void findViews() {
        barChart = findViewById(R.id.barChart_MainActivity);
        btn_Menu = findViewById(R.id.btn_MainActivity_Menu);
        btn_Customers = findViewById(R.id.btn_MainActivity_Customers);
        btn_Category = findViewById(R.id.btn_MainActivity_Category);
        txt_CategoryNo = findViewById(R.id.txt_Selector_NoCategory);
        txt_CustomerNo = findViewById(R.id.txt_Selector_NoCustomers);
        txt_ProductNo = findViewById(R.id.txt_Selector_NoProducts);
        btn_AddOrder = findViewById(R.id.btn_ToolbarMain_AddOrder);
        btn_Orders = findViewById(R.id.btn_MainActivity_Order);
    }

    private void setListeners() {
        btn_Orders.setOnClickListener(v -> {
            tools.startActivity(this, getApplication(), OrdersActivity.class);
        });

        btn_AddOrder.setOnClickListener(v -> {
            tools.startActivity(this, getApplication(), AddOrder.class);
        });

        btn_Menu.setOnClickListener(v -> {
            tools.startActivity(this, getApplication(), ProductsActivity.class);
        });

        btn_Customers.setOnClickListener(v -> {
            tools.startActivity(this, getApplication(), CustomersActivity.class);
        });

        btn_Category.setOnClickListener(v -> {
            tools.startActivity(this, getApplication(), CategoryActivity.class);
        });

    }

    private void barChartAdapter() {
        barEntryArrayList = new ArrayList<>();
        labelNames = new ArrayList<>();
        barEntryArrayList.clear();
        labelNames.clear();
        for (int i = 0; i < monthSalesDataModels.size(); i++) {
            String month = monthSalesDataModels.get(i).getMonth();
            int sales = monthSalesDataModels.get(i).getSales();
            barEntryArrayList.add(new BarEntry(i, sales));
            labelNames.add(month);
        }
        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "???????? ????????????");
        barDataSet.setColor(getResources().getColor(R.color.Primary));
        Description description = new Description();
        description.setText("MONTH");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelNames.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();
    }

    private void fillMonthSale() {
        monthSalesDataModels.clear();
        monthSalesDataModels.add(new MonthSalesDataModel("??????????????", 252000));
        monthSalesDataModels.add(new MonthSalesDataModel("????????????????", 2234000));
        monthSalesDataModels.add(new MonthSalesDataModel("??????????", 572000));
        monthSalesDataModels.add(new MonthSalesDataModel("??????", 223000));
        monthSalesDataModels.add(new MonthSalesDataModel("??????????", 1234000));
        monthSalesDataModels.add(new MonthSalesDataModel("????????????", 7654000));
        monthSalesDataModels.add(new MonthSalesDataModel("??????", 1234000));
        monthSalesDataModels.add(new MonthSalesDataModel("????????", 4567000));
        monthSalesDataModels.add(new MonthSalesDataModel("??????", 6543000));
        monthSalesDataModels.add(new MonthSalesDataModel("????", 987000));
        monthSalesDataModels.add(new MonthSalesDataModel("????????", 2225000));
        monthSalesDataModels.add(new MonthSalesDataModel("??????????", 1238000));
    }

}