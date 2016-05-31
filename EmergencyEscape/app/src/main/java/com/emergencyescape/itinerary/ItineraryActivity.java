package com.emergencyescape.itinerary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.RxApplication;
import com.emergencyescape.server.ServerService;
import com.emergencyescape.server.model.Node;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ItineraryActivity extends CommonBehaviourActivity<ItineraryView,ItineraryPresenter> implements ItineraryView {

    // TODO: Creare Background Thread (RxJava) che va a fare il calcolo prendendo i valori(Departure (e) Destination) (per ora) da CommonBehaviourViewState

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.Percorso) TextView rxResponse;

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
        RxApplication rxApplication = (RxApplication) getApplication();
        service = rxApplication.getServerService();
        presenter = new ItineraryPresenter(this,service);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState!=null){
            rxCallInWorks = savedInstanceState.getBoolean(EXTRA_RX);
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        presenter.rxUnSubscribe();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_RX, rxCallInWorks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(rxCallInWorks)
            presenter.loadMaps();
    }

    @OnClick(R.id.buttonRx)
    public void startRx(){
        rxCallInWorks=true;
        presenter.loadMaps();
    }

    protected void showMapsResults(Node response){
        rxResponse.setText(response.getId().toString()); // TODO: Implementare RecycleView Adapter per stampare le risposte
        rxResponse.setVisibility(View.VISIBLE);
    }

    protected void showRxFailure(Throwable throwable){
        Log.d("TAG", throwable.toString());
        rxResponse.setText("ERROR");
        rxResponse.setVisibility(View.VISIBLE);
    }

}
