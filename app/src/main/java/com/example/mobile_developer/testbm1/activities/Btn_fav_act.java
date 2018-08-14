package com.example.mobile_developer.testbm1.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mobile_developer.testbm1.R;
import com.example.mobile_developer.testbm1.adapter.ExampleAdapter;
import com.example.mobile_developer.testbm1.data.FavoriteContract;
import com.example.mobile_developer.testbm1.data.FavoriteDbHelper;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

// here is the detail will be show when user click on our recyclerview items

public class Btn_fav_act extends AppCompatActivity {


    private FavoriteDbHelper favoriteDbHelper;
    private ExampleAdapter.ExampleItem favorite;
    private final AppCompatActivity activity = Btn_fav_act.this;
    private SQLiteDatabase mDb;
    ExampleAdapter.ExampleItem movie;
    String  movieName, rating;
    int movie_id,myimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_fav_act);



        try{


        favoriteDbHelper=new FavoriteDbHelper(this);
        mDb = favoriteDbHelper.getWritableDatabase();

      // get data from adtapter

        Intent data = getIntent();

        if (data.hasExtra("movies"))
        {



/*
            movie_id = data.getExtras().getInt("id");
            movieName = data.getExtras().getString("title");
            rating = data.getExtras().getString("rat");
            myimg = data.getExtras().getInt("img");
*/

            movie = getIntent().getParcelableExtra("movies");
            movie_id = movie.getId();
            movieName = movie.getText1();
            rating = movie.getText2();
            myimg = movie.getImageResource();



           }

           // action when user click on bookmark button (materialFavoriteButton)

        MaterialFavoriteButton materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite_button);


        if (Exists(movieName)){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true)
                            {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                            else
                                {
                                favoriteDbHelper = new FavoriteDbHelper(Btn_fav_act.this);

     favoriteDbHelper.delete(FavoriteContract.FavoriteEntry.TABLE_NAME,FavoriteContract.FavoriteEntry.COLUMN_TITLE,movieName);

      Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show(); // its like toast
                               }
                        }
                    });
                }

        else
            {

            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else
                                {
                                favoriteDbHelper = new FavoriteDbHelper(Btn_fav_act.this);
  favoriteDbHelper.delete(FavoriteContract.FavoriteEntry.TABLE_NAME,FavoriteContract.FavoriteEntry.COLUMN_TITLE,movieName);

              Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show(); // its like toast
                            }
                        }
                    });
               }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }


    }



    // to chech if position of recyclerview item has found in our sqlite our no

    public boolean Exists(String searchItem) {

        String[] projection = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING,
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH,

        };
        String selection = FavoriteContract.FavoriteEntry.COLUMN_TITLE + " =?";
        String [] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = mDb.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

  // to put position of recyclerview item data in our Recyclerview

    public void saveFavorite()
    {
            favoriteDbHelper = new FavoriteDbHelper(activity);
            favorite = new ExampleAdapter.ExampleItem();

            favorite.setId(movie_id);
            favorite.setText1(movieName);
            favorite.setText2(rating);
            favorite.setImageResource(myimg);

            favoriteDbHelper.addFavorite(favorite);

    }





}
