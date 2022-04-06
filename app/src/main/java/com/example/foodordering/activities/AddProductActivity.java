package com.example.foodordering.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodordering.R;
import com.example.foodordering.adapters.Adapter_FoodMenu_Spinner;
import com.example.foodordering.classes.EnDeCode;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.database.dao.ProductDao;
import com.example.foodordering.models.ProductsModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProductActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private ProductsModel productsModel;

    private EnDeCode enDeCode = new EnDeCode();

    private TextInputEditText edt_FoodName, edt_Price;
    private LinearLayout btn_addIMG;
    private TextView txt_Title, btn_Back, btn_Add;
    private Adapter_FoodMenu_Spinner adapterFoodMenuSpinner;
    private String[] str;
    private Spinner spinnerCategory;
    private CircleImageView circleImageView;

    private static final int pickImage = 100;
    private String encodedImage;


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
//      Get  Intent
        productsModel = (ProductsModel) getIntent().getSerializableExtra("objectProduct");

    }

    private void designs() {
        getSupportActionBar().hide();
        txt_Title.setText("اضافه کردن محصول");
        //Spinner
        adapterFoodMenuSpinner = new Adapter_FoodMenu_Spinner(this, R.layout.item_spinner_addfood);
        adapterFoodMenuSpinner.addAll(str);
        if (productsModel != null) {
            //Get Intent
            if (productsModel.picture != null) {
                circleImageView.setImageBitmap(enDeCode.decode(productsModel.picture));
            }
            edt_FoodName.setText(productsModel.name);
            edt_Price.setText(String.valueOf(productsModel.price));
            adapterFoodMenuSpinner.add(productsModel.category);
            btn_Add.setText("ویرایش");
        } else {
            adapterFoodMenuSpinner.add("دسته را انتخاب کنید...");
            btn_Add.setText("ثبت");
        }
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

    private void setListeners() {
        btn_Back.setOnClickListener(v -> {
            finishVoid();
        });

        btn_addIMG.setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, pickImage);
        });

        btn_Add.setOnClickListener(v -> {
            String sName = edt_FoodName.getText().toString().trim();
            String valueSpin = spinnerCategory.getSelectedItem().toString();
            String iPrice = edt_Price.getText().toString().trim();
            if (!sName.equals("") && !iPrice.equals("")) {
                if (productsModel == null) {
                    productDao.insert(new ProductsModel(sName, encodedImage, valueSpin, Integer.valueOf(iPrice)));
                    Toast.makeText(this, "ثبت شد!", Toast.LENGTH_SHORT).show();
                    finishVoid();
                } else {
                    int sID = productsModel.product_id;
                    productDao.update(sID, sName, encodedImage, valueSpin, Integer.valueOf(iPrice));
                    Toast.makeText(this, "ویرایش شد.", Toast.LENGTH_SHORT).show();
                    finishVoid();
                }
            } else {
                Toast.makeText(this, "لطفا فیلد ها را پر کنید.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finishVoid() {
        startActivity(new Intent(AddProductActivity.this, ProductsActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                byte[] imageByte = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishVoid();
    }
}