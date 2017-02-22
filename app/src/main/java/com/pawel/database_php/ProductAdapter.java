package com.pawel.database_php;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Paweł on 2017-02-02.
 */
public class ProductAdapter extends ArrayAdapter<DataBody.Product> {

    private Context context;
    private List<DataBody.Product>products;


    public ProductAdapter(Context context, List<DataBody.Product> products) {
        super(context,R.layout.list_item, products);
        this.context=context;
        this.products=products;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView textView = (TextView) row.findViewById(R.id.name_all_products);

        DataBody.Product item = products.get(position);
        String message = item.getName();
        textView.setText(message);

        return row;
    }



}



