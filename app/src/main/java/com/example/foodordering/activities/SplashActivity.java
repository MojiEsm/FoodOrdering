package com.example.foodordering.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodordering.R;
import com.example.foodordering.classes.ConnectionDetector;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

public class SplashActivity extends AppCompatActivity {
    private ConnectionDetector cd;
    private TextView btn_SignUp, btn_Login;
    private ProgressBar progressBar;
    private LottieAnimationView lottieAnimationView;
    private LinearLayout lnr_Buttons_Splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViews();
        setListener();
        designs();
        handlerDelayConnection();
    }

    private void designs() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //Full Screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Progress Bar
//        Sprite doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    private void findViews() {
        //Connection Detector
        cd = new ConnectionDetector(this);

        //Find Views
        btn_Login = findViewById(R.id.btn_Splash_Login);
        btn_SignUp = findViewById(R.id.btn_Splash_SignUp);
        lnr_Buttons_Splash = findViewById(R.id.lnr_button_Splash);
        lottieAnimationView = findViewById(R.id.animationView);
//        progressBar = findViewById(R.id.spin_kit);
    }

    private void setListener() {
        btn_Login.setOnClickListener(v -> {
            startActivity(new Intent(SplashActivity.this, Login_Activity.class));
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        });
        btn_SignUp.setOnClickListener(v -> {
            Toast.makeText(this, "SIGN UP", Toast.LENGTH_SHORT).show();
        });
    }


    private void chechConnection() {

        // Create AlertDialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(R.id.custom);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_check_connection, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();


        //Views AlertDialog
        TextView btn_Dialog_try = dialogView.findViewById(R.id.btn_dialog_try);


        //Listeners AlertDialog
        btn_Dialog_try.setOnClickListener(v -> {
            if (!cd.isConnected()) {
                chechConnection();
            } else {
                alertDialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottieAnimationView.setVisibility(View.GONE);
                        lnr_Buttons_Splash.setVisibility(View.VISIBLE);
                    }
                }, 2000);
            }
        });
        alertDialog.setOnCancelListener(v -> {
                SplashActivity.this.finish();

        });


        //Show AlertDialog

        if (!cd.isConnected()) {
            alertDialog.show();
        } else {
            lottieAnimationView.setVisibility(View.GONE);
            lnr_Buttons_Splash.setVisibility(View.VISIBLE);
        }

    }

    private void handlerDelayConnection() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chechConnection();
            }
        }, 2000);
    }


}