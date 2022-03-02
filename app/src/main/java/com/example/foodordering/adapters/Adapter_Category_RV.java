package com.example.foodordering.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.activities.AddCategoryActivity;
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

    private DataBaseHelper dataBaseHelper;
    private CategoryDao categoryDao;

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
        private RelativeLayout relativeLayoutRoot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dataBaseHelper = DataBaseHelper.getInstance(context);
            categoryDao = dataBaseHelper.categoryDao();
            findViews();
            setListeners();
        }

        private void findViews() {
            txt_title = itemView.findViewById(R.id.txt_itemCategory_Title);
            img_Category = itemView.findViewById(R.id.img_itemCategory);
            relativeLayoutRoot = itemView.findViewById(R.id.relative_itemCategory_RV);
        }

        private void setListeners() {
            relativeLayoutRoot.setOnClickListener(v -> {
                showDialog();
            });
        }

        private void showDialog() {
            CategoryModel categoryModel = listData.get(getAdapterPosition());

            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_ud);

            TextView btn_Edit = dialog.findViewById(R.id.btn_Edit_BottomSheet);
            TextView btn_Delete = dialog.findViewById(R.id.btn_Delete_BottomSheet);

            btn_Edit.setOnClickListener(v -> {
                Intent intent = new Intent(context, AddCategoryActivity.class);
                intent.putExtra("objectModel", categoryModel);
                context.startActivity(intent);
                ((Activity) context).finish();
            });

            btn_Delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("حذف");
                builder.setMessage("آیا مایل به حذف هستید؟");
                builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categoryDao.delete(categoryModel);
                        Toast.makeText(context, "حذف شد.", Toast.LENGTH_SHORT).show();
                        listData.remove(categoryModel);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            });

            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeBottomSheet;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }

    }
}
