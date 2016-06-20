package com.emergencyescape.main;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.facebook.stetho.Stetho;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends CommonBehaviourActivity<MainView,MainPresenter> {

    @BindView(R.id.toolbar) Toolbar toolbar;

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link MainPresenter} for this view
     */
    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.settings_file, false); // Carica i valori di default delle opzioni

        ButterKnife.bind(this);

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this); // TODO: Usare shared Betta
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {

            presenter.loadServer2Db();  // Carico il DB al primo lancio dell'app


            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();

        }




        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
}
