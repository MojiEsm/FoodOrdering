package com.example.foodordering.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Category_RV extends RecyclerView.Adapter<Adapter_Category_RV.MyViewHolder> {
    private Context context;
    private List<CategoryModel> listData;
    private CategoryModel categoryModel;

    public Adapter_Category_RV(Context context, List<CategoryModel> ltData) {
        this.context = context;
        this.listData = ltData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_category_rv,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        categoryModel = mdata.get(position);
        holder.txt_title.setText(listData.get(position).name);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_itemCategory_Title);
        }
    }
}
