package com.emergencyescape;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItineraryActivity extends CommonBehaviourActivity {

    // TODO: Creare Background Thread (RxJava) che va a fare il calcolo prendendo i valori(Departure (e) Destination) (per ora) da CommonBehaviourViewState

    @BindView(R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
