package com.example.mobile_developer.testbm1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobile_developer.testbm1.R;


 // here is our lancher activity
// i will use it to just intent to another activties

public class MainActivity extends AppCompatActivity {

    Button btn_main,btn_fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        btn_main = (Button) findViewById(R.id.button1);
        btn_fav = (Button) findViewById(R.id.button2);

        // move to Total activity

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in1 = new Intent(MainActivity.this, Total.class);
                startActivity(in1);

            }
        });

        // move to BookMarks activity

        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in2 = new Intent(MainActivity.this, BookMarks.class);
                startActivity(in2);

            }
        });



    }





 }

