package com.example.mobile_developer.testbm1.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.mobile_developer.testbm1.R;
import com.example.mobile_developer.testbm1.adapter.ExampleAdapter;
import com.example.mobile_developer.testbm1.data.FavoriteDbHelper;

import java.util.ArrayList;
import java.util.List;

public class BookMarks extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener

{

   //  This activity to show our BookMarks

    private RecyclerView recyclerView1;
    private static ExampleAdapter adapter;
    private static List<ExampleAdapter.ExampleItem> movieList;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private  static FavoriteDbHelper favoriteDbHelper;

    private AppCompatActivity activity = BookMarks.this;
    public static final String LOG_TAG = ExampleAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_marks);


        initViews2();
    }

    // to show our recyclerview data

    private void initViews2(){
        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        movieList = new ArrayList<>();
        adapter = new ExampleAdapter (getApplicationContext(),movieList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView1.setLayoutManager(new GridLayoutManager(this, 1));
        } else {
            recyclerView1.setLayoutManager(new GridLayoutManager(this, 1));
        }

        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        favoriteDbHelper = new FavoriteDbHelper(activity);

        getAllFavorite();
    }

  // To get data from  our sqlite database

    private static void getAllFavorite()
    {
        new  AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                movieList.clear();
                movieList.addAll(favoriteDbHelper.getAllFavorite());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }


     public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;

    }



     @Override
     public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s){
         Log.d(LOG_TAG, "Preferences updated");
         checkSortOrder();
     }

     private void checkSortOrder(){


         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
         String sortOrder = preferences.getString(
                 this.getString(R.string.pref_sort_order_key),
                 this.getString(R.string.favorite)
         );
         if (sortOrder.equals(this.getString(R.string.favorite))){
             Log.d(LOG_TAG, "Sorting by favorite");
             initViews2();
         }
     }

     @Override
     public void onResume(){
         super.onResume();
         if (movieList.isEmpty()){
             checkSortOrder();
         }
         else{

             checkSortOrder();
         }
     }



}
