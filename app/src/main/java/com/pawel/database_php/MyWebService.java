package com.pawel.database_php;

import retrofit2.Call;
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
         Call<DataBody> insertUser(
            @Field("name")String name,
            @Field("price")String price,
            @Field("description")String description
   );

}
