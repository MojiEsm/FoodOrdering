package com.example.foodordering.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class EnDeCode {
    public Bitmap decode(String imageS){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageByte = baos.toByteArray();
        String imageString = imageS;
        if (imageString!=null){
            imageByte = Base64.decode(imageString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
            return bitmap;
        }
        return null;
    }
}
