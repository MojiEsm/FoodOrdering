package com.example.foodordering.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.classes.EnDeCode;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CategoryDao;
import com.example.foodordering.models.CategoryModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Adapter_Category_RV extends RecyclerView.Adapter<Adapter_Category_RV.MyViewHolder> {
    private Context context;
    private List<CategoryModel> listData;

    private EnDeCode enDeCode = new EnDeCode();

    public Adapter_Category_RV(Context context, List<CategoryModel> ltData) {
        this.context = context;
        this.listData = ltData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_category_rv, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String imageString = listData.get(position).image;
        if (imageString == null) {
            holder.img_Category.setImageResource(R.drawable.splash3);
        } else {
            holder.img_Category.setBackground(context.getDrawable(R.color.white));
            holder.img_Category.setImageBitmap(enDeCode.decode(imageString));
        }
        holder.txt_title.setText(listData.get(position).name);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_title;
        private ImageView img_Category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_itemCategory_Title);
            img_Category = itemView.findViewById(R.id.img_itemCategory);
        }
    }
}
