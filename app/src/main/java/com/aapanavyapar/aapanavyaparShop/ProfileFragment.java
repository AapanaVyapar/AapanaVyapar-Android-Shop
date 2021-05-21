package com.aapanavyapar.aapanavyaparShop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aapanavyapar.aapanavyapar.services.GetShopDetailsRequest;
import com.aapanavyapar.aapanavyapar.services.GetShopDetailsResponse;
import com.aapanavyapar.aapanavyapar.services.ViewProviderServiceGrpc;
import com.aapanavyapar.dataModel.DataModel;
import com.aapanavyapar.serviceWrappers.UpdateToken;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;


public class ProfileFragment extends Fragment {

    private DataModel dataModel;

    ManagedChannel mChannel;
    ViewProviderServiceGrpc.ViewProviderServiceBlockingStub blockingStub;

    TextView categoryEditText;
    EditText shopNameEditText, primaryImageEditText, imageUrlsEditText, businessInfoEditText, locationEditText;
    EditText fullNameEditText, houseDetailsEditText, streetEditText, landmarkEditText, pinCodeEditText, cityEditText, stateEditText, countryEditText, businessPhoneEditText;
    Button update;

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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        shopNameEditText = view.findViewById(R.id.profile_shop_shop_name_input);
        primaryImageEditText = view.findViewById(R.id.profile_shop_primary_image_input);
        primaryImageEditText = view.findViewById(R.id.profile_shop_primary_image_input);
        imageUrlsEditText = view.findViewById(R.id.profile_shop_images_input);
        businessInfoEditText = view.findViewById(R.id.profile_shop_business_info_input);
        locationEditText = view.findViewById(R.id.profile_shop_location_input);
        fullNameEditText = view.findViewById(R.id.profile_shop_full_name_input);
        houseDetailsEditText = view.findViewById(R.id.profile_shop_street_house_details_input);
        streetEditText = view.findViewById(R.id.profile_shop_street_details_input);
        landmarkEditText = view.findViewById(R.id.profile_shop_landmark_input);
        pinCodeEditText = view.findViewById(R.id.profile_shop_pin_code_input);
        cityEditText = view.findViewById(R.id.profile_shop_city_input);
        stateEditText = view.findViewById(R.id.profile_shop_state_input);
        countryEditText = view.findViewById(R.id.profile_shop_country_input);
        businessPhoneEditText = view.findViewById(R.id.profile_shop_phoneNo_input);
        categoryEditText = view.findViewById(R.id.profile_shop_category);

        update = view.findViewById(R.id.updateData);

        try {
            GetShopDetailsRequest request = GetShopDetailsRequest.newBuilder()
                    .setApiKey(MainActivity.API_KEY)
                    .setToken(dataModel.getAuthToken())
                    .build();
            GetShopDetailsResponse shopDetailsResponse = blockingStub.withDeadlineAfter(5, TimeUnit.MINUTES).getShopDetails(request);

            shopNameEditText.setText(shopDetailsResponse.getShopName());
            primaryImageEditText.setText(shopDetailsResponse.getPrimaryImage());
            imageUrlsEditText.setText(shopDetailsResponse.getImagesList().toString());
            businessInfoEditText.setText(shopDetailsResponse.getBusinessInformation());
            locationEditText.setText(shopDetailsResponse.getLocation().getLatitude() + "," + shopDetailsResponse.getLocation().getLongitude());
            fullNameEditText.setText(shopDetailsResponse.getAddress().getFullName());
            houseDetailsEditText.setText(shopDetailsResponse.getAddress().getHouseDetails());
            streetEditText.setText(shopDetailsResponse.getAddress().getStreetDetails());
            landmarkEditText.setText(shopDetailsResponse.getAddress().getLandMark());
            pinCodeEditText.setText(shopDetailsResponse.getAddress().getPinCode());
            cityEditText.setText(shopDetailsResponse.getAddress().getCity());
            stateEditText.setText(shopDetailsResponse.getAddress().getState());
            countryEditText.setText(shopDetailsResponse.getAddress().getCountry());
            businessPhoneEditText.setText(shopDetailsResponse.getAddress().getPhoneNo());
            categoryEditText.setText(shopDetailsResponse.getCategoryList().toString());

        }catch (StatusRuntimeException e) {
            e.printStackTrace();

            if (e.getMessage().equals("UNAUTHENTICATED: Request With Invalid Token")) {
                Toast.makeText(view.getContext(), "Update Refresh Token", Toast.LENGTH_SHORT).show();
                UpdateToken updateToken = new UpdateToken();
                if (updateToken.GetUpdatedToken(dataModel.getRefreshToken())) {
                    dataModel.setAuthToken(updateToken.getAuthToken());

                    try {
                        GetShopDetailsRequest request = GetShopDetailsRequest.newBuilder()
                                .setApiKey(MainActivity.API_KEY)
                                .setToken(dataModel.getAuthToken())
                                .build();
                        GetShopDetailsResponse shopDetailsResponse = blockingStub.withDeadlineAfter(5, TimeUnit.MINUTES).getShopDetails(request);

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