package com.emergencyescape.noemtxt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import com.emergencyescape.R;
import com.emergencyescape.utility.CommonMenuActivity;
import com.emergencyescape.utility.DestinationActivity;

public class NoemTextActivity extends CommonMenuActivity {


    public void sendInfo(View view) {
        Intent intent = new Intent(this, DestinationActivity.class);
        EditText editText = (EditText) findViewById(R.id.editPartenza);
        String message = editText.getText().toString();
        intent.putExtra("aula", message);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noemtext);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //attività bottone
       /* Button button = (Button) findViewById(R.id.btnNoemPartenza);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(NoemTextActivity.this, DestinationActivity.class));
            }
        });*/



    }

    //action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);



    }





}
