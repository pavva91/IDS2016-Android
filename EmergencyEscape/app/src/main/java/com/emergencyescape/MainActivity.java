package com.emergencyescape;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.emergencyescape.utility.CommonMenuActivity;


public class MainActivity extends CommonMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        logout();
    }

//action bar
    public boolean onCreateOptionsMenu(Menu menu) {
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
                //canella dal db la session key
                UtenteTable ut = new UtenteTable(getApplicationContext());
                ut.destroySession(sc.getSessionvalue());

                Toast.makeText(getApplicationContext(), "A presto " + sc.getUser() + "!", Toast.LENGTH_LONG).show();

                //pulisce le variabili globali
                sc.clearSessionvalue();
                sc.clearSessionvalue();
            }
        });
    }


}
