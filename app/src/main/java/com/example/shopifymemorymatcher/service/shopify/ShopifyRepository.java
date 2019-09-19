package com.example.shopifymemorymatcher.service.shopify;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.shopifymemorymatcher.service.model.Product;
import com.example.shopifymemorymatcher.service.model.ProductImage;
import com.example.shopifymemorymatcher.service.model.Products;
import com.example.shopifymemorymatcher.ui.adapter.card.CardState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class ShopifyRepository {

    private ShopifyClient shopifyClient;
    private static ShopifyRepository shopifyRepository;

    private ShopifyRepository() {
        shopifyClient = ShopifyServiceGenerator.createService(ShopifyClient.class);
    }

    public synchronized static ShopifyRepository getInstance() {
        if (shopifyRepository == null)
            shopifyRepository = new ShopifyRepository();
        return shopifyRepository;
    }

    public LiveData<List<ProductImage>> getProductImages(String accessToken, int uniques, int matches) {
        final MutableLiveData<List<ProductImage>> data = new MutableLiveData<>();

        Call<Products> productsCall = shopifyClient.products(1, accessToken);
        productsCall.enqueue(new Callback<Products>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Products responseBody = response.body();
                List<Product> products = responseBody.getProducts();
                List<ProductImage> half = new ArrayList<>();
                List<ProductImage> productImages = new ArrayList<>();
                int size = products.size() < uniques ? products.size() : uniques;
                for (int i = 0; i < size; i++) {
                    int pos = i > 10 ? i + 1 : i;
                    Product product = products.get(pos);
                    half.add(product.getImage());
                }

                for (int i = 0; i < matches; i++) {
                    productImages.addAll(half);
                }

                Collections.shuffle(productImages);

                data.setValue(productImages);
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
