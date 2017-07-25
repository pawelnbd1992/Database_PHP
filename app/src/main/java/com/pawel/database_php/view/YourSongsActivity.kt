package com.pawel.database_php.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.pawel.database_php.R
import com.pawel.database_php.view.auth.SignInActivity
import com.pawel.database_php.view.songlist.SongListFragment
import com.pawel.database_php.view.songtext.DisplayTextFragment
import kotlinx.android.synthetic.main.activity_your__songs.*

class YourSongsActivity : AppCompatActivity(), SearchView.OnQueryTextListener, SongListFragment.SongListFragmentListener {

    var songList: SongListFragment = null!!
    private var auth: FirebaseAuth? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null
    override fun onQueryTextSubmit(query: String?): Boolean {
        throw UnsupportedOperationException("not implemented")

    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return true;
    }



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
        setSupportActionBar(toolbar)
        if (savedInstanceState != null) {
            songList = SongListFragment()
            var transation: android.support.v4.app.FragmentTransaction = supportFragmentManager.beginTransaction()
            transation.add(R.id.head_container, songList)
            transation.commit()

        }


    }

    override fun onItemSelected(position: Int) {
        val displaytext: DisplayTextFragment = DisplayTextFragment()
        var transaction: android.support.v4.app.FragmentTransaction? = supportFragmentManager.beginTransaction()
        transaction!!.replace(R.id.head_container, displaytext as android.support.v4.app.Fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_your__songs, menu)

        val searchViewItem = menu.findItem(R.id.search_song_searchview)

        val searchViewAndroidActionBar = MenuItemCompat.getActionView(searchViewItem) as SearchView

        searchViewAndroidActionBar.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu);
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        val context = applicationContext
        if (id == R.id.search) {

            signout()
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
            finish()

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


}
