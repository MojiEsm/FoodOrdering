package com.example.foodordering.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

public class Adapter_FoodMenu_Spinner extends ArrayAdapter<String> {
    public Adapter_FoodMenu_Spinner(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}
