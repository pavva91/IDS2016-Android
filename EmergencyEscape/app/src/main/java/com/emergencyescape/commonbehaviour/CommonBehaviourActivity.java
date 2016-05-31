package com.emergencyescape.commonbehaviour;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.emergencyescape.login.LoginActivity;
import com.emergencyescape.main.MainPresenter;
import com.emergencyescape.settings.SettingsActivity;
import com.emergencyescape.text.TextDepartureActivity;
import com.emergencyescape.tap.TapActivity;
import com.emergencyescape.R;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by valerio on 27/04/2016.
 *
 * Activity standard da cui ereditare se si vuole avere:
 *      - activity con il menù(em,noem) con i relativi sotto menu
 *      - gestione comune logout
 */

public abstract class CommonBehaviourActivity<V extends CommonBehaviourView, P extends MvpPresenter<V>> extends MvpActivity<V,P>
        implements CommonBehaviourView {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_emtext){
            Intent intent = new Intent(this,TextDepartureActivity.class).putExtra("emergencyState",true);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_emtap){
            Intent intent = new Intent(this,TapActivity.class).putExtra("emergencyState",true);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_emqr){  // TODO: Sistemare il qr-code in modo da fare un intent interno (QrActivity) che a sua volta interagisce con zxing
            Intent intent = new Intent("com.google.zxing.client.android.SCAN"); //Intent zxing
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE" for bar codes
            startActivityForResult(intent, 0); //start barcode scanner zxing
            return true;
        }


        if(id == R.id.action_noemtext){
            Intent intent = new Intent(this,TextDepartureActivity.class).putExtra("emergencyState",false);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_noemtap){
            Intent intent = new Intent(this,TapActivity.class).putExtra("emergencyState",false);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_noemqr){
            Intent intent = new Intent("com.google.zxing.client.android.SCAN"); //Intent zxing
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE" for bar codes
            startActivityForResult(intent, 0); //start barcode scanner zxing
            return true;
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_logout) { // Creo un presenter perchè CommonBehaviourPresenter è Astratto
                MainPresenter presenterCommonBehaviourDescendentan = new MainPresenter();

                boolean logoutOk = presenterCommonBehaviourDescendentan.logout(getApplicationContext()); // Effettua il logout

                if (logoutOk) {
                    Intent loginIntent = new Intent(this, LoginActivity.class); // Reindirizzo al login
                    startActivity(loginIntent);
                }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) { // Action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);



    }



}
