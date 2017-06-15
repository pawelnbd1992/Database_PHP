package com.pawel.database_php

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_texts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Texts : Activity() {
    var pid_of_song: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_texts)


        val extras = intent.extras

        if (extras != null) {
            pid_of_song = extras.getString(PID_OF_SONG)
        }

        val client = RetrofitBuilder().myWebService
        val call = getTextOfSong(client)
        getText(call,song_et)

    }

    private fun  getText(call: Call<DataBody>, song_et: TextView?) {
        call.enqueue(object : Callback<DataBody> {
            override fun onResponse(call: Call<DataBody>, response: Response<DataBody>) {

                if (response.isSuccessful) {

                    val list_of_products = ArrayList(response.body().getProduct())
                    




                }
            }

            override fun onFailure(call: Call<DataBody>, t: Throwable) {

            }
        })
    }

    private fun  getTextOfSong(client: MyWebService?): Call<DataBody> {
      return client!!.getProductDetails(pid_of_song)
    }

    companion object {
        val PID_OF_SONG = "PID_OF_SONG"
    }


}
