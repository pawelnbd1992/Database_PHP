package com.pawel.database_php;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paweł on 2017-04-15.
 */
public class RetrofitBuilder{
    private String baseUrl;
    private Retrofit.Builder builder;
    private Retrofit retrofit;
     private MyWebService myWebService;

    public RetrofitBuilder() {
        this.baseUrl ="http://pawelnbd.ayz.pl/";
    }
    public MyWebService getMyWebService() {
        builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        myWebService =retrofit.create(MyWebService.class);
        return myWebService;
    }


    public String getBaseUrl() {
        return baseUrl;
    }






}
