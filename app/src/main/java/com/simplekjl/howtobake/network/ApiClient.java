/*
 * Develop by Jose L Crisostomo S. on 2/3/19 6:00 PM
 * Last modified 2/3/19 6:00 PM.
 * Copyright (c) 2019. All rights reserved.
 *
 *
 */

package com.simplekjl.howtobake.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit sRetrofit = null;
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    public ApiClient() {
        //empty constructor
    }

    public static Retrofit getInstance() {
        if (sRetrofit != null) {
            return sRetrofit;
        } else {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return sRetrofit;
        }
    }

    public static <T> T createService(Class<T> serviceClass) {
        return getInstance().create(serviceClass);
    }
}
