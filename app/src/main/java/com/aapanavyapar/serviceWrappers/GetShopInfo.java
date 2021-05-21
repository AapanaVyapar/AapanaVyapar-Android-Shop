package com.aapanavyapar.serviceWrappers;

import com.aapanavyapar.aapanavyaparShop.MainActivity;
import com.aapanavyapar.aapanavyaparShop.services.GetShopDetailsRequest;
import com.aapanavyapar.aapanavyaparShop.services.GetShopDetailsResponse;
import com.aapanavyapar.aapanavyaparShop.services.ViewProviderServiceGrpc;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class GetShopInfo {

    ManagedChannel mChannel;
    ViewProviderServiceGrpc.ViewProviderServiceBlockingStub blockingStub;

    GetShopDetailsResponse shopDetailsResponse;

    public GetShopInfo(){
        mChannel = ManagedChannelBuilder.forTarget(MainActivity.VIEW_SERVICE_ADDRESS).usePlaintext().build();
        blockingStub = ViewProviderServiceGrpc.newBlockingStub(mChannel);
    }

    public int GetShopDetails(String token) {
        GetShopDetailsRequest getShopDetailsRequest = GetShopDetailsRequest.newBuilder()
                .setApiKey(MainActivity.API_KEY)
                .setToken(token)
                .build();

        try {
            this.shopDetailsResponse = blockingStub.withDeadlineAfter(5, TimeUnit.MINUTES).getShopDetails(getShopDetailsRequest);

        } catch (StatusRuntimeException e) {
            mChannel.shutdown();
            if(e.getMessage().toLowerCase().contains("unavailable")){
                return 2;
            }
            return 0;
        }
        mChannel.shutdown();
        return 1;
    }

    public GetShopDetailsResponse getShopDetailsResponse() {
        return shopDetailsResponse;
    }
}
