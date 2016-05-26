package com.emergencyescape.main;

import android.content.Intent;
import android.os.Bundle;

<<<<<<< HEAD
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;


import com.emergencyescape.LoginActivity;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends CommonBehaviourActivity<MainView,MainPresenter> {
    @BindView(R.id.logoutbutton) Button logout;
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
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

    }


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
=======
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.emergencyescape.utility.CommonMenuActivity;
import com.emergencyescape.login.LoginActivity;
import com.emergencyescape.R;
import com.emergencyescape.businesslogic.SessionClass;


public class MainActivity extends CommonMenuActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        logout();
    }


    //action bar
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);


        return super.onCreateOptionsMenu(menu);
    }

    private void logout()
    {
        Button logout = (Button) findViewById(R.id.logoutbutton);

        assert logout != null;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // definisco l'intenzione
                Intent openPage1 = new Intent(MainActivity.this, LoginActivity.class);
                // passo all'attivazione dell'activity Pagina.java
                startActivity(openPage1);

                SessionClass sc = SessionClass.getInstance();

                Toast.makeText(getApplicationContext(), "A presto " + sc.getUser(getApplicationContext()) + "!", Toast.LENGTH_LONG).show();

                //pulisce le variabili globali
                sc.clearRestaconnesso(getApplicationContext());
                sc.clearUser(getApplicationContext());
                //sc.clearSessionkeyServer(getApplicationContext());
            }
        });
>>>>>>> origin/betta
    }


}
