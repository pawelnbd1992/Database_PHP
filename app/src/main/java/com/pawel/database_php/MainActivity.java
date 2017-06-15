package com.pawel.database_php;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button_all_products= (Button) findViewById(R.id.button_all_products);
        Button button_create_product= (Button) findViewById(R.id.button_create_new_product);
        Button button_your_songs = (Button) findViewById(R.id.button_your_songs);

        button_all_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CreateProductsActivity.class);
                startActivity(i);
            }
        });


        button_create_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(i);
            }
        });
        button_your_songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), YourSongsActivity.class);
                startActivity(i);
            }
        });
    }
}
