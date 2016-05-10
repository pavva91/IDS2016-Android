package com.emergencyescape.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.emergencyescape.R;

public class EmTextActivity extends CommonMenuActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emtext);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //attività bottone
        Button button = (Button) findViewById(R.id.btnPartenza);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: Richiamare funzione del presenter dedicato per delegare la business logic.
                startActivity(new Intent(EmTextActivity.this, ItineraryActivity.class));
            }
        });
    }

    //action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }




}
