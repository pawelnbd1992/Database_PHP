package com.pawel.database_php.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.pawel.database_php.R;
import com.pawel.database_php.data.DataBody;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends ArrayAdapter<DataBody.Product> implements Filterable {

    private Context context;
    private List<DataBody.Product>products;
    int previousLength =0;
    private List <DataBody.Product>originalProducts;


    public ProductAdapter(Context context, List<DataBody.Product> products)   {
        super(context, R.layout.list_item, products);
        this.context=context;
        this.products=products;
        originalProducts =products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        ViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title_of_song= (TextView) row.findViewById(R.id.name_all_products);
            viewHolder.pid = (TextView) row.findViewById(R.id.pid);
            row.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) row.getTag();
        }

        //TextView textView = (TextView) row.findViewById(R.id.name_all_products);

        DataBody.Product item = products.get(position);
        String message = item.getName();
        viewHolder.title_of_song.setText(message);
        int id = item.getPid();
        viewHolder.pid.setText(Integer.toString(id));
        //textView.setText(message);

        return row;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
          FilterResults filterResults = new FilterResults();
          ArrayList<DataBody.Product> tempproducts = new ArrayList<>();
          if(constraint!=null && products!=null ){
              if(previousLength>constraint.length()){
                 products= originalProducts;
                  previousLength = constraint.length();

              }else{
                  previousLength= constraint.length();
              }

              for(DataBody.Product p :products){
                  if(p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())){

                      tempproducts.add(p);
                  }
              }

              filterResults.values = tempproducts;
              filterResults.count = tempproducts.size();
          }

          return filterResults;
      }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                products = (List<DataBody.Product>) results.values;
            if(results.count>0){

                notifyDataSetChanged();}
            else{
               notifyDataSetInvalidated();
            }
        }


    };

private static class ViewHolder{
    TextView title_of_song;
    TextView pid;
}
}
