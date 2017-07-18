package com.pawel.database_php.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

import com.pawel.database_php.R
import com.pawel.database_php.data.DataBody

import java.util.ArrayList


class ProductAdapter( context: Context, private var products: List<DataBody.Product>) : ArrayAdapter<DataBody.Product>(context, R.layout.list_item, products), Filterable {
    internal var previousLength = 0
    private val originalProducts: List<DataBody.Product>


    init {
        originalProducts = products

    }

    override fun getCount(): Int {
        return products!!.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var row: View? = convertView
        val viewHolder: ViewHolder
        if (row == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(R.layout.list_item, parent, false)
            viewHolder = ViewHolder()
            viewHolder.title = row!!.findViewById(R.id.name_all_products) as TextView
            viewHolder.pid = row.findViewById(R.id.pid) as TextView
            row.tag = viewHolder
        } else {
            viewHolder = row.tag as ViewHolder
        }


        val item = products!![position]
        val message = item.name
        val author = item.author
        viewHolder.author=author
        viewHolder.title!!.text =author +" -"+message
        val id = item.pid!!
        viewHolder.pid!!.text = Integer.toString(id)


        return row
    }

    override fun getFilter(): Filter {
        return filter
    }

    internal var filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val filterResults = Filter.FilterResults()
            val tempproducts = ArrayList<DataBody.Product>()
            if (constraint != null && products != null) {
                if (previousLength > constraint.length) {
                    products = originalProducts
                    previousLength = constraint.length

                } else {
                    previousLength = constraint.length
                }

                for (p in products!!) {
                    if (p.name!!.toUpperCase().startsWith(constraint.toString().toUpperCase())) {

                        tempproducts.add(p)
                    }else{
                        if (p.author!!.toUpperCase().startsWith(constraint.toString().toUpperCase())) {

                            tempproducts.add(p)
                        }
                    }
                }

                filterResults.values = tempproducts
                filterResults.count = tempproducts.size
            }

            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            products = results.values as List<DataBody.Product>
            if (results.count > 0) {

                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }


    }

    private class ViewHolder {
        internal var title: TextView? = null
        internal var pid: TextView? = null
        internal var author: String?=null

    }
}
