package com.pawel.database_php

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.ipaulpro.afilechooser.utils.FileUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class YourSongsActivity : AppCompatActivity() {


    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private var mViewPager: ViewPager? = null
    private var auth: FirebaseAuth? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {

            } else {
                val intent = Intent(this@YourSongsActivity, SignInActivity::class.java)
                startActivity(intent)
                this@YourSongsActivity.finish()
                return@AuthStateListener


            }
        }
        setContentView(R.layout.activity_your__songs)


        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_your__songs, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        val context = applicationContext
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            signout()
            val intent = Intent(context, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun signout() {
        auth!!.signOut()
    }

    override fun onStart() {
        super.onStart()
        auth!!.addAuthStateListener(authStateListener!!)
    }

    class Fragment1 : Fragment() {


        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val tab = arguments.getInt(ARG_SECTION_NUMBER)

            when (tab) {
                1 -> {
                    val rootView = inflater!!.inflate(R.layout.fragment_your__songs, container, false)
                    val listView = rootView.findViewById(R.id.list_of_songs) as ListView
                    val client = RetrofitBuilder().myWebService
                    val call = getDataBodyCall(client)
                    val search_song = rootView.findViewById(R.id.search_song) as EditText
                    getAllSongs(listView, call, search_song)
                    val add_song_button = rootView.findViewById(R.id.add_song_button) as Button
                    AddNewSong(add_song_button)
                    return rootView
                }

                2 -> {
                }
            }

            return null
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

        private fun getAllSongs(listView: ListView, call: Call<DataBody>, editText: EditText) {
            call.enqueue(object : Callback<DataBody> {
                override fun onResponse(call: Call<DataBody>, response: Response<DataBody>) {

                    if (response.isSuccessful) {

                        val list_of_products = ArrayList(response.body().getProduct())
                        if (list_of_products != null) {

                            val productAdapter = ProductAdapter(activity.applicationContext, list_of_products)
                            listView.adapter = productAdapter
                            SearchSong(editText, productAdapter)
                            getListView(listView)

                        }
                    }
                }

                override fun onFailure(call: Call<DataBody>, t: Throwable) {

                }
            })
        }

        private fun getListView(listView: ListView) {
            listView.setOnItemClickListener { adapterView, view, i, l ->

                    val pidOfSong = (view.findViewById(R.id.pid) as TextView).text.toString()
                    val intent = Intent(context, Texts::class.java)
                    intent.putExtra(PID_OF_SONG, pidOfSong)
                    startActivity(intent)
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

            val PID_OF_SONG = "PID_OF_SONG"
            private val ARG_SECTION_NUMBER = "section_number"
            private val REQUEST_CHOOSER = 1234

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */

            fun newInstance(sectionNumber: Int): Fragment1 {
                val fragment = Fragment1()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }

    }//  SaveAdapter saveAdapter = new SaveAdapter();


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a Fragment1 (defined as a static inner class below).
            return Fragment1.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Twoje utwory"
                1 -> return "Wyszukaj"
            }
            return null
        }
    }


}
