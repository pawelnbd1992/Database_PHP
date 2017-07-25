package com.pawel.database_php.view.songlist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.ipaulpro.afilechooser.utils.FileUtils
import com.pawel.database_php.R
import com.pawel.database_php.data.DataBody
import com.pawel.database_php.data.MyWebService
import com.pawel.database_php.view.RetrofitBuild.RetrofitBuilder
import com.pawel.database_php.view.adapters.ProductAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SongListFragment : Fragment() {

   var listener:SongListFragmentListener = null!!


    interface SongListFragmentListener{

        fun onItemSelected(position:Int)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {


                val rootView = inflater!!.inflate(R.layout.fragment_your__songs, container, false)

                val recyclerView = rootView.findViewById(R.id.list_of_songs) as RecyclerView
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager= LinearLayoutManager(context)
                recyclerView.itemAnimator = DefaultItemAnimator()
                val client = RetrofitBuilder().myWebService
                val call = getDataBodyCall(client)
                val search_song = rootView.findViewById(R.id.search_song) as EditText
                getAllSongs(recyclerView, call, search_song)

                return rootView
            }





    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener=context as SongListFragmentListener

    }

    override fun onDetach() {
        super.onDetach()
        listener = null!!


    }

    private fun AddNewSong(add_song_button: Button) {
        add_song_button.setOnClickListener { show() }
    }

    private fun show() {
        val getContentIntent = FileUtils.createGetContentIntent()

        val intent = Intent.createChooser(getContentIntent, "Select a File")
        startActivityForResult(intent, REQUEST_CHOOSER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHOOSER -> if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    // Get the URI of the selected file
                    val uri = data.data
                    try {
                        // Get the file path from the URI
                        val path = FileUtils.getPath(activity, uri)

                    } catch (e: Exception) {

                    }

                }
            }
        }


        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getDataBodyCall(client: MyWebService): Call<DataBody> {
        return client.allProduct
    }

    private fun getAllSongs(recyclerView: RecyclerView, call: Call<DataBody>, editText: EditText){

        call.enqueue(object : Callback<DataBody> {
            override fun onResponse(call: Call<DataBody>, response: Response<DataBody>) {

                if (response.isSuccessful) {

                    val list_of_products = ArrayList(response.body().getProduct())

                    if (list_of_products != null) {
                        val productAdapter = ProductAdapter(list_of_products,recyclerView, listener)
                        recyclerView.adapter=productAdapter
                        recyclerView.adapter = productAdapter
                        SearchSong(editText, productAdapter)


                    }
                }
            }

            override fun onFailure(call: Call<DataBody>, t: Throwable) {

            }
        })

    }





    private fun getList(listView: ListView) {
        listView.setOnItemClickListener { adapterView, view, i, l ->

            val pidOfSong = (view.findViewById(R.id.pid) as TextView).text.toString()
           // val intent = Intent(context, TextsActivity::class.java)
            //intent.putExtra(PID_OF_SONG, pidOfSong)
            //startActivity(intent)
        }
    }



    private fun createURL(title_of_song: String): String {
        var title_of_song = title_of_song
        title_of_song = "http://pawelnbd.ayz.pl/pdf/$title_of_song.php"
        return title_of_song
    }


    private fun SearchSong(search_song: EditText, productAdapter: ProductAdapter) {
        search_song.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {

                productAdapter.getFilter().filter(s)

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    companion object {


        private val REQUEST_CHOOSER = 1234



}
}
