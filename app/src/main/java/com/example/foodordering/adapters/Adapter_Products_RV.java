package com.example.foodordering.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.activities.AddProductActivity;
import com.example.foodordering.classes.EnDeCode;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.ProductDao;
import com.example.foodordering.models.ProductsModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Products_RV extends RecyclerView.Adapter<Adapter_Products_RV.MyViewHolder> implements Filterable {

    private DataBaseHelper dataBaseHelper;
    private ProductDao productDao;
    Listener listener;

    private EnDeCode enDeCode = new EnDeCode();

    private Context context;
    private List<ProductsModel> mdata;
    private List<ProductsModel> listDataFilter;
    private ProductsModel productsModel;

    public Adapter_Products_RV(Context context, List<ProductsModel> mdata, Listener listener) {
        this.context = context;
        this.mdata = mdata;
        listDataFilter = new ArrayList<>(mdata);
        this.listener = listener;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filterValues;
    }

    private Filter filterValues = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ProductsModel> filteredList = new ArrayList<>();
            if (charSequence==null||charSequence.length()==0){
                filteredList.addAll(listDataFilter);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ProductsModel item : listDataFilter){
                    if (item.name.toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }else if (item.category.toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mdata.clear();
            mdata.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface Listener {
        void onClick(ProductsModel productsModel, int pos);
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
        String imageString = productsModel.picture;
        if (imageString == null) {
            holder.img_food.setImageResource(R.drawable.splash3);
        } else {
            holder.img_food.setBackground(context.getDrawable(R.color.white));
            holder.img_food.setImageBitmap(enDeCode.decode(imageString));
        }

        holder.txt_NameFood.setText(productsModel.name);
        holder.txt_Category.setText(productsModel.category);
        holder.txt_Price.setText(String.valueOf(productsModel.price));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_NameFood, txt_Category, txt_Price;
        private CircleImageView img_food;
        private CardView cardViewRoot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dataBaseHelper = DataBaseHelper.getInstance(context);
            productDao = dataBaseHelper.productDao();
            findViews();
            setListeners();
        }

        private void findViews() {
            txt_NameFood = itemView.findViewById(R.id.txt_itemFoodMenu_Name);
            txt_Category = itemView.findViewById(R.id.txt_itemFoodMenu_Category);
            img_food = itemView.findViewById(R.id.img_itemFoodMenu);
            txt_Price = itemView.findViewById(R.id.txt_itemFoodMenu_Price);
            cardViewRoot = itemView.findViewById(R.id.cardView_itemProduct_RV);
        }

        private void setListeners() {
            cardViewRoot.setOnClickListener(v -> {
                ProductsModel productsModel = mdata.get(getAdapterPosition());
                listener.onClick(productsModel, getAdapterPosition());
            });
        }
    }

    public void showDialog(int pos) {

        ProductsModel productsModel = mdata.get(pos);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_ud);

        TextView btn_Edit = dialog.findViewById(R.id.btn_Edit_BottomSheet);
        TextView btn_Delete = dialog.findViewById(R.id.btn_Delete_BottomSheet);

        btn_Edit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddProductActivity.class);
            intent.putExtra("objectProduct", productsModel);
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
                    productDao.delete(productsModel);
                    mdata.remove(productsModel);
                    notifyDataSetChanged();
                    Toast.makeText(context, "محصول حذف شد.", Toast.LENGTH_SHORT).show();
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
