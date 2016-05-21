package com.emergencyescape.utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.emergencyescape.R;

public class DestinationActivity extends CommonMenuActivity {

    TextView partenza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //visualizza aula partenza
        partenza = (TextView) findViewById(R.id.partenza);
        Intent intent = getIntent();
        String part = intent.getStringExtra("aula");
        String quota = intent.getStringExtra("quotap");
        partenza.setText("Aula di partenza: " + part + " quota " +quota);

           }

    //attività bottone invio aula destinazione
    public void sendDest(View view) {
        Intent intent = new Intent(DestinationActivity.this, ItineraryActivity.class);
        EditText editText = (EditText) findViewById(R.id.editDestinazione);
        String destinazione = editText.getText().toString();
        intent.putExtra("dest", destinazione);

        String partt = intent.getStringExtra("aula");
        String quotaa = intent.getStringExtra("quotap");
        intent.putExtra("partenza", partt);
        intent.putExtra("quota", quotaa);

        startActivity(intent);
    }


    //action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);



    }






}
