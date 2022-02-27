package com.example.foodordering.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodordering.R;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.models.CategoryModel;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCategoryActivity extends AppCompatActivity {

    private DataBaseHelper db;
    private CategoryDao dao;
    private TextView txt_title, btn_Back, btn_Add;
    private EditText edt_Name;
    private LinearLayout btn_ChooseIMG;
    private CircleImageView circleImageView;
    private static final int PICKIMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        db = DataBaseHelper.getInstance(this);
        dao = db.categoryDao();

        findViews();
        designs();
        setListeners();
    }

    private void setListeners() {
        btn_Back.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        btn_ChooseIMG.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICKIMAGE);
        });
        btn_Add.setOnClickListener(v -> {
            if (!edt_Name.getText().toString().equals("")) {
                dao.insert(new CategoryModel(edt_Name.getText().toString()));
                Toast.makeText(this, "ثبت شد!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AddCategoryActivity.this, CategoryActivity.class));
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
            }
        }
    }

    private void designs() {
        txt_title.setText("اضافه کردن دسته");
    }

    private void findViews() {
        txt_title = findViewById(R.id.txt_toolbarBackTitle_Title);
        btn_Back = findViewById(R.id.btn_toolbarBackTitle_Back);
        btn_ChooseIMG = findViewById(R.id.btn_AddCategory_img);
        circleImageView = findViewById(R.id.img_AddCategory_img);
        btn_Add = findViewById(R.id.btn_AddCategory_Add);
        edt_Name = findViewById(R.id.edt_Category_Name);
    }
}