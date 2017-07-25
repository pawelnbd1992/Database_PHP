package com.pawel.database_php.view.adapters
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.pawel.database_php.R
import com.pawel.database_php.data.DataBody
import com.pawel.database_php.view.songlist.SongListFragment
import com.pawel.database_php.view.songtext.DisplayTextFragment
import java.util.*


class ProductAdapter(private var products: ArrayList<DataBody.Product>, recycler: RecyclerView, mylistener: SongListFragment.SongListFragmentListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {


    internal var previousLength = 0
    private val originalProducts: ArrayList<DataBody.Product>
    private var listOfSongs: ArrayList<DataBody.Product>? = null
    private var recyclerView: RecyclerView? = null
    private var listener: SongListFragment.SongListFragmentListener? = null

    init {
        listOfSongs = products
        recyclerView = recycler
        listener = mylistener


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        val item = products[position]
        val name = item.name
        val author = item.author
        val id = item.pid!!

        (holder as ViewHolder).author = author
        holder.title!!.text = (author + " -" + name)
        holder.pid!!.text = Integer.toString(id)

        var fragmentDisplayText: DisplayTextFragment = DisplayTextFragment()
        var bundle: Bundle = Bundle()
        bundle.putInt("PID", item.pid!!)
        fragmentDisplayText.arguments = bundle


    }


    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.list_item, parent, false)

        view.setOnClickListener {

            val pidOfSong = (view.findViewById(R.id.pid) as TextView).text.toString()
            val intent = Intent(parent.context, DisplayTextFragment.javaClass)
            intent.putExtra("PID_OF_SONG", pidOfSong)

        }

        return ViewHolder(view, listener!!)

    }


    init {
        originalProducts = products

    }

    /**
    override fun getCount(): Int {
    return products!!.size
    }**/
    /**

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
     **/
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
                    } else {
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
            products = results.values as ArrayList<DataBody.Product>
            if (results.count > 0) {

                notifyDataSetChanged()
            } else {

            }
        }


    }

    class ViewHolder(itemView: View?, mylistener: SongListFragment.SongListFragmentListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        var title: TextView? = null
        var pid: TextView? = null
        var author: String? = null
        var listener: SongListFragment.SongListFragmentListener = null!!


        init {
            listener = mylistener
            title = itemView!!.findViewById(R.id.name_all_products) as TextView
            pid = itemView.findViewById(R.id.pid) as TextView
            title!!.setOnClickListener(this)

        }

        override fun onClick(view: View?) {

            listener.onItemSelected(Integer.valueOf(pid!!.text.toString()))
        }

    }
}


