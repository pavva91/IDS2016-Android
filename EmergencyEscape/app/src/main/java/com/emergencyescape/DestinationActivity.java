package com.emergencyescape;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DestinationActivity extends CommonMenuActivity {

    TextView partenza;
    //TextView quotap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //visualizza quota partenza
       /* quotap = (TextView) findViewById(R.id.quotap);
        Intent intento = getIntent();
        String quotap = intento.getStringExtra("quotap");
        partenza.setText("Quota di partenza: " + quotap);*/

        String quotap = getIntent().getExtras().getString("quotap");
        System.out.println("quota " + String.valueOf(quotap));

        //visualizza aula partenza
        partenza = (TextView) findViewById(R.id.partenza);
        Intent intent = getIntent();
        String part = intent.getStringExtra("aula");
        partenza.setText("Aula di partenza: " + part);

        //attività bottone
        Button button = (Button) findViewById(R.id.btnDestinazione);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DestinationActivity.this, ItineraryActivity.class));
            }
        });

           }

    //action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);



    }






}
