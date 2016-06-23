package com.emergencyescape.main;

import android.content.Intent;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.Toast;

import com.emergencyescape.R;
import com.emergencyescape.businesslogic.SessionClass;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends CommonBehaviourActivity<MainView,MainPresenter>
{
    @BindView(R.id.logoutbutton)
    Button logout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

    }


    @OnClick(R.id.logoutbutton) // On click listener
    public void logout()
    {
        boolean logoutOk = presenter.logout(getApplicationContext()); // chiama il CommonBehaviourPresenter

        if (logoutOk)
        {
            Toast.makeText(getApplicationContext(), "A presto!", Toast.LENGTH_LONG).show();
            Intent loginIntent = new Intent(MainActivity.this, com.emergencyescape.login.LoginActivity.class);
            startActivity(loginIntent);
        }

        // Le funzioni di prima per il logout le ho spostate nel CommonBehaviourPresenter
    }


}