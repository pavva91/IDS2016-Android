package com.emergencyescape.qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QrActivity extends CommonBehaviourActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link MvpPresenter} for this view
     */
    @NonNull
    @Override
    public QrPresenter createPresenter() {
        return new QrPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emqr);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scanNow();


    }

    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }

    private void scanNow(){
        // TODO: Rifinire interfacciamento con zxing, per ora metto le 3 righe base che lo richiamano
        Intent intent = new Intent("com.google.zxing.client.android.SCAN"); //Intent zxing
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE" for bar codes
        startActivityForResult(intent, 0); //start barcode scanner zxing
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.i("xZing", "contents: "+contents+" format: "+format); // Handle successful scan
            }
            else if(resultCode == RESULT_CANCELED){ // Handle cancel
                Log.i("xZing", "Cancelled");
            }
        }
    }
}
