package com.emergencyescape.commonbehaviour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.emergencyescape.EmTapActivity;
import com.emergencyescape.TextDepartureActivity;
import com.emergencyescape.NoemTapActivity;
import com.emergencyescape.R;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by valerio on 27/04/2016.
 *
 * Activity standard da cui ereditare se si vuole avere:
 *      - activity con il menù(em,noem) con i relativi sotto menu
 *      - gestione comune logout
 */

public class CommonBehaviourActivity extends MvpViewStateActivity<MvpView, CommonBehaviourPresenter>
        implements MvpView {
    /**
     * Creates the ViewState instance
     */
    @Override
    public RestorableViewState createViewState() {
        return new CommonBehaviourViewState();
    }

    /**
     * Called if a new {@link ViewState} has been created because no viewstate from a previous
     * Activity or Fragment instance could be restored.
     * <p><b>Typically this is called on the first time the <i>Activity</i> or <i>Fragment</i> starts
     * and therefore no view state instance previously exists</b></p>
     */
    @Override
    public void onNewViewStateInstance() {

    }

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link CommonBehaviourPresenter} for this view
     */
    @NonNull
    @Override
    public CommonBehaviourPresenter createPresenter() {
        return new CommonBehaviourPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_emtext){
            Intent intent = new Intent(this,TextDepartureActivity.class).putExtra("emergencyState",true);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_emtap){
            Intent intent = new Intent(this,EmTapActivity.class).putExtra("emergencyState",true);
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
            Intent intent = new Intent(this,NoemTapActivity.class).putExtra("emergencyState",false);
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

    // Action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);



    }



}
