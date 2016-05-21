package com.emergencyescape.utility;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.emergencyescape.R;
import com.emergencyescape.utility.CommonMenuActivity;


public class ItineraryActivity extends CommonMenuActivity {

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

          String quota = getIntent().getExtras().getString("quota");
          String partenza = getIntent().getExtras().getString("partenza");
          String destinazione = getIntent().getExtras().getString("dest");
          System.out.println("quota " + String.valueOf(quota) + " partenza " + String.valueOf(partenza) + " destinazione " + String.valueOf(destinazione));

           }





    //action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);



    }




}
