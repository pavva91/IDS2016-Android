package com.emergencyescape.qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.itinerary.ItineraryActivity;
import com.emergencyescape.main.MainPresenter;
import com.emergencyescape.main.MainView;
import com.emergencyescape.text.TextDestinationActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QrActivity extends CommonBehaviourActivity<QrView,QrPresenter> {
    @BindView(R.id.toolbar) Toolbar toolbar;

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link QrPresenter} for this view
     */
    @NonNull
    @Override
    public QrPresenter createPresenter() {
        return new QrPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentToStart;
        if (this.getEmergencyState()) {
            intentToStart = new Intent(QrActivity.this, ItineraryActivity.class);
        }else{
            intentToStart = new Intent(QrActivity.this,TextDestinationActivity.class);
        }
        if (correctNameQr(getIntent().getStringExtra("qrValue"))){
            presenter.setUserDeparture(this.getAula());
            startActivity(intentToStart);
        }else{
            Toast.makeText(this, getResources().getString(R.string.wrong_qr), Toast.LENGTH_LONG).show();
        }
    }

    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }

    private String getAula(){
        return getIntent().getStringExtra("qrValue");
    }

    /**
     * Verifica esistenza qrValue nel DB
     * @param qrValue
     * @return
     */
    private boolean correctNameQr(String qrValue){
        List<String> nodesList = presenter.getNodesList();
        for (String nodeName : nodesList){
            if (qrValue.equals(nodeName)){
                return true;
            }
        }
        return false;
    }
}
