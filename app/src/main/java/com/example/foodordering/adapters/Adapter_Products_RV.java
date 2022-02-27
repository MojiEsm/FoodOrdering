package com.example.foodordering.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.models.ProductsModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Products_RV extends RecyclerView.Adapter<Adapter_Products_RV.MyViewHolder> {
    private Context context;
    private List<ProductsModel> mdata;
    private ProductsModel productsModel;

    public Adapter_Products_RV(Context context, List<ProductsModel> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_products_rv, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        productsModel = mdata.get(position);
        holder.txt_NameFood.setText(productsModel.name);
        holder.txt_Category.setText(productsModel.category);
        holder.txt_Price.setText(String.valueOf(productsModel.price));
        holder.img_food.setImageResource(R.drawable.splash3);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_NameFood, txt_Category, txt_Price;
        private CircleImageView img_food;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews();
            setListeners();
        }

        private void findViews() {
            txt_NameFood = itemView.findViewById(R.id.txt_itemFoodMenu_Name);
            txt_Category = itemView.findViewById(R.id.txt_itemFoodMenu_Category);
            img_food = itemView.findViewById(R.id.img_itemFoodMenu);
            txt_Price = itemView.findViewById(R.id.txt_itemFoodMenu_Price);
        }

        private void setListeners() {

        }
    }
}
