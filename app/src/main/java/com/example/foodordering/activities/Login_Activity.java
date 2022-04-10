package com.example.foodordering.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.foodordering.R;

public class Login_Activity extends AppCompatActivity {
    private TextView btn_Back , btn_Login , btn_SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        designs();
        findViews();
        setListeners();
    }

    private void designs() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //FullScreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    private void findViews() {
        btn_Back = findViewById(R.id.btn_Toolbar_Back);
        btn_Login = findViewById(R.id.btn_Login_Login);
        btn_SignUp = findViewById(R.id.btn_Login_SignUp);
    }

    private void setListeners() {
//        btn_Back.setOnClickListener(v->{
//            startActivity(new Intent(Login_Activity.this,SplashActivity.class));
//            finish();
//            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//        });

        btn_Login.setOnClickListener(v->{
            startActivity(new Intent(Login_Activity.this, MainActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        });
    }
}