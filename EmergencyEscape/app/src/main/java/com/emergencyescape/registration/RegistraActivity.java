package com.emergencyescape.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emergencyescape.R;
import com.emergencyescape.businesslogic.ServerConnection;
import com.emergencyescape.model.UtenteTable;
import com.emergencyescape.login.LoginActivity;

public class RegistraActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);

        Registra();
    }


    private void Registra()
    {
        Button reg = (Button) findViewById(R.id.registrabutton);
        final EditText psw = (EditText) findViewById(R.id.pswregistra);
        final EditText user = (EditText) findViewById(R.id.userregistra);

        reg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                String u = user.getText().toString();
                String p = psw.getText().toString();

                ServerConnection scon = ServerConnection.getInstance(getApplicationContext());
                boolean ris = scon.SendRegistraParameters(u,p);

                if( ris == true)
                {
                    Toast.makeText(getApplicationContext(), "Registrazione avvenuta con successo!", Toast.LENGTH_LONG).show();
                    // definisco l'intenzione
                    Intent openPage1 = new Intent(RegistraActivity.this, LoginActivity.class);
                    // passo all'attivazione dell'activity Pagina.java
                    startActivity(openPage1);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "ERRORE: username già utilizzato!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registrazioneOffline()
    {

        long ok = -1;

        UtenteTable ut = new UtenteTable(getApplicationContext());
        //ok = ut.inserisciUtente(user.getText().toString(), psw.getText().toString());

        //Log.i("Rowind nuovo", String.valueOf(ok));

        if (ok != -1)
        {
            Toast.makeText(getApplicationContext(), "Registrazione avvenuta con successo!", Toast.LENGTH_LONG).show();
            // definisco l'intenzione
            Intent openPage1 = new Intent(RegistraActivity.this, LoginActivity.class);
            // passo all'attivazione dell'activity Pagina.java
            startActivity(openPage1);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "ERRORE: username già utilizzato!", Toast.LENGTH_LONG).show();
        }

    }



}
