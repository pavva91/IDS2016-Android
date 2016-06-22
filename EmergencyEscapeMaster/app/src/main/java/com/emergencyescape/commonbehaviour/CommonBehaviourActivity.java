package com.emergencyescape.commonbehaviour;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.emergencyescape.qr.QrActivity;
import com.emergencyescape.text.TextDepartureActivity;
import com.emergencyescape.tap.TapActivity;
import com.emergencyescape.R;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

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
            Intent intent = new Intent(this,QrActivity.class).putExtra("emergencyState",true);
            startActivity(intent);
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
            Intent intent = new Intent(this,QrActivity.class).putExtra("emergencyState",false);
            startActivity(intent);
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
