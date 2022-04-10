package com.example.foodordering.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.foodordering.R;

public class Tools {

    public void startActivity(Activity activity, Context act1, Class act2) {
        Intent intent = new Intent(act1, act2);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        act1.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
