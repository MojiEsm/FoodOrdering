package com.example.foodordering.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordering.R;
import com.example.foodordering.models.AddOrderModel;

import java.util.List;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.MyViewHolder> {

    private Context context;
    private List<AddOrderModel> listData;
    private Listeners listeners;

    public AdapterOrders(Context context, List<AddOrderModel> listData, Listeners listeners) {
        this.context = context;
        this.listData = listData;
        this.listeners = listeners;
    }

    public interface Listeners {

        void deleteItem(AddOrderModel addOrderModel, int pos);

        void root(AddOrderModel addOrderModel, int pos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_orders_rv, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AddOrderModel addOrderModel = listData.get(position);
        holder.txt_Name.setText(addOrderModel.customerName);
        holder.txt_Status.setText(addOrderModel.orderStatus);
        holder.txt_TotalPrice.setText(String.valueOf(addOrderModel.totalPrice));
        holder.txt_Desc.setText(addOrderModel.orderDesc);
        holder.txt_Date.setText(addOrderModel.orderDate);
        holder.txt_Time.setText(addOrderModel.orderTime);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView root;
        private TextView txt_Name, txt_TotalPrice, txt_Status, txt_Desc, txt_Date, txt_Time;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews();
            setListeners();
        }

        private void bindViews() {
            root = itemView.findViewById(R.id.cardView_itemOrdersRV);
            txt_Name = itemView.findViewById(R.id.txt_itemOrdersRV_name);
            txt_Status = itemView.findViewById(R.id.txt_itemOrdersRV_status);
            txt_TotalPrice = itemView.findViewById(R.id.txt_itemOrdersRV_totalPrice);
            txt_Desc = itemView.findViewById(R.id.txt_itemOrdersRV_desc);
            txt_Date = itemView.findViewById(R.id.txt_itemOrdersRV_date);
            txt_Time = itemView.findViewById(R.id.txt_itemOrdersRV_time);
            imageView = itemView.findViewById(R.id.btn_itemOrdersRV_Delete);
        }

        private void setListeners() {
            imageView.setOnClickListener(v -> {
                AddOrderModel addOrderModel = listData.get(getAdapterPosition());
                listeners.deleteItem(addOrderModel, getAdapterPosition());
            });
            root.setOnClickListener(v -> {
                AddOrderModel addOrderModel = listData.get(getAdapterPosition());
                listeners.root(addOrderModel, getAdapterPosition());
            });
        }
    }
}
