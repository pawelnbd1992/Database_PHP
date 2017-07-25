package com.pawel.database_php.data;

import com.pawel.database_php.data.DataBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MyWebService {

    @GET("get_product_details.php")
     Call<DataBody> getProductDetails(@Query("pid")Integer pid);


    @GET("get_all_product.php")
    Call<DataBody> getAllProduct();



   @FormUrlEncoded
    @POST("android/create_product.php?")
         Call<DataBody> insertUser(
            @Field("name")String name,
            @Field("price")String author,
            @Field("description")String description
   );

}
