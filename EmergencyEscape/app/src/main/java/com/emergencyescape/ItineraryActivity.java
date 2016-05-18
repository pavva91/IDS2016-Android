package com.emergencyescape;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;


public class ItineraryActivity extends CommonBehaviourActivity {

    // TODO: Creare Background Thread (RxJava) che va a fare il calcolo prendendo i valori(Departure (e) Destination) (per ora) da CommonBehaviourViewState


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
