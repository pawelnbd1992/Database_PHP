package com.pawel.database_php.view.songtext

import android.app.Fragment
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pawel.database_php.R
import com.pawel.database_php.data.DataBody
import com.pawel.database_php.data.MyWebService
import com.pawel.database_php.view.RetrofitBuilder
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DisplayTextFragment : Fragment() {

    var pid_of_song: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pid_of_song = arguments.getInt("PID")

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val rootView = inflater!!.inflate(R.layout.display_text_fragment, container, false)
        // val extras = intent.extras

        //if (extras != null) {
        //    pid_of_song = extras.getString(PID_OF_SONG)
        // }
        val client = RetrofitBuilder().myWebService
        val call = getTextOfSong(client,pid_of_song)
        getText(call)

        return rootView
    }

    private fun getText(call: Call<DataBody>) {
        call.enqueue(object : Callback<DataBody> {
            override fun onResponse(call: Call<DataBody>, response: Response<DataBody>) {

                if (response.isSuccessful) {

                    val list_of_products = ArrayList(response.body().getProduct())

                    if (list_of_products != null) {

                        author_song_et?.setText(list_of_products.get(0).name)
                        name_song_et?.setText(Html.fromHtml(list_of_products.get(0).textOfSong))

                    } else {author_song_et?.setText("cos jest nulem")}

                }
            }

            override fun onFailure(call: Call<DataBody>, t: Throwable) {

                author_song_et?.setText("Mamy jakis blad")

            }
        })
    }

    private fun getTextOfSong(client: MyWebService, pid: Int): Call<DataBody> {
        return client.getProductDetails(pid)
    }

    companion object {
        val PID_OF_SONG = "PID_OF_SONG"
    }


}

