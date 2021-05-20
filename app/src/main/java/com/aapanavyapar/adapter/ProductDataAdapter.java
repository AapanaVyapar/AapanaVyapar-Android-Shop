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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aapanavyapar.aapanavyapar.ProductOnCardClick;
import com.aapanavyapar.aapanavyapar.R;
import com.aapanavyapar.viewData.ProductData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductDataAdapter extends RecyclerView.Adapter<ProductDataAdapter.ViewHolder> {

    ArrayList<ProductData> productDataList;
    Context context;

    public ProductDataAdapter(ArrayList<ProductData> productData, Context activity) {
        this.productDataList = productData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ProductDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_data_card,parent,false);
        ProductDataAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductData productCard = productDataList.get(position);

        Glide.with(this.context)
            .load(productCard.getImages()[0])
            .fitCenter()
            .into(holder.image);

        holder.productName.setText(productCard.getProductName());
        holder.description.setText(productCard.getDescription());
        holder.shippingInfo.setText(productCard.getShippingInfo());
        holder.stock.setText("Stock : " + String.valueOf(productCard.getStock()));
        holder.price.setText("Price : " + String.valueOf(productCard.getPrice()) + " Paise");
        holder.offer.setText("Offer : " + String.valueOf(productCard.getOffer()) + "%");
        holder.category.setText("Category : " + productCard.getCategory());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context , productCard.getProductName(),Toast.LENGTH_LONG).show();
//                AppCompatActivity activity = (AppCompatActivity)v.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ProductOnCardClick.class,null).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public void notifyData(ArrayList<ProductData> myList) {
        this.productDataList = myList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        TextView description;
        TextView shippingInfo;
        TextView stock;
        TextView price;
        ImageView image;
        TextView category;
        TextView offer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_LONG).show();
               }
           });

            productName = itemView.findViewById(R.id.productName);
            description = itemView.findViewById(R.id.description);
            shippingInfo = itemView.findViewById(R.id.shippingInfo);
            stock = itemView.findViewById(R.id.stock);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.productImage);
            category = itemView.findViewById(R.id.category);
            offer = itemView.findViewById(R.id.offer);
        }
    }

}
