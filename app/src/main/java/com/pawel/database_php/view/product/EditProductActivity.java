package com.pawel.database_php.view.product;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pawel.database_php.data.DataBody;
import com.pawel.database_php.data.MyWebService;
import com.pawel.database_php.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProductActivity extends Activity {

    private EditText inputName;
    private EditText inputPrice;
    private EditText inputDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);


        inputName = (EditText) findViewById(R.id.product_name_edit);
        inputPrice = (EditText) findViewById(R.id.price_edit);
        inputDesc = (EditText) findViewById(R.id.description_edit);


        Button button = (Button) findViewById(R.id.edit_product);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder = new Retrofit.Builder().
                        baseUrl("http://10.0.2.2/android/").
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit = builder.build();
                MyWebService client = retrofit.create(MyWebService.class);
                Call<DataBody> call = client.getProductDetails(1);
                call.enqueue(new Callback<DataBody>() {
                    @Override
                    public void onResponse(Call<DataBody> call, Response<DataBody> response) {

                        DataBody dataBody = response.body();
                        inputName.setText(dataBody.getProduct().get(0).getName());
                        inputPrice.setText(dataBody.getProduct().get(0).getAuthor());
                        inputDesc.setText(dataBody.getProduct().get(0).getDescription());

                    }


                    @Override
                    public void onFailure(Call<DataBody> call, Throwable t) {

                        inputDesc.setText(t.getMessage().toString());

                    }
                });


            }
        });


    }
}
