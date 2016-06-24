package com.emergencyescape.commonbehaviour;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.emergencyescape.MyApplication;
import com.emergencyescape.login.LoginActivity;
import com.emergencyescape.main.MainPresenter;
import com.emergencyescape.qr.QrActivity;
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
    boolean emergencyState = false; // Aggiunto per gestire qr-code  (zxing elimina l'extra)

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

        if(id == R.id.action_emqr){
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            emergencyState = true;
            startActivityForResult(intent, 0);
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
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            emergencyState = false;
            startActivityForResult(intent, 0);
            return true;
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_logout) { // Creo un presenter perchè CommonBehaviourPresenter è Astratto
                MainPresenter presenterCommonBehaviourDescendentan = new MainPresenter();

                boolean logoutOk = presenterCommonBehaviourDescendentan.logout(getApplicationContext()); // Effettua il logout

                if (logoutOk)
                {
                    Toast.makeText(getApplicationContext(), "A presto!", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String qrValue = data.getStringExtra("SCAN_RESULT"); //this is the result
                Intent intentQr = new Intent(this, QrActivity.class);
                intentQr.putExtra("qrValue",qrValue).putExtra("emergencyState",emergencyState);
                startActivity(intentQr);

            } else
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,getResources().getString(R.string.error_qr),Toast.LENGTH_LONG).show();
            }
        }
    }



}
