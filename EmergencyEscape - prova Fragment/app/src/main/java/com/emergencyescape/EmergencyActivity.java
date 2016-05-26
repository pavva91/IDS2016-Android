package com.emergencyescape;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.emergencyescape.option.OptionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyActivity extends OptionActivity {


    @BindView (R.id.toolbar) Toolbar toolbar;
    /*  ButterKnife sostituisce:
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        dentro la OnCreate
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        if (savedInstanceState == null) {
            Fragment f = getFragment(); // setto il Fragment da visualizzare dentro l'Activity
            if (f == null) {
                Toast.makeText(this, "Error: No fragment specified", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, f).commit(); // Lancio il Fragment
            }
        }
    }

        //attività bottone

    /*  // TODO: Dove delego ora gli @OnClick??
        Button button = (Button) findViewById(R.id.btnPartenza);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: Richiamare funzione del presenter dedicato per delegare la business logic.
                startActivity(new Intent(EmergencyActivity.this, ItineraryActivity.class));
            }
        });


    }
    */

    //action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }


    private Fragment getFragment() {   // Seleziono il fragment appropriato
        String fragmentName = getIntent().getStringExtra("fragment");
        if (fragmentName == null) {
            return null;
        }

        if ("DepartureTextFragment".equals(fragmentName)) {
            return new DepartureTextFragment();
        }

        if ("DepartureTapFragment".equals(fragmentName)){
            return new DepartureTapFragment();
        }

        if ("DepartureQrFragment".equals(fragmentName)){
            return new DepartureQrFragment();
        }

        getSupportActionBar().setTitle(fragmentName);

        return null;
    }




}
