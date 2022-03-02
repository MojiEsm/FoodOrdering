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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.activities.AddCustomerActivity;
import com.example.foodordering.database.DataBaseHelper;
import com.example.foodordering.database.dao.CustomersDao;
import com.example.foodordering.models.CustomerModel;

import java.util.List;

public class Adapter_Customer_RV extends RecyclerView.Adapter<Adapter_Customer_RV.MyVeiwHolder> {
    private Context context;
    private List<CustomerModel> listData;

    private DataBaseHelper dataBaseHelper;
    private CustomersDao customersDao;

    public Adapter_Customer_RV(Context context, List<CustomerModel> listData) {
        this.context = context;
        this.listData = listData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_customer_rv, parent, false);
        return new MyVeiwHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVeiwHolder holder, int position) {
        dataBaseHelper = DataBaseHelper.getInstance(context);

        holder.txt_Name.setText(listData.get(position).fullName);
        holder.txt_PhoneNumber.setText(listData.get(position).phoneNumber);
        holder.txt_Address.setText(listData.get(position).address);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyVeiwHolder extends RecyclerView.ViewHolder {
        private TextView txt_Name, txt_PhoneNumber, txt_Address;
        private CardView cardViewRoot;

        public MyVeiwHolder(@NonNull View itemView) {
            super(itemView);
            dataBaseHelper = DataBaseHelper.getInstance(context);
            customersDao = dataBaseHelper.customersDao();

            findViews();
            setListeners();

        }

        private void findViews() {
            txt_Name = itemView.findViewById(R.id.txt_itemCustomer_UsreName);
            txt_PhoneNumber = itemView.findViewById(R.id.txt_itemCustomer_PhoneNumber);
            txt_Address = itemView.findViewById(R.id.txt_itemCustomer_Address);
            cardViewRoot = itemView.findViewById(R.id.cardView_itemCustomer_RV);
        }

        private void setListeners() {
            cardViewRoot.setOnClickListener(v -> {
                showDialog();
            });
        }

        private void showDialog() {
            CustomerModel customerModel = listData.get(getAdapterPosition());
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_ud);

            TextView btn_Edit = dialog.findViewById(R.id.btn_Edit_BottomSheet);
            TextView btn_Delete = dialog.findViewById(R.id.btn_Delete_BottomSheet);

            btn_Edit.setOnClickListener(v -> {
                Intent intent = new Intent(context, AddCustomerActivity.class);
                intent.putExtra("objectCustomer", customerModel);
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
                        customersDao.delete(customerModel);
                        listData.remove(customerModel);
                        notifyDataSetChanged();
                        Toast.makeText(context, "مشتری حذف شد.", Toast.LENGTH_SHORT).show();
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
