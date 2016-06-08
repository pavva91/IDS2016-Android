package com.emergencyescape.itinerary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.dijkstra.Graph;
import com.emergencyescape.server.ServerService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItineraryActivity extends CommonBehaviourActivity<ItineraryView,ItineraryPresenter> implements ItineraryView {

    // TODO: Creare Background Thread (RxJava) che va a fare il calcolo prendendo i valori(Departure (e) Destination) (per ora) da CommonBehaviourViewState

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.Percorso) TextView rxResponse;

    @BindView(R.id.pathListView) ListView pathListView;


    private static final String EXTRA_RX = "EXTRA_RX";
    private ServerService service;
    private boolean rxCallInWorks = false;

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link ItineraryPresenter} for this view
     */
    @NonNull
    @Override
    public ItineraryPresenter createPresenter() {
        presenter = new ItineraryPresenter();
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getEmergencyState()){
            showShortestPathEmergency();
        }else{
            showShortestPathNoEmergency();
        }


        if(savedInstanceState!=null){
            rxCallInWorks = savedInstanceState.getBoolean(EXTRA_RX);
        }


    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showShortestPathNoEmergency(){ // No emergency, sembra funzionare
        Graph.CostPathPair shortestPath = presenter.getShortestPath(
                presenter.getDeparture(),
                presenter.getDestination());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, shortestPath.getPath());

        pathListView.setAdapter(adapter);
    }

    @Override
    public void showShortestPathEmergency() { // Emergency, calcola tutti i possibili path, ma non funziona
        Graph.CostPathPair shortestPath = presenter.getEmergencyShortestPath(
                presenter.getDeparture(),
                presenter.getEmergencyDestinations());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, shortestPath.getPath());//TODO: Far ritornare List<Edge> (Edge del greenDAO)

        pathListView.setAdapter(adapter);
    }


    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }
}
