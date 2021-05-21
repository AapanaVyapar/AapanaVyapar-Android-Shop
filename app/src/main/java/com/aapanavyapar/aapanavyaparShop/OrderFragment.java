package com.aapanavyapar.aapanavyaparShop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aapanavyapar.aapanavyapar.services.GetOrdersRequest;
import com.aapanavyapar.aapanavyapar.services.GetOrdersResponse;
import com.aapanavyapar.aapanavyapar.services.ViewProviderServiceGrpc;
import com.aapanavyapar.adapter.OrderDataAdapter;
import com.aapanavyapar.dataModel.DataModel;
import com.aapanavyapar.serviceWrappers.UpdateToken;
import com.aapanavyapar.viewData.OrderData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;


public class OrderFragment extends Fragment {

    private DataModel dataModel;

    RecyclerView orderRecycleView;

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
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderRecycleView = view.findViewById(R.id.order_recycler_view);
        orderRecycleView.setHasFixedSize(true);
        orderRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<OrderData> orderData = new ArrayList<>();
        OrderDataAdapter orderDataAdapter = new OrderDataAdapter(orderData, getContext());

        orderRecycleView.setAdapter(orderDataAdapter);

        try {
            GetOrdersRequest request = GetOrdersRequest.newBuilder()
                    .setApiKey(MainActivity.API_KEY)
                    .setToken(dataModel.getAuthToken())
                    .build();
            Iterator<GetOrdersResponse> ordersResponseIterator = blockingStub.withDeadlineAfter(5, TimeUnit.MINUTES).getOrders(request);

            while (ordersResponseIterator.hasNext()) {
                GetOrdersResponse ordersResponse = ordersResponseIterator.next();
                Log.d("ORDER_FRAGMENT", ordersResponse.getProductName());
                orderData.add(new OrderData(
                     ordersResponse.getProductId(),
                     ordersResponse.getOrderId(),
                     ordersResponse.getStatus().name(),
                     ordersResponse.getDeliveryTimeStamp(),
                     ordersResponse.getOrderTimeStamp(),
                     ordersResponse.getPrice(),
                     ordersResponse.getQuantity(),
                     ordersResponse.getProductName(),
                     ordersResponse.getProductImage(),
                     ordersResponse.getAddress()
                ));
                orderDataAdapter.notifyData(orderData);
            }


        }catch (StatusRuntimeException e) {
            e.printStackTrace();

            if (e.getMessage().equals("UNAUTHENTICATED: Request With Invalid Token")) {
                Toast.makeText(view.getContext(), "Update Refresh Token", Toast.LENGTH_SHORT).show();
                UpdateToken updateToken = new UpdateToken();
                if (updateToken.GetUpdatedToken(dataModel.getRefreshToken())) {
                    dataModel.setAuthToken(updateToken.getAuthToken());

                    try {
                        GetOrdersRequest request = GetOrdersRequest.newBuilder()
                                .setApiKey(MainActivity.API_KEY)
                                .setToken(dataModel.getAuthToken())
                                .build();
                        Iterator<GetOrdersResponse> ordersResponseIterator = blockingStub.withDeadlineAfter(5, TimeUnit.MINUTES).getOrders(request);

                        while (ordersResponseIterator.hasNext()) {
                            GetOrdersResponse ordersResponse = ordersResponseIterator.next();
                            Log.d("ORDER_FRAGMENT", ordersResponse.getProductName());
                            orderData.add(new OrderData(
                                    ordersResponse.getProductId(),
                                    ordersResponse.getOrderId(),
                                    ordersResponse.getStatus().name(),
                                    ordersResponse.getDeliveryTimeStamp(),
                                    ordersResponse.getOrderTimeStamp(),
                                    ordersResponse.getPrice(),
                                    ordersResponse.getQuantity(),
                                    ordersResponse.getProductName(),
                                    ordersResponse.getProductImage(),
                                    ordersResponse.getAddress()
                            ));
                            orderDataAdapter.notifyData(orderData);
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