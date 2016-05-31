package com.emergencyescape.main;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.greendao.DaoMaster;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.NoteDao;
import com.emergencyescape.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends CommonBehaviourActivity<MainView,MainPresenter> {

    @BindView(R.id.toolbar) Toolbar toolbar;

    private SQLiteDatabase db;

    private EditText editText;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private NoteDao noteDao;

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
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.settings_file, false); // Carica i valori di default delle opzioni

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        /**/

    }


    /*
    @OnClick(R.id.logoutbutton) // On click listener
    public void logout()
    {
        assert logout != null; // cosa serve?

        boolean logoutOk = presenter.logout(getApplicationContext()); // chiama il CommonBehaviourPresenter

        if (logoutOk){
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }

        // Le funzioni di prima per il logout le ho spostate nel CommonBehaviourPresenter
    }*/


}
