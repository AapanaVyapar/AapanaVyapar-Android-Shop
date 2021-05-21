package com.aapanavyapar.aapanavyaparShop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.aapanavyapar.aapanavyapar.services.GetProductsRequest;
import com.aapanavyapar.aapanavyapar.services.GetProductsResponse;
import com.aapanavyapar.aapanavyapar.services.ViewProviderServiceGrpc;
import com.aapanavyapar.adapter.ProductDataAdapter;
import com.aapanavyapar.dataModel.DataModel;
import com.aapanavyapar.serviceWrappers.UpdateToken;
import com.aapanavyapar.viewData.ProductData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class ProductFragment extends Fragment {

    private DataModel dataModel;

    ImageButton addProduct;
    RecyclerView productRecycleView;

    ManagedChannel mChannel;
    ViewProviderServiceGrpc.ViewProviderServiceBlockingStub blockingStub;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataModel = new ViewModelProvider(requireActivity()).get(DataModel.class);

        mChannel = ManagedChannelBuilder.forTarget(MainActivity.VIEW_SERVICE_ADDRESS).usePlaintext().build();

        blockingStub = ViewProviderServiceGrpc.newBlockingStub(mChannel);

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

        ArrayList<ProductData> productData = new ArrayList<>();
        ProductDataAdapter productDataAdapter = new ProductDataAdapter(productData, getContext());

        productRecycleView.setAdapter(productDataAdapter);

        try {
            GetProductsRequest request = GetProductsRequest.newBuilder()
                    .setApiKey(MainActivity.API_KEY)
                    .setToken(dataModel.getAuthToken())
                    .build();
            Iterator<GetProductsResponse> responseProducts = blockingStub.withDeadlineAfter(5, TimeUnit.MINUTES).getProducts(request);

            while (responseProducts.hasNext()) {
                GetProductsResponse product = responseProducts.next();
                Log.d("PRODUCT_FRAGMENT", product.getProductName());
                productData.add(new ProductData(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getShippingInfo(),
                        product.getStock(),
                        product.getPrice(),
                        product.getOffer(),
                        product.getImagesList().toArray(new String[0]),
                        product.getCategoryList().toString())
                );
                productDataAdapter.notifyData(productData);
            }


        }catch (StatusRuntimeException e) {
            e.printStackTrace();

            if (e.getMessage().equals("UNAUTHENTICATED: Request With Invalid Token")) {
                Toast.makeText(view.getContext(), "Update Refresh Token", Toast.LENGTH_SHORT).show();
                UpdateToken updateToken = new UpdateToken();
                if (updateToken.GetUpdatedToken(dataModel.getRefreshToken())) {
                    dataModel.setAuthToken(updateToken.getAuthToken());

                    try {
                        GetProductsRequest request = GetProductsRequest.newBuilder()
                                .setApiKey(MainActivity.API_KEY)
                                .setToken(dataModel.getAuthToken())
                                .build();
                        Iterator<GetProductsResponse> responseProducts = blockingStub.withDeadlineAfter(5, TimeUnit.MINUTES).getProducts(request);
                        while (responseProducts.hasNext()) {
                            GetProductsResponse product = responseProducts.next();
                            productData.add(new ProductData(
                                    product.getProductId(),
                                    product.getProductName(),
                                    product.getDescription(),
                                    product.getShippingInfo(),
                                    product.getStock(),
                                    product.getPrice(),
                                    product.getOffer(),
                                    product.getImagesList().toArray(new String[0]),
                                    product.getCategoryList().toString())
                            );
                            productDataAdapter.notifyData(productData);
                        }

                    }catch (StatusRuntimeException e1) {
                        Toast.makeText(view.getContext(), "Error Occurred Unable To Get Data", Toast.LENGTH_SHORT).show();
                        e1.printStackTrace();
                    }

                } else {
                    Toast.makeText(view.getContext(), "Unable To Update Token Please Try Again ..!!", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(view.getContext(), "Unable To Get Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mChannel.shutdown();
    }
}