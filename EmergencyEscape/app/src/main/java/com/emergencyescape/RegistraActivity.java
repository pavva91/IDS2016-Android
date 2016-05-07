package com.emergencyescape;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            public void onClick(View arg0) {
                long ok;

                UtenteTable ut = new UtenteTable(getApplicationContext());
                ok = ut.inserisciUtente(user.getText().toString(), psw.getText().toString());

                Log.i("Rowind nuovo", String.valueOf(ok));

                if (ok != -1) {
                    Toast.makeText(getApplicationContext(), "Registrazione avvenuta con successo!", Toast.LENGTH_LONG).show();
                    // definisco l'intenzione
                    Intent openPage1 = new Intent(RegistraActivity.this, LoginActivity.class);
                    // passo all'attivazione dell'activity Pagina.java
                    startActivity(openPage1);
                } else {
                    Toast.makeText(getApplicationContext(), "ERRORE: username già utilizzato!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
