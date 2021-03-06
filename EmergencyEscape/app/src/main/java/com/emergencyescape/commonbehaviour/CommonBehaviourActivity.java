package com.emergencyescape.commonbehaviour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.emergencyescape.MyApplication;
import com.emergencyescape.utils.Server2Db;
import com.emergencyescape.login.LoginActivity;
import com.emergencyescape.main.MainActivity;
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
            refreshDB();
            setBestPathUI();
            Intent intent = new Intent(this,TextDepartureActivity.class).putExtra("emergencyState",true);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_emtap){
            refreshDB();
            setBestPathUI();
            Intent intent = new Intent(this,TapActivity.class).putExtra("emergencyState",true);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_emqr){
            refreshDB();
            setBestPathUI();
            try
            {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                emergencyState = true;
                startActivityForResult(intent, 0);
                return true;
            }
            catch (Exception e){
                Toast.makeText(MyApplication.context, "Per utilizzare il QR devi scaricare l'app BAR CODE SCANNER!", Toast.LENGTH_LONG).show();
            }

        }


        if(id == R.id.action_noemtext){
            refreshDB();
            setBestPathUI();
            Intent intent = new Intent(this,TextDepartureActivity.class).putExtra("emergencyState",false);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_noemtap){
            refreshDB();
            setBestPathUI();
            Intent intent = new Intent(this,TapActivity.class).putExtra("emergencyState",false);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_noemqr){
            refreshDB();
            setBestPathUI();
            try
            {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                emergencyState = false;
                startActivityForResult(intent, 0);

                return true;
            }
            catch (Exception e){
                Toast.makeText(MyApplication.context, "Per utilizzare il QR devi scaricare l'app BAR CODE SCANNER!", Toast.LENGTH_LONG).show();
            }
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

    protected void refreshDB(){
        Server2Db server2Db = MyApplication.getInstance().getServer2Db();
        server2Db.setToken();
        server2Db.refreshDb();
    }

    protected void setBestPathUI(){
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = wmbPreference.edit();
        editor.putBoolean("BEST_PATH_UI", true);
        editor.commit();
    }

    protected void setAlternativePathUI(){
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = wmbPreference.edit();
        editor.putBoolean("BEST_PATH_UI", false);
        editor.commit();
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
                Intent errorIntent = new Intent(this, MainActivity.class);
                startActivity(errorIntent);
            }
        }
    }
}
