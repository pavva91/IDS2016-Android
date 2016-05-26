package com.emergencyescape.noemtap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.emergencyescape.utility.CommonMenuActivity;
import com.emergencyescape.R;


public class NoemTapActivity extends CommonMenuActivity {


      public void onClick(View v)
    {

        int id = v.getId();

        if (id == R.id.q145)
        {
            Intent in = new Intent(this, MapTapActivity.class);
            in.putExtra("id", R.drawable.q145);
            in.putExtra("quota", "q145");
            startActivity(in);
        }

        if (id == R.id.q150)
        {
            Intent in = new Intent(this, MapTapActivity.class);
            in.putExtra("id", R.drawable.q150);
            in.putExtra("quota", "q150");
            startActivity(in);
        }

        if (id == R.id.q155)
        {
            Intent in = new Intent(this, MapTapActivity.class);
            in.putExtra("id", R.drawable.q155);
            in.putExtra("quota", "q155");
            startActivity(in);
        }

    }


    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noemtap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


}


    //action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


}