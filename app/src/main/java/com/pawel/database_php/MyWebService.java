package com.pawel.database_php;

import android.provider.ContactsContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface MyWebService {

    @GET("get_product_details.php?pid=2")
    Call<DataBody> getData();


    @GET("get_all_product.php")
    Call<DataBody> getAllProduct();



   @FormUrlEncoded
    @POST("android/create_product.php?")
    public Call<DataBody> insertUser(
            @Field("name")String name,
            @Field("price")String price,
            @Field("description")String description
   );

}
