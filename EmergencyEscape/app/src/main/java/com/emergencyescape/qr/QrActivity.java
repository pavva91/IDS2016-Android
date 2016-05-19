package com.emergencyescape.qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QrActivity extends CommonBehaviourActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emqr);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: Rifinire interfacciamento con zxing, per ora metto le 3 righe base che lo richiamano
        Intent intent = new Intent("com.google.zxing.client.android.SCAN"); //Intent zxing
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE" for bar codes
        startActivityForResult(intent, 0); //start barcode scanner zxing
    }

    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }
}
