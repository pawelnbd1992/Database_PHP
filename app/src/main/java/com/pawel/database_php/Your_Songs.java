package com.pawel.database_php;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Your_Songs extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){

                }else {
                    Intent intent = new Intent(Your_Songs.this,SignInActivity.class);
                    startActivity(intent);
                    Your_Songs.this.finish();
                    return;


                }


            }
        };
        setContentView(R.layout.activity_your__songs);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_your__songs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Context context = getApplicationContext();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            signout();
            Intent intent = new Intent(context,SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signout() {
        auth.signOut();
    }

    @Override
    protected void onStart() {
     super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    public static class Fragment1 extends Fragment {

        public static final String URL_OF_SONG = "URL_OF_SONG";
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final int REQUEST_CHOOSER =1234 ;
        private Button add_song_button;
      //  SaveAdapter saveAdapter = new SaveAdapter();

        public Fragment1() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */

        public static Fragment1 newInstance(int sectionNumber) {
            Fragment1 fragment = new Fragment1();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            int tab = getArguments().getInt(ARG_SECTION_NUMBER);

            switch (tab){
                case 1:
                    View rootView = inflater.inflate(R.layout.fragment_your__songs, container, false);
                    ListView listView = (ListView) rootView.findViewById(R.id.list_of_songs);
                    MyWebService client = new RetrofitBuilder().getMyWebService();
                    Call<DataBody> call = getDataBodyCall(client);
                    EditText search_song = (EditText) rootView.findViewById(R.id.search_song);
                    getAllSongs(listView, call,search_song);
                    add_song_button = (Button) rootView.findViewById(R.id.add_song_button);
                    AddNewSong(add_song_button);
                    return rootView;

                case 2:
                    break;
            }

           return null;
        }

        private void AddNewSong(Button add_song_button) {
            add_song_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show();

                }
            });
        }

        private void show() {
            Intent getContentIntent = FileUtils.createGetContentIntent();

            Intent intent = Intent.createChooser(getContentIntent, "Select a File");
            startActivityForResult(intent,REQUEST_CHOOSER);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case REQUEST_CHOOSER:
                    if (resultCode == RESULT_OK) {
                        if (data != null) {
                            // Get the URI of the selected file
                            final Uri uri = data.getData();
                            try {
                                // Get the file path from the URI
                                final String path = FileUtils.getPath(getActivity(), uri);

                            } catch (Exception e) {

                            }
                        }
                    }
                    break;
            }


            super.onActivityResult(requestCode, resultCode, data);
        }

        private Call<DataBody> getDataBodyCall(MyWebService client) {
            return client.getAllProduct();
        }

        private void getAllSongs(final ListView listView, Call<DataBody> call, final EditText editText) {
            call.enqueue(new Callback<DataBody>() {
                @Override
                public void onResponse(Call<DataBody> call, Response<DataBody> response) {

                    if (response.isSuccessful()) {

                      List<DataBody.Product> list_of_products = new ArrayList<>(response.body().getProduct());

                        if (list_of_products != null) {

                            ProductAdapter productAdapter = new ProductAdapter(getActivity().getApplicationContext(), list_of_products);
                            listView.setAdapter(productAdapter);
                            SearchSong(editText,productAdapter);
                            getListView(listView);

                                                    }
                                                }
                }

                @Override
                public void onFailure(Call<DataBody> call, Throwable t) {

                }
            });
        }

        private void getListView(ListView listView) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                  String title_of_song = ((TextView) view.findViewById(R.id.name_all_products)).getText().toString();
                    String url =createURL(title_of_song);
                    Intent intent = new Intent(getContext(),Texts.class);
                    intent.putExtra(URL_OF_SONG,url);
                    startActivity(intent);
                }
            });

        }

        private String createURL(String title_of_song) {
            title_of_song = "http://pawelnbd.ayz.pl/pdf/"+title_of_song+".pdf";
            return title_of_song;
        }


        private void SearchSong(EditText search_song , final ProductAdapter productAdapter) {
            search_song.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                    productAdapter.getFilter().filter(s);

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a Fragment1 (defined as a static inner class below).
            return Fragment1.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Twoje utwory";
                case 1:
                    return "Wyszukaj";


            }
            return null;
        }
    }



}
