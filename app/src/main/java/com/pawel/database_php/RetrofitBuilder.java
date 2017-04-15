package com.pawel.database_php;


import retrofit2.Retrofit;

/**
 * Created by Paweł on 2017-04-15.
 */
public class RetrofitBuilder{
    private String baseUrl;

    public RetrofitBuilder() {
        this.baseUrl ="http://pawelnbd.ayz.pl/";
    }

    public Retrofit.Builder getBuilder() {
        return new Retrofit.Builder().baseUrl(baseUrl);
    }

    public String getBaseUrl() {
        return baseUrl;
    }






}
