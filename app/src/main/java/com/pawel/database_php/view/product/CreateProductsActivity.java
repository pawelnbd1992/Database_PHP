package com.pawel.database_php.view.product;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pawel.database_php.data.DataBody;
import com.pawel.database_php.data.MyWebService;
import com.pawel.database_php.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreateProductsActivity extends Activity {

    EditText inputName;
    EditText inputPrice;
    EditText inputDesc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_product);

        // Edit Text
        inputName = (EditText) findViewById(R.id.product_name);
        inputPrice = (EditText) findViewById(R.id.price);
        inputDesc = (EditText) findViewById(R.id.description);
        inputName.setText("Samsung");
        inputPrice.setText("1000");

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.create_new_product);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String name = inputName.getText().toString();
                String price = inputPrice.getText().toString();
                String description = inputDesc.getText().toString();


                //DataBody.Product product = new DataBody.Product(name, price, description,null,null);

                sendRequestBody(name, price, description);
                Toast.makeText(CreateProductsActivity.this, "OK", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sendRequestBody(String name, String price, String description) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addInterceptor(loggingInterceptor);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl("http://10.0.2.2/").
                addConverterFactory(GsonConverterFactory.create(gson)).
                client(okHttpClient.build());

        Retrofit retrofit = builder.build();
        MyWebService client = retrofit.create(MyWebService.class);
        Call<DataBody> call = client.insertUser(name, price, description);
        call.enqueue(new Callback<DataBody>() {
            @Override
            public void onResponse(Call<DataBody> call, Response<DataBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateProductsActivity.this, "OK", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<DataBody> call, Throwable t) {

                inputDesc.setText(t.toString());
            }
        });

    }

}




