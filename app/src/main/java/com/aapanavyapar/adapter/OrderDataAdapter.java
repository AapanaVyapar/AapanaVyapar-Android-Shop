package com.aapanavyapar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aapanavyapar.aapanavyaparShop.R;
import com.aapanavyapar.viewData.OrderData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrderDataAdapter extends RecyclerView.Adapter<OrderDataAdapter.ViewHolder> {
    ArrayList<OrderData> orderDataList;
    Context context;

    public OrderDataAdapter(ArrayList<OrderData> orderData, Context activity) {
        this.orderDataList = orderData;
        this.context = activity;
    }

    @NonNull
    @Override
    public OrderDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_data_card,parent,false);
        OrderDataAdapter.ViewHolder viewHolder = new OrderDataAdapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderDataAdapter.ViewHolder holder, int position) {
        final OrderData orderData = orderDataList.get(position);

        Glide.with(this.context)
                .load(orderData.getOrderProductImage())
                .fitCenter()
                .into(holder.orderProductImage);

        holder.orderId.setText("Order Id : " + orderData.getOrderId());
        holder.orderStatus.setText("Order Status : " + orderData.getOrderStatus());
        holder.orderDeliveryTimeStamp.setText("Order Delivery Time : " + orderData.getOrderDeliveryTimeStamp());
        holder.orderOrderTimeStamp.setText("Order Ordered Time : " + orderData.getOrderOrderTimeStamp());
        holder.orderProductPrice.setText("Order Price : " + String.valueOf(orderData.getOrderProductPrice()));
        holder.orderProductQty.setText("Order Quantity : " + String.valueOf(orderData.getOrderProductQty()));
        holder.orderProductName.setText(orderData.getOrderProductName());

        holder.orderAddressFullName.setText("Full Name : " + orderData.getAddress().getFullName());
        holder.orderAddressHouseDetails.setText("House Details : " + orderData.getAddress().getHouseDetails());
        holder.orderAddressStreetDetails.setText("Street Details : " + orderData.getAddress().getStreetDetails());
        holder.orderAddressLandmark.setText("Landmark : " + orderData.getAddress().getLandMark());
        holder.orderAddressPinCode.setText("Pin Code : " + orderData.getAddress().getPinCode());
        holder.orderAddressCity.setText("City : " + orderData.getAddress().getCity());
        holder.orderAddressState.setText("State : " + orderData.getAddress().getState());
        holder.orderAddressCountry.setText("Country : " + orderData.getAddress().getCountry());
        holder.orderAddressPhoneNo.setText("Phone No : " + orderData.getAddress().getPhoneNo());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context , orderData.getOrderProductName(),Toast.LENGTH_LONG).show();
//                AppCompatActivity activity = (AppCompatActivity)v.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ProductOnCardClick.class,null).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }

    public void notifyData(ArrayList<OrderData> myList) {
        this.orderDataList = myList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderId;
        TextView orderStatus;
        TextView orderDeliveryTimeStamp;
        TextView orderOrderTimeStamp;
        TextView orderProductPrice;
        TextView orderProductQty;
        TextView orderProductName;
        ImageView orderProductImage;

        TextView orderAddressFullName;
        TextView orderAddressHouseDetails;
        TextView orderAddressStreetDetails;
        TextView orderAddressLandmark;
        TextView orderAddressPinCode;
        TextView orderAddressCity;
        TextView orderAddressState;
        TextView orderAddressCountry;
        TextView orderAddressPhoneNo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_LONG).show();
                }
            });

            orderId = itemView.findViewById(R.id.orderId);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderDeliveryTimeStamp = itemView.findViewById(R.id.orderDelivery);
            orderOrderTimeStamp = itemView.findViewById(R.id.orderOrder);
            orderProductPrice = itemView.findViewById(R.id.orderPrice);
            orderProductQty = itemView.findViewById(R.id.orderQuantity);
            orderProductName = itemView.findViewById(R.id.orderName);
            orderProductImage = itemView.findViewById(R.id.orderImage);

            orderAddressFullName = itemView.findViewById(R.id.orderAddressFullName);
            orderAddressHouseDetails = itemView.findViewById(R.id.orderAddressHouseDetails);
            orderAddressStreetDetails = itemView.findViewById(R.id.orderAddressStreetDetails);
            orderAddressLandmark = itemView.findViewById(R.id.orderAddressLandMark);
            orderAddressPinCode = itemView.findViewById(R.id.orderAddressPinCode);
            orderAddressCity = itemView.findViewById(R.id.orderAddressCity);
            orderAddressState = itemView.findViewById(R.id.orderAddressState);
            orderAddressCountry = itemView.findViewById(R.id.orderAddressCountry);
            orderAddressPhoneNo = itemView.findViewById(R.id.orderAddressPhoneNo);
        }
    }
}
