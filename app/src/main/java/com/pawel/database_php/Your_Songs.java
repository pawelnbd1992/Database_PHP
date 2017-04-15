package com.pawel.database_php;

import android.content.Context;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
            startActivity(intent);
            Your_Songs.this.finish();

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
//--------------class Fragment1--------------------//
    public static class Fragment1 extends Fragment {

        public static final String TITLE_OF_SONG = "TITLE_OF_SONG";
        private static final String ARG_SECTION_NUMBER = "section_number";


        public Fragment1() {
        }


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
                    MyWebService client = getMyWebService();
                    Call<DataBody> call = getDataBodyCall(client);
                    EditText search_song = (EditText) rootView.findViewById(R.id.search_song);
                    getAllSongs(listView, call,search_song);
                    return rootView;

                case 2:
                    break;
            }

           return null;
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

                  String name = ((TextView) view.findViewById(R.id.name_all_products)).getText().toString();
                    Toast.makeText(getContext(),"Pozycja "+name,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(),Texts.class);
                    intent.putExtra(TITLE_OF_SONG,name);
                    startActivity(intent);
                }
            });

        }

        private MyWebService getMyWebService() {
            RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
            Retrofit.Builder builder = retrofitBuilder.getBuilder();
            Retrofit retrofit = builder.build();
            return retrofit.create(MyWebService.class);
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
