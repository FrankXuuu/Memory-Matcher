package com.example.shopifymemorymatcher.service.shopify;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by frankxu on 2018-07-15.
 */

public class ShopifyServiceGenerator {

    private static final String BASE_URL = "https://shopicruit.myshopify.com/admin/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory( GsonConverterFactory.create() );
    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, new Interceptor[]{});
    }

    public static <S> S createService(Class<S> serviceClass, Interceptor[] interceptors) {
        if (!httpClient.interceptors().contains(logging)) {

            for (Interceptor interceptor : interceptors) {
                httpClient.addInterceptor(interceptor);
            }

            httpClient.addInterceptor(logging);

            builder = builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
}

