package com.emergencyescape.option;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.emergencyescape.EmergencyActivity;
import com.emergencyescape.NoEmergencyActivity;
import com.emergencyescape.R;

/**
 * Created by Valerio Mattioli on 27/04/2016.
 *
 * Activity standard da cui ereditare se si vuole avere l'activity con il menù(em,noem) con i relativi sotto menu
 *
 * NB: Esistono solo due Activity che fanno poi da controller sui Fragment da invocare
 */

public class OptionActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_emtext:
                startActivity(new Intent(this,EmergencyActivity.class).putExtra("fragment", "DepartureTextFragment"));
                break;
            case R.id.action_emtap:
                startActivity(new Intent(this,EmergencyActivity.class).putExtra("fragment", "DepartureTapFragment"));
                break;
            case R.id.action_emqr:
                startActivity(new Intent(this,EmergencyActivity.class).putExtra("fragment", "DepartureQrFragment"));
                break;
            case R.id.action_noemtext:
                startActivity(new Intent(this,NoEmergencyActivity.class).putExtra("fragment", "DepartureTextFragment"));
                break;
            case R.id.action_noemtap:
                startActivity(new Intent(this,NoEmergencyActivity.class).putExtra("fragment", "DepartureTapFragment"));
                break;
            case R.id.action_noemqr:
                startActivity(new Intent(this,NoEmergencyActivity.class).putExtra("fragment", "DepartureQrFragment"));

                /*TODO: Fare Refactoring di questo script che chiama l'intent QRCODE su Presenter QR
                Intent qrIntent = new Intent("com.google.zxing.client.android.SCAN"); //Intent zxing
                qrIntent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE" for bar codes
                startActivityForResult(qrIntent, 0); //start barcode scanner zxing
                */

                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
