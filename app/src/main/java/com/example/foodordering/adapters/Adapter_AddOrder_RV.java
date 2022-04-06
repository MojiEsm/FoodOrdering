package com.example.foodordering.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.classes.EnDeCode;
import com.example.foodordering.models.CustomerModel;
import com.example.foodordering.models.OrderDetailModel;
import com.example.foodordering.models.ProductsModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_AddOrder_RV extends RecyclerView.Adapter<Adapter_AddOrder_RV.MyViewHolder> {
    private Context context;
    private ArrayList<ProductsModel> mdata;
    private ProductsModel productsModel;
    Listeners listeners;

    private EnDeCode enDeCode = new EnDeCode();

    public Adapter_AddOrder_RV(Context context, ArrayList<ProductsModel> mdata , Listeners listeners) {
        this.context = context;
        this.mdata = mdata;
        this.listeners = listeners;
    }

    public interface Listeners{
        void btnMinus(ProductsModel productsModel ,int pos);
        void btnPlus(ProductsModel productsModel ,int pos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order_rv, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        productsModel = mdata.get(position);
        String imageString = productsModel.picture;
        if (imageString != null) {
            holder.circleImageView.setImageBitmap(enDeCode.decode(imageString));
            holder.circleImageView.setBackground(context.getDrawable(R.color.white));
        } else {
            holder.circleImageView.setImageResource(R.drawable.splash3);
        }
        holder.txt_Name.setText(productsModel.name);
        holder.txt_Price.setText(String.valueOf(productsModel.price));
        holder.txt_Category.setText(productsModel.category);
        holder.txt_Number.setText(String.valueOf(productsModel.amount));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_Name, txt_Category, txt_Price, txt_Number;
        private ImageButton btn_Minus, btn_Plus;
        private CircleImageView circleImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews();
            setListeners();
        }

        private void findViews() {
            txt_Name = itemView.findViewById(R.id.txt_itemOrder_Name);
            txt_Category = itemView.findViewById(R.id.txt_itemOrder_Category);
            txt_Price = itemView.findViewById(R.id.txt_itemOrder_Price);
            txt_Number = itemView.findViewById(R.id.txt_itemOrder_Number);
            btn_Plus = itemView.findViewById(R.id.btn_itemOrder_Plus);
            btn_Minus = itemView.findViewById(R.id.btn_itemOrder_Minus);
            circleImageView = itemView.findViewById(R.id.img_itemOrder);
        }

        private void setListeners() {

            btn_Minus.setOnClickListener(v -> {
                ProductsModel productsModel = mdata.get(getAdapterPosition());
                listeners.btnMinus(productsModel, getAdapterPosition());
            });
            btn_Plus.setOnClickListener(v -> {
                ProductsModel productsModel = mdata.get(getAdapterPosition());
                listeners.btnPlus(productsModel, getAdapterPosition());
            });
        }
    }
}
