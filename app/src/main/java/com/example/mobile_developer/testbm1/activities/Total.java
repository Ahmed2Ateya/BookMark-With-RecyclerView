package com.example.mobile_developer.testbm1.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.mobile_developer.testbm1.R;

import com.example.mobile_developer.testbm1.adapter.ExampleAdapter;
import com.example.mobile_developer.testbm1.data.FavoriteDbHelper;

import java.util.ArrayList;
import java.util.List;

public class Total extends AppCompatActivity {

        private ExampleAdapter adapter;
        private List<ExampleAdapter.ExampleItem> exampleList;

    private FavoriteDbHelper favoriteDbHelper;
    private ExampleAdapter.ExampleItem favorite;
    private final AppCompatActivity activity = Total.this;
    private SQLiteDatabase mDb;

    ExampleAdapter.ExampleItem movie;
    String  movieName, rating;
    int movie_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);


            fillExampleList();
            setUpRecyclerView();


    }



    //  here is our data items
    private void fillExampleList() {
            exampleList = new ArrayList<>();

            exampleList.add(new ExampleAdapter.ExampleItem(R.drawable.s1, "One", "Ten"));
            exampleList.add(new ExampleAdapter.ExampleItem(R.drawable.s2, "Two", "Eleven"));
            exampleList.add(new ExampleAdapter.ExampleItem(R.drawable.s3, "Three", "Twelve"));
            exampleList.add(new ExampleAdapter.ExampleItem(R.drawable.s1, "Four", "Thirteen"));
            exampleList.add(new ExampleAdapter.ExampleItem(R.drawable.s2, "Five", "Fourteen"));
            exampleList.add(new ExampleAdapter.ExampleItem(R.drawable.s3, "Six", "Fifteen"));
            exampleList.add(new ExampleAdapter.ExampleItem(R.drawable.s1, "Seven", "Sixteen"));
            exampleList.add(new ExampleAdapter.ExampleItem(R.drawable.s2, "Eight", "Seventeen"));
            exampleList.add(new ExampleAdapter.ExampleItem(R.drawable.s3, "Nine", "Eighteen"));



        }

    // To Fill our Recyclerview

        private void setUpRecyclerView() {
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            adapter = new ExampleAdapter(getApplicationContext(),exampleList);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }


        // here is our search engine to search in our recyclerview
       /* it is no  must or mandatory code in our bookmark project i mean you can cancel it   */

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.action_search, menu);

            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) searchItem.getActionView();

            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
            return true;
        }


    }


