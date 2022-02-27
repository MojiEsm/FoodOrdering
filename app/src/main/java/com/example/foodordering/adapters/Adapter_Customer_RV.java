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
import com.example.foodordering.models.CustomerModel;

import java.util.List;

public class Adapter_Customer_RV extends RecyclerView.Adapter<Adapter_Customer_RV.MyVeiwHolder> {
    private Context context;
    private List<CustomerModel> listData;
    private DataBaseHelper dataBaseHelper;

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
//        CustomerModel customerModel = listData.get(position);
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

        public MyVeiwHolder(@NonNull View itemView) {
            super(itemView);
            txt_Name = itemView.findViewById(R.id.txt_itemCustomer_UsreName);
            txt_PhoneNumber = itemView.findViewById(R.id.txt_itemCustomer_PhoneNumber);
            txt_Address = itemView.findViewById(R.id.txt_itemCustomer_Address);
        }
    }
}
