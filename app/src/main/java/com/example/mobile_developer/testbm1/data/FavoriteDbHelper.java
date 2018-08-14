package com.example.mobile_developer.testbm1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.mobile_developer.testbm1.adapter.ExampleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by delaroy on 5/27/17.
 */
public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION = 100;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID + " INTEGER, " +
                FavoriteContract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING + " TEXT NOT NULL  ,"+
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL " + " ); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addFavorite(ExampleAdapter.ExampleItem str_data){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID, str_data.getId());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, str_data.getText1());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_USERRATING, str_data.getText2());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH, str_data.getImageResource());



        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
        db.close();
    }


/*
    public void deleteFavorite(int id)
     {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_MOVIEID+ " = " + id,null);
     }
*/


    public void delete(String tableName, String cloName, String colValue)
    {
         SQLiteDatabase db = this.getWritableDatabase();
         db.delete(tableName, cloName + " = ? ", new String[]{colValue});

    }




    public List<ExampleAdapter.ExampleItem> getAllFavorite(){
        String[] columns = {
              FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING,
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH,


        };
        String sortOrder =
                FavoriteContract.FavoriteEntry._ID + " ASC";
        List<ExampleAdapter.ExampleItem> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                ExampleAdapter.ExampleItem movie = new ExampleAdapter.ExampleItem();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID))));
                movie.setText1(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TITLE)));
                movie.setText2(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_USERRATING)));
                movie.setImageResource(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH))));


                favoriteList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }

}
