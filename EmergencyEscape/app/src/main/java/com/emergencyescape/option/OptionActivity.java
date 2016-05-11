package com.emergencyescape.option;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.emergencyescape.EmQrActivity;
import com.emergencyescape.EmTapActivity;
import com.emergencyescape.EmTextActivity;
import com.emergencyescape.NoEmTapActivity;
import com.emergencyescape.NoEmTextActivity;
import com.emergencyescape.R;

/**
 * Created by valerio on 27/04/2016.
 *
 * Activity standard da cui ereditare se si vuole avere l'activity con il menù(em,noem) con i relativi sotto menu
 */

public class OptionActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_emtext:
                startActivity(new Intent(this,EmTextActivity.class));
                break;
            case R.id.action_emtap:
                startActivity(new Intent(this,EmTapActivity.class));
                break;
            case R.id.action_emqr:
                startActivity(new Intent(this,EmQrActivity.class));
                break;
            case R.id.action_noemtext:
                startActivity(new Intent(this,NoEmTextActivity.class));
                break;
            case R.id.action_noemtap:
                startActivity(new Intent(this,NoEmTapActivity.class));
                break;
            case R.id.action_noemqr:
                Intent qrIntent = new Intent("com.google.zxing.client.android.SCAN"); //Intent zxing
                qrIntent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE" for bar codes
                startActivityForResult(qrIntent, 0); //start barcode scanner zxing
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
