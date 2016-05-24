package com.emergencyescape.itinerary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.rxretrofit.FriendResponse;
import com.emergencyescape.rxretrofit.NetworkService;
import com.emergencyescape.rxretrofit.RxApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ItineraryActivity extends CommonBehaviourActivity<ItineraryView,ItineraryPresenter> {

    // TODO: Creare Background Thread (RxJava) che va a fare il calcolo prendendo i valori(Departure (e) Destination) (per ora) da CommonBehaviourViewState

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.Percorso) TextView rxResponse;

    private static final String EXTRA_RX = "EXTRA_RX";
    private NetworkService service;
    private boolean rxCallInWorks = false;

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link ItineraryPresenter} for this view
     */
    @NonNull
    @Override
    public ItineraryPresenter createPresenter() {
        RxApplication rxApplication = (RxApplication)getApplication();
        service = rxApplication.getNetworkService();
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
            presenter.loadRxData();
    }

    @OnClick(R.id.buttonRx)
    public void startRx(){
        rxCallInWorks=true;
        presenter.loadRxData();
    }


    protected void showRxResults(FriendResponse response){
        rxResponse.setText(response.friendLocations.data.friend.get(1).friendName);
        rxResponse.setVisibility(View.VISIBLE);
    }

    protected void showRxFailure(Throwable throwable){
        Log.d("TAG", throwable.toString());
        rxResponse.setText("ERROR");
        rxResponse.setVisibility(View.VISIBLE);
    }

}
