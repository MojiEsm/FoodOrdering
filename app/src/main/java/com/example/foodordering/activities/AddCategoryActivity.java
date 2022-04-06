package com.example.foodordering.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodordering.R;
import com.example.foodordering.classes.EnDeCode;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.models.CategoryModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCategoryActivity extends AppCompatActivity {

    private DataBaseHelper db;
    private CategoryDao dao;

    private CategoryModel categoryModel;
    private EnDeCode enDeCode = new EnDeCode();

    private TextView txt_title, btn_Back, btn_Add;
    private EditText edt_Name;
    private LinearLayout btn_ChooseIMG;
    private CircleImageView circleImageView;

    private static final int PICKIMAGE = 100;
    private String enCodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        dbVoid();
        findViews();
        designs();
        setListeners();
    }

    private void dbVoid() {
        db = DataBaseHelper.getInstance(this);
        dao = db.categoryDao();

        categoryModel = (CategoryModel) getIntent().getSerializableExtra("objectModel");
    }

    private void designs() {
        getSupportActionBar().hide();
        txt_title.setText("اضافه کردن دسته");

        if (categoryModel != null) {
            if (categoryModel.image != null) {
                circleImageView.setImageBitmap(enDeCode.decode(categoryModel.image));
            }
            edt_Name.setText(categoryModel.name);
            btn_Add.setText("ویرایش");
        } else {
            btn_Add.setText("ثبت");
        }
    }

    private void findViews() {
        txt_title = findViewById(R.id.txt_toolbarBackTitle_Title);
        btn_Back = findViewById(R.id.btn_toolbarBackTitle_Back);
        btn_ChooseIMG = findViewById(R.id.btn_AddCategory_img);
        circleImageView = findViewById(R.id.img_AddCategory_img);
        btn_Add = findViewById(R.id.btn_AddCategory_Add);
        edt_Name = findViewById(R.id.edt_Category_Name);
    }

    private void setListeners() {
        btn_Back.setOnClickListener(v -> {
            startActivity(new Intent(AddCategoryActivity.this, CategoryActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        btn_ChooseIMG.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICKIMAGE);
        });
        btn_Add.setOnClickListener(v -> {
            if (!edt_Name.getText().toString().equals("")) {
                if (categoryModel != null) {
                    int sID = categoryModel.id;
                    dao.update(sID, edt_Name.getText().toString(), enCodedImage);
                    Toast.makeText(this, "ویرایش شد!", Toast.LENGTH_SHORT).show();
                    finishVoid();
                } else {
                    dao.insert(new CategoryModel(edt_Name.getText().toString(), enCodedImage));
                    Toast.makeText(this, "ثبت شد!", Toast.LENGTH_SHORT).show();
                    finishVoid();
                }
            } else {
                Toast.makeText(this, "لطفا فیلد ها را پر کنید.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finishVoid() {
        startActivity(new Intent(AddCategoryActivity.this, CategoryActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICKIMAGE) {
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
                enCodedImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishVoid();
    }
}