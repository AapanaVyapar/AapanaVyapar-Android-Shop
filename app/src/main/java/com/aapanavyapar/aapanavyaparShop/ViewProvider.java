package com.aapanavyapar.aapanavyaparShop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.aapanavyapar.dataModel.DataModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewProvider extends AppCompatActivity {

    public static final String TAG = "ViewProvider";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String token = intent.getStringExtra("Token");
        String authToken = intent.getStringExtra("AuthToken");
        int[] access =  intent.getIntArrayExtra("Access");

        DataModel dataModel = new ViewModelProvider(this).get(DataModel.class);
        dataModel.setRefreshToken(token);
        dataModel.setAuthToken(authToken);
        dataModel.setAccess(access);


        setContentView(R.layout.view_provider);
            BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
            bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.av_product:
                            selectedFragment = new ProductFragment();
                            break;
                        case R.id.av_orders:
                            selectedFragment = new OrderFragment();
                            break;
                        case R.id.av_shop:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            });
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductFragment()).commit();
        }
}

// Product, Shop, Orders