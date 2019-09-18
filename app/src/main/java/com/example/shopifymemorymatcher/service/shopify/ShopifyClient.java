package com.example.shopifymemorymatcher.service.shopify;

import com.example.shopifymemorymatcher.service.model.Products;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShopifyClient {

    @GET("/admin/products.json")
    Call<Products> products(
            @Query("page") Integer page,
            @Query("access_token") String accessToken
    );
}
