package com.emergencyescape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by valerio on 27/04/2016.
 *
 * Activity standard da cui ereditare se si vuole avere l'activity con il menù(em,noem) con i relativi sotto menu
 */

public class CommonMenuActivity extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_emtext){
            Intent intent = new Intent(this,EmTextActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_emtap){
            Intent intent = new Intent(this,EmTapActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_emqr){
            Intent intent = new Intent("com.google.zxing.client.android.SCAN"); //Intent zxing
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE" for bar codes
            startActivityForResult(intent, 0); //start barcode scanner zxing
            return true;
        }


        if(id == R.id.action_noemtext){
            Intent intent = new Intent(this,NoemTextActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_noemtap){
            Intent intent = new Intent(this,NoemTapActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_noemqr){
            Intent intent = new Intent("com.google.zxing.client.android.SCAN"); //Intent zxing
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE" for bar codes
            startActivityForResult(intent, 0); //start barcode scanner zxing
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
