package com.example.foodordering.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_FoodMenu_Spinner;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.database.dao.ProductDao;
import com.example.foodordering.models.ProductsModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProductActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private ProductDao productDao;
    private CategoryDao categoryDao;

    private TextInputEditText edt_FoodName, edt_Price;
    private LinearLayout btn_addIMG;
    private TextView txt_Title, btn_Back, btn_Add;
    private Adapter_FoodMenu_Spinner adapterFoodMenuSpinner;
    private String[] str;
    private Spinner spinnerCategory;
    private CircleImageView circleImageView;
    private static final int pickImage = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        db();
        findViews();
        designs();
        setListeners();
    }

    private void db() {
        dataBaseHelper = DataBaseHelper.getInstance(this);
        productDao = dataBaseHelper.productDao();
        categoryDao = dataBaseHelper.categoryDao();
        str = categoryDao.getName().toArray(new String[0]);
    }

    private void setListeners() {
        btn_Back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        btn_addIMG.setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, pickImage);
        });

        btn_Add.setOnClickListener(v -> {
            String valueSpin = spinnerCategory.getSelectedItem().toString();
            if (!edt_FoodName.getText().toString().equals("") && !edt_Price.getText().toString().equals("")) {
                productDao.insert(new ProductsModel(edt_FoodName.getText().toString().trim(), "IMG", valueSpin, Integer.valueOf(edt_Price.getText().toString().trim())));
                Toast.makeText(this, "ثبت شد!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AddProductActivity.this, ProductsActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            } else {
                Toast.makeText(this, "لطفا فیلد ها را پر کنید.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                circleImageView.setBackground(getDrawable(R.color.white));
                circleImageView.setImageBitmap(bitmap);
            }
        }
    }

    private void designs() {
        txt_Title.setText("اضافه کردن غذا");
        //Spinner
        adapterFoodMenuSpinner = new Adapter_FoodMenu_Spinner(this, R.layout.item_spinner_addfood);
        adapterFoodMenuSpinner.addAll(str);
        adapterFoodMenuSpinner.add("دسته را انتخاب کنید...");
        spinnerCategory.setAdapter(adapterFoodMenuSpinner);
        spinnerCategory.setSelection(adapterFoodMenuSpinner.getCount());

    }

    private void findViews() {
        txt_Title = findViewById(R.id.txt_toolbarBackTitle_Title);
        btn_Back = findViewById(R.id.btn_toolbarBackTitle_Back);
        spinnerCategory = findViewById(R.id.spinner_AddFoodMenu_Category);
        btn_addIMG = findViewById(R.id.btn_AddFoodMenu_img);
        circleImageView = findViewById(R.id.img_AddFoodMenu_img);
        edt_FoodName = findViewById(R.id.edt_AddProduct_FoodName);
        edt_Price = findViewById(R.id.edt_AddProduct_Price);
        btn_Add = findViewById(R.id.btn_AddProduct_Add);
    }
}