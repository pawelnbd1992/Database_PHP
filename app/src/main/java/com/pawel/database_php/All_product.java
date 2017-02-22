package com.pawel.database_php;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paweł on 2017-02-02.
 */
public class All_product extends ListActivity {


    ListView listView;
    EditText editText;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_product);
        editText = (EditText) findViewById(R.id.edit_text_of_all_products);
        webView = (WebView) findViewById(R.id.webwiew);



        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl("http://10.0.2.2/android/").
                addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        MyWebService client = retrofit.create(MyWebService.class);
        Call<DataBody> call = client.getAllProduct();
        call.enqueue(new Callback<DataBody>() {
            @Override
            public void onResponse(Call<DataBody> call, Response<DataBody> response) {

                if(response.isSuccessful()){
                    editText.setText("Odpowiedz jest OK!");
                    List<DataBody.Product> list_of_products =new ArrayList<>(response.body().getProduct());
                    if (list_of_products != null) {
                        listView.setAdapter(new ProductAdapter(All_product.this,list_of_products));





                    }


                }


            }

            @Override
            public void onFailure(Call<DataBody> call, Throwable t) {
                Toast.makeText(All_product.this,t.toString(),Toast.LENGTH_SHORT).show();

            }
        });

        listView =getListView();
       getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String url="http://localhost/android/pdf/wymarzona.pdf";
               Intent intent = new Intent(All_product.this,Texts.class);
               intent.putExtra("Pozycja",listView.getItemAtPosition(i).toString());
               startActivity(intent);


           }
       });

    }


}
