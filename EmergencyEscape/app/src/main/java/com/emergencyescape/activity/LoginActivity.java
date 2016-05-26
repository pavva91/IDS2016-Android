package com.emergencyescape.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.emergencyescape.R;

import java.util.ArrayList;

import com.emergencyescape.RegistraActivity;
import com.emergencyescape.SessionClass;
import com.emergencyescape.UtenteTable;
import com.emergencyescape.main.MainActivity;
import com.facebook.stetho.Stetho;


import java.util.ArrayList;

/**
 *
 */
public class LoginActivity extends AppCompatActivity
{
    UtenteTable ut;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_login);

        passaARegistra();
        setView();
        login();

    }


    private void passaARegistra()
    {
        TextView cambia =(TextView)findViewById(R.id.registra);

        cambia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // definisco l'intenzione
                Intent openPage1 = new Intent(LoginActivity.this, RegistraActivityOld.class);
                // passo all'attivazione dell'activity Pagina.java
                startActivity(openPage1);
            }
        });
    }

    //mi passa ad un'altra activity se l'utente ha fatto il login
    private void setView()
    {
        ut = new UtenteTable(getApplicationContext());
        //verifico se un utente è rimasto loggato
        ArrayList<String> ris = ut.checkSession();

        //se un utente è collegato passo all'altra pagina
        if(!ris.get(0).equals("nessuno"))
        {
            Intent openPage1 = new Intent(LoginActivity.this, MainActivity.class);
            // passo all'attivazione dell'activity Pagina.java
            startActivity(openPage1);
            //ripristino le variabili globali della sessione
            SessionClass sc = SessionClass.getInstance();
            sc.setUser(ris.get(0));
            sc.setSessionvalue(ris.get(1));

            Toast.makeText(getApplicationContext(),  getResources().getString(R.string.welcome_message)+ " " + sc.getUser(), Toast.LENGTH_LONG).show();
        }

    }

    private void login()
    {
        Button log = (Button) findViewById(R.id.loginbutton);
        final EditText psw = (EditText) findViewById(R.id.psw);
        final EditText user = (EditText) findViewById(R.id.user);
        final CheckBox ricorda = (CheckBox) findViewById(R.id.remember);

        ut = new UtenteTable(getApplicationContext());

        log.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ut.login(user.getText().toString(), psw.getText().toString()) != 0) {
                    Intent openPage1 = new Intent(LoginActivity.this, MainActivity.class);
                    // passo all'attivazione dell'activity Pagina.java
                    startActivity(openPage1);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.welcome_message)+ " " + user.getText().toString(), Toast.LENGTH_LONG).show();
                    SessionClass sc = SessionClass.getInstance();
                    // crea e setta la chiave di sessione se ricordami è selezionato
                    if (ricorda.isChecked() == true) {
                        ut.createSession(user.getText().toString(), psw.getText().toString());
                        //setto in una variabile globale la chiave di sessione
                        sc.setSessionvalue(ut.getSessionKey(user.getText().toString(), psw.getText().toString()));
                    }

                    // setto in una variabile globale lo username dell'utente loggato
                    sc.setUser(user.getText().toString());

                } else {
                    Toast.makeText(getApplicationContext(),  getResources().getString(R.string.error_message)+ ": " + getResources().getString(R.string.wrong_user_pass) , Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}

