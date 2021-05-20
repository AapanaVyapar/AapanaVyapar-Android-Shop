package com.aapanavyapar.aapanavyapar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.aapanavyapar.aapanavyapar.services.AddProductRequest;
import com.aapanavyapar.aapanavyapar.services.AddProductResponse;
import com.aapanavyapar.aapanavyapar.services.Category;
import com.aapanavyapar.aapanavyapar.services.GetProductsRequest;
import com.aapanavyapar.aapanavyapar.services.GetProductsResponse;
import com.aapanavyapar.aapanavyapar.services.ViewProviderServiceGrpc;
import com.aapanavyapar.dataModel.DataModel;
import com.aapanavyapar.serviceWrappers.UpdateToken;
import com.aapanavyapar.viewData.ProductData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class AddProductFragment extends Fragment {

    private DataModel dataModel;

    ManagedChannel mChannel;
    ViewProviderServiceGrpc.ViewProviderServiceBlockingStub blockingStub;
    ViewProviderServiceGrpc.ViewProviderServiceStub asyncStub;

    EditText productNameEditText, descriptionEditText, shippingInfoEditText, stockEditText, priceEditText, offerEditText, productUrlEditText;
    Button createProduct;

    ArrayList<Category> categories;

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
        asyncStub = ViewProviderServiceGrpc.newStub(mChannel);

        categories = new ArrayList<>();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productNameEditText = view.findViewById(R.id.create_product_product_name_input);
        descriptionEditText = view.findViewById(R.id.create_product_product_description_input);
        shippingInfoEditText = view.findViewById(R.id.create_product_product_shipping_info_input);
        stockEditText = view.findViewById(R.id.create_product_product_stock_input);
        priceEditText = view.findViewById(R.id.create_product_product_price_input);
        offerEditText = view.findViewById(R.id.create_product_product_offer_input);
        productUrlEditText = view.findViewById(R.id.create_product_product_images_input);

        view.findViewById(R.id.create_product_SPORTS_AND_FITNESS).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_ELECTRIC).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_DEVOTIONAL).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_AGRICULTURAL).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_WOMENS_CLOTHING).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_WOMENS_ACCESSORIES).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_MENS_CLOTHING).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_MENS_ACCESSORIES).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_HOME_GADGETS).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_TOYS).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_ELECTRONIC).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_DECORATION).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_FOOD).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_STATIONERY).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_BAGS).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_HARDWARE).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_FURNITURE).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_PACKAGING_AND_PRINTING).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_BEAUTY_AND_PERSONAL_CARE).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_CHEMICALS).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_GARDEN).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_KITCHEN).setOnClickListener(this::onCheckboxClicked);
        view.findViewById(R.id.create_product_MACHINERY).setOnClickListener(this::onCheckboxClicked);

        createProduct = view.findViewById(R.id.create_product);
        createProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    AddProductRequest request = AddProductRequest.newBuilder()
                            .setApiKey(MainActivity.API_KEY)
                            .setToken(dataModel.getAuthToken())
                            .setTitle(productNameEditText.getText().toString())
                            .setDescription(descriptionEditText.getText().toString())
                            .setShippingInfo(shippingInfoEditText.getText().toString())
                            .setStock(Integer.parseInt(stockEditText.getText().toString()))
                            .setPrice(Float.parseFloat(priceEditText.getText().toString()))
                            .setOffer(Integer.parseInt(offerEditText.getText().toString()))
                            .addAllImages(Arrays.asList(productUrlEditText.getText().toString().trim().split(",")))
                            .addAllCategory(categories)
                            .build();
                    AddProductResponse response = blockingStub.withDeadlineAfter(5, TimeUnit.MINUTES).addProduct(request);
                    if(!response.getOk()){
                        Toast.makeText(view.getContext(), "Unable To Add Your Product", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(view.getContext(), "Success ..!!", Toast.LENGTH_SHORT).show();

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProductFragment.class, null).commit();

                }catch (StatusRuntimeException e) {
                    e.printStackTrace();

                    if (e.getMessage().equals("UNAUTHENTICATED: Request With Invalid Token")) {
                        Toast.makeText(view.getContext(), "Update Refresh Token", Toast.LENGTH_SHORT).show();
                        UpdateToken updateToken = new UpdateToken();
                        if (updateToken.GetUpdatedToken(dataModel.getRefreshToken())) {
                            dataModel.setAuthToken(updateToken.getAuthToken());

                            try {
                                AddProductRequest request = AddProductRequest.newBuilder()
                                        .setApiKey(MainActivity.API_KEY)
                                        .setToken(dataModel.getAuthToken())
                                        .setTitle(priceEditText.getText().toString())
                                        .build();
                                AddProductResponse response = blockingStub.withDeadlineAfter(5, TimeUnit.MINUTES).addProduct(request);
                                if(!response.getOk()){
                                    Toast.makeText(view.getContext(), "Unable To Add Your Product", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(view.getContext(), "Success ..!!", Toast.LENGTH_SHORT).show();

                                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProductFragment.class, null).commit();

                            }catch (StatusRuntimeException e1) {
                                Toast.makeText(view.getContext(), "Error Occurred Unable To Get Data", Toast.LENGTH_SHORT).show();
                                e1.printStackTrace();
                            }

                        } else {
                            Toast.makeText(view.getContext(), "Unable To Update Token Please Try Again ..!!", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(view.getContext(), "Unable To Add Product To Your Shop", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    public void onCheckboxClicked(@NotNull View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.create_product_SPORTS_AND_FITNESS:
                if (checked)
                    categories.add(Category.SPORTS_AND_FITNESS);
                break;
            case R.id.create_product_ELECTRIC:
                if (checked)
                    categories.add(Category.ELECTRIC);
                break;
            case R.id.create_product_DEVOTIONAL:
                if (checked)
                    categories.add(Category.DEVOTIONAL);
                break;
            case R.id.create_product_AGRICULTURAL:
                if (checked)
                    categories.add(Category.AGRICULTURAL);
                break;
            case R.id.create_product_WOMENS_CLOTHING:
                if (checked)
                    categories.add(Category.WOMENS_CLOTHING);
                break;
            case R.id.create_product_WOMENS_ACCESSORIES:
                if (checked)
                    categories.add(Category.WOMENS_ACCESSORIES);
                break;
            case R.id.create_product_MENS_CLOTHING:
                if (checked)
                    categories.add(Category.MENS_CLOTHING);
                break;
            case R.id.create_product_MENS_ACCESSORIES:
                if (checked)
                    categories.add(Category.MENS_ACCESSORIES);
                break;
            case R.id.create_product_HOME_GADGETS:
                if (checked)
                    categories.add(Category.HOME_GADGETS);
                break;
            case R.id.create_product_TOYS:
                if (checked)
                    categories.add(Category.TOYS);
                break;
            case R.id.create_product_ELECTRONIC:
                if (checked)
                    categories.add(Category.ELECTRONIC);
                break;
            case R.id.create_product_DECORATION:
                if (checked)
                    categories.add(Category.DECORATION);
                break;
            case R.id.create_product_FOOD:
                if (checked)
                    categories.add(Category.FOOD);
                break;
            case R.id.create_product_STATIONERY:
                if (checked)
                    categories.add(Category.STATIONERY);
                break;
            case R.id.create_product_BAGS:
                if (checked)
                    categories.add(Category.BAGS);
                break;
            case R.id.create_product_HARDWARE:
                if (checked)
                    categories.add(Category.HARDWARE);
                break;
            case R.id.create_product_FURNITURE:
                if (checked)
                    categories.add(Category.FURNITURE);
                break;
            case R.id.create_product_PACKAGING_AND_PRINTING:
                if (checked)
                    categories.add(Category.PACKAGING_AND_PRINTING);
                break;
            case R.id.create_product_BEAUTY_AND_PERSONAL_CARE:
                if (checked)
                    categories.add(Category.BEAUTY_AND_PERSONAL_CARE);
                break;
            case R.id.create_product_CHEMICALS:
                if (checked)
                    categories.add(Category.CHEMICALS);
                break;
            case R.id.create_product_GARDEN:
                if (checked)
                    categories.add(Category.GARDEN);
                break;
            case R.id.create_product_KITCHEN:
                if (checked)
                    categories.add(Category.KITCHEN);
                break;
            case R.id.create_product_MACHINERY:
                if (checked)
                    categories.add(Category.MACHINERY);
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mChannel.shutdown();
    }

}