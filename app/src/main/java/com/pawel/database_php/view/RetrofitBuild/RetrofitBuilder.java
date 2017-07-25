package com.pawel.database_php.view.RetrofitBuild;


import com.pawel.database_php.data.MyWebService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private String baseUrl;
    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private MyWebService myWebService;


    public RetrofitBuilder() {
        this.baseUrl = "http://pawelnbd.ayz.pl/";
    }

    public MyWebService getMyWebService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        builder = new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        myWebService = retrofit.create(MyWebService.class);
        return myWebService;
    }


    public String getBaseUrl() {
        return baseUrl;
    }


}
