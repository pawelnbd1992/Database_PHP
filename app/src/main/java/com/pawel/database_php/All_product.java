package com.pawel.database_php;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class All_product extends ListActivity {

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_product);
        getAllSongs();
        listView = getListView();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(), "Pozycja " + Integer.toString(i), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getAllSongs() {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Retrofit.Builder builder = retrofitBuilder.getBuilder();
        Retrofit retrofit = builder.build();
        MyWebService client = retrofit.create(MyWebService.class);
        Call<DataBody> call = client.getAllProduct();
        Callback<DataBody> dataBodyCallback = getDataBodyCallback();
        call.enqueue(dataBodyCallback);
    }

    private Callback<DataBody> getDataBodyCallback() {

        return new Callback<DataBody>() {
            @Override
            public void onResponse(Call<DataBody> call, Response<DataBody> response) {
                if (response.isSuccessful()) {
                    List<DataBody.Product> list_of_products = new ArrayList<>(response.body().getProduct());
                    if (list_of_products != null) {
                        listView.setAdapter(new ProductAdapter(All_product.this, list_of_products));

                    }

                }
            }

            @Override
            public void onFailure(Call<DataBody> call, Throwable t) {
                Toast.makeText(All_product.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        };
    }




}
