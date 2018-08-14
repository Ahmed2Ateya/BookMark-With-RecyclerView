package com.example.mobile_developer.testbm1.adapter;



import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_developer.testbm1.R;
import com.example.mobile_developer.testbm1.activities.Btn_fav_act;
import com.example.mobile_developer.testbm1.data.FavoriteContract;
import com.example.mobile_developer.testbm1.data.FavoriteDbHelper;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {
    public List<ExampleItem> exampleList;
    public List<ExampleItem> exampleListFull;

    public ExampleAdapter() {
    }

    public ExampleItem myitem;

    public static Context mContext;

    public FavoriteDbHelper favoriteDbHelper;
    public SQLiteDatabase mDb;


   public class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        MaterialFavoriteButton materialFavoriteButton1;


       ExampleViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            textView1 = (TextView) itemView.findViewById(R.id.text_view1);
            textView2 = (TextView) itemView.findViewById(R.id.text_view2);
            materialFavoriteButton1 = (MaterialFavoriteButton) itemView.findViewById(R.id.favorite_button1);


            // todo when user click on item and intent to our btn_fav activity

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        ExampleItem clickedDataItem = exampleList.get(pos);
                        Intent intent = new Intent(v.getContext(), Btn_fav_act.class);
                        intent.putExtra("movies", clickedDataItem);

                        /*
                        intent.putExtra("id", clickedDataItem.getId());
                        intent.putExtra("title", clickedDataItem.getText1());
                        intent.putExtra("rat", clickedDataItem.getText2());
                        intent.putExtra("img", clickedDataItem.getImageResource());
                       */

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(intent);

                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getText1(), Toast.LENGTH_SHORT).show();

                    }
                }
            });


        }


    }

    //  todo our  constructor

    public ExampleAdapter(Context mContext, List<ExampleItem> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
        this.mContext = mContext;
        favoriteDbHelper = new FavoriteDbHelper(mContext);
        mDb = favoriteDbHelper.getWritableDatabase();
        myitem = new ExampleItem();

    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,
                parent, false);
        return new ExampleViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {
        final ExampleItem currentItem = exampleList.get(position);

        holder.textView1.setText(currentItem.getText1().toString());
        holder.textView2.setText(currentItem.getText2().toString());
        holder.imageView.setImageResource(currentItem.getImageResource());


        // todo when user click materialFavoriteButton1 and - no - intent to btn_fav activity
        //  i mean you will bookmark without intent

        if (Exists(currentItem.getText1().toString())) {
            // when todo bookmark

            holder.materialFavoriteButton1.setFavorite(true);
            holder.materialFavoriteButton1.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                            try {

                                final String str1 = currentItem.getText1().toString();
                                final String str2 = currentItem.getText2().toString();
                                final int dd = currentItem.getId();
                                final String myid = String.valueOf(dd);
                                final int myimg = currentItem.getImageResource();

                                if (favorite == true) {
                                    // to save data in our sqlite databse

                                    favoriteDbHelper = new FavoriteDbHelper(mContext);
                                    myitem = new ExampleItem();
                                    myitem.setId(dd);
                                    myitem.setText1(str1);
                                    myitem.setText2(str2);
                                    myitem.setImageResource(myimg);

                                    favoriteDbHelper.addFavorite(myitem);

                                    Snackbar.make(buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT).show();// its like toast
                                } else {
                                    // todo delete from our sqlite

                                    favoriteDbHelper = new FavoriteDbHelper(mContext);
                                    favoriteDbHelper.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_TITLE, str1);
                                    Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();// its like toast
                                }
                            } catch (Exception e) {
                                Toast.makeText(mContext, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        } else {

            // when un bookmark

            holder.materialFavoriteButton1.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                            try {
                                final String str1 = currentItem.getText1().toString();
                                final String str2 = currentItem.getText2().toString();
                                final int dd = currentItem.getId();
                                final String myid = String.valueOf(dd);
                                final int myimg = currentItem.getImageResource();

                                if (favorite == true) {
                                    // to save data in our sqlite databse
                                    favoriteDbHelper = new FavoriteDbHelper(mContext);
                                    myitem = new ExampleItem();
                                    myitem.setId(dd);
                                    myitem.setText1(str1);
                                    myitem.setText2(str2);
                                    myitem.setImageResource(myimg);

                                    favoriteDbHelper.addFavorite(myitem);

                                    Snackbar.make(buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                                } else {

                                    // todo delete from our sqlite

                                    favoriteDbHelper = new FavoriteDbHelper(mContext);
                                    favoriteDbHelper.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_TITLE, str1);
                                    Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();

                                }

                            } catch (Exception e) {
                                Toast.makeText(mContext, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }

                        }


                    });
        }

    }

    // todo check if position of recyclerview item has found in our sqlite our no.

    public boolean Exists(String searchItem) {

        String[] projection = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING,

        };
        String selection = FavoriteContract.FavoriteEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";

        Cursor cursor = mDb.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


// todo implemmentation recyclerview methods

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    // todo search in our recyclerview items its just search i mean you cancel it

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ExampleItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ExampleItem item : exampleListFull) {
                    if (item.getText2().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



  // todo entity data class  (you can make this class in aseperate file java class)


    public static class ExampleItem implements Parcelable {



        @SerializedName("id")
        private int id;

        @SerializedName("imageResource")
        private int imageResource;

        @SerializedName("text1")
        private String text1;

        @SerializedName("text2")
        private String text2;

        public ExampleItem() {

        }

        public ExampleItem(String text1, String text2) {
            this.text1 = text1;
            this.text2 = text2;
        }

        public ExampleItem(int imageResource, String text1, String text2) {
            this.imageResource = imageResource;
            this.text1 = text1;
            this.text2 = text2;
        }

        public ExampleItem(int id, int imageResource, String text1, String text2) {
            this.id = id;
            this.imageResource = imageResource;
            this.text1 = text1;
            this.text2 = text2;
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getImageResource() {
            return imageResource;
        }

        public void setImageResource(int imageResource) {
            this.imageResource = imageResource;
        }

        public String getText1() {
            return text1;
        }

        public void setText1(String text1) {
            this.text1 = text1;
        }

        public String getText2() {
            return text2;
        }

        public void setText2(String text2) {
            this.text2 = text2;
        }



        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeString(this.text1);
            dest.writeString(this.text2);


            dest.writeValue(this.id);
            dest.writeValue(this.imageResource);

        }

        protected ExampleItem(Parcel in) {
            this.text1 = in.readString();
            this.text2 = in.readString();
            this.id = (int) in.readValue(int.class.getClassLoader());
            this.imageResource = (int) in.readValue(int.class.getClassLoader());

        }

        public static final Creator<ExampleItem> CREATOR = new Creator<ExampleItem>() {
            @Override
            public ExampleItem createFromParcel(Parcel source) {
                return new ExampleItem(source);
            }

            @Override
            public ExampleItem[] newArray(int size) {
                return new ExampleItem[size];
            }
        };
    }

}

