package com.pawel.database_php.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import com.pawel.database_php.R
import com.pawel.database_php.data.DataBody
import com.pawel.database_php.data.MyWebService
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TextsActivity : AppCompatActivity() {
    var pid_of_song: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_texts)

       // setSupportActionBar(toolbar)

        val extras = intent.extras

        if (extras != null) {
            pid_of_song = extras.getString(PID_OF_SONG)
        }

        val client = RetrofitBuilder().myWebService
        val call = getTextOfSong(client,pid_of_song)
        getText(call)




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_text,menu)

        var menuItem = menu?.findItem(R.id.search)

        return super.onCreateOptionsMenu(menu)
    }







    private fun  getText(call: Call<DataBody>) {
        call.enqueue(object : Callback<DataBody> {
            override fun onResponse(call: Call<DataBody>, response: Response<DataBody>) {

                if (response.isSuccessful) {

                            //potrzebna zmiana na Kotlina
                   //val list_of_products :ArrayList<DataBody.Product>?
                    val list_of_products = ArrayList(response.body().getProduct())

                    if (list_of_products != null) {

                        author_song_et?.setText(list_of_products.get(0).name)
                        name_song_et?.setText(Html.fromHtml(list_of_products.get(0).textOfSong))



                    }else{
                        author_song_et?.setText("cos jest nulem")
                    }



                }
            }

            override fun onFailure(call: Call<DataBody>, t: Throwable) {

                author_song_et?.setText("Mamy jakis blad")

            }
        })
    }

    private fun  getTextOfSong(client: MyWebService, pid:String): Call<DataBody> {
      return client.getProductDetails(pid)
    }

    companion object {
        val PID_OF_SONG = "PID_OF_SONG"
    }


}








