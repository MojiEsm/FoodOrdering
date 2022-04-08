package com.example.foodordering.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;

import java.util.List;

public class Adapter_ProductCategory_RV extends RecyclerView.Adapter<Adapter_ProductCategory_RV.MyViewHolder> {
    private List<String> listData;
    private Context context;

    private Listeners listeners;

    public Adapter_ProductCategory_RV(Context context, List<String> listData, Listeners listeners) {
        this.listData = listData;
        this.context = context;
        this.listeners = listeners;
    }

    public interface Listeners {
        void onClick(String categoryModelString, int pos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_product_category, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_nameCategory.setText(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_nameCategory;
        private CardView rootElement;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameCategory = itemView.findViewById(R.id.txt_itemProductCategory);
            rootElement = itemView.findViewById(R.id.cardView_itemProductCategory);

            setListeners();
        }

        private void setListeners() {
            rootElement.setOnClickListener(v -> {
//                CategoryModel categoryModel = listData.get(getAdapterPosition());
//                listeners.onClick(categoryModel, getAdapterPosition());
                String categoryModelString = listData.get(getAdapterPosition());
                listeners.onClick(categoryModelString,getAdapterPosition());
            });
        }
    }
}
