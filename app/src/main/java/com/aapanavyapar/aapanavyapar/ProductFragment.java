package com.aapanavyapar.aapanavyapar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.aapanavyapar.adapter.OrderedProductAdapter;
import com.aapanavyapar.adapter.ProductDataAdapter;
import com.aapanavyapar.viewData.OrderedProductData;
import com.aapanavyapar.viewData.ProductData;

import io.grpc.StatusRuntimeException;

public class ProductFragment extends Fragment {

    ImageButton addProduct;
    RecyclerView productRecycleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addProduct = view.findViewById(R.id.add_product);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, AddProductFragment.class, null).addToBackStack(null).commit();
            }
        });

        productRecycleView = view.findViewById(R.id.product_recycler_view);
        productRecycleView.setHasFixedSize(true);
        productRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        ProductData[] productData = new ProductData[]{};


        try {



        }catch (StatusRuntimeException e) {



        }


        ProductDataAdapter productDataAdapter = new ProductDataAdapter(productData, getContext());
        productRecycleView.setAdapter(productDataAdapter);

    }
}