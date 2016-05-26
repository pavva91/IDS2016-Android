package com.emergencyescape.login;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emergencyescape.R;
import com.emergencyescape.registration.RegistraActivity;
import com.emergencyescape.businesslogic.ServerConnection;
import com.emergencyescape.businesslogic.SessionClass;
import com.emergencyescape.model.UtenteTable;
import com.emergencyescape.main.MainActivity;

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
        setContentView(R.layout.activity_login);


        passaARegistra();
        setView();
        login();

        LoginPresenter lp = new LoginPresenter(getApplicationContext());
        lp.startRepeatingTask();
    }


    @Override
    // cosa fare quando l'acitivity viene distrutta
    protected void onStop()
    {
        super.onStop();

        //vedere che se non è settato ricordami del login e quindi se nel file shared preference non
        //resta connesso quando sto chiudendo l'app devo eliminare la chiave di sessione inviata dal server
        //e comunicarlo al server stesso

    }

    // cliccando sull'apposito bottone mi passa dal login al registra
    private void passaARegistra()
    {
        TextView cambia =(TextView)findViewById(R.id.registra);

        cambia.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (checkConnection() == true) //se il telefono è connesso gli do la possibilità di farlo
                {
                     // definisco l'intenzione
                    Intent openPage1 = new Intent(LoginActivity.this, RegistraActivity.class);
                     // passo all'attivazione dell'activity Pagina.java
                      startActivity(openPage1);
                }
                else //altrimenti non può neanche andare alla pagina
                {
                    Toast.makeText(getApplicationContext(), "Registrazione possibile solo se il dispositivo è connesso", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // sceglie l'activity da visualizzare al lancio dell'app
    private void setView()
    {
        SessionClass sc = SessionClass.getInstance();

        String ris = sc.getRestaconnesso(getApplicationContext());

        //se un utente è collegato passo all'altra pagina
        if(ris != null)
        {
            Intent openPage1 = new Intent(LoginActivity.this, MainActivity.class);
            // passo all'attivazione dell'activity Pagina.java
            startActivity(openPage1);
            //NOTA: non devo resettare le variabili perchè vuol dire che il file non è stato ripulito dal logout
            Toast.makeText(getApplicationContext(), "Bentornato " + sc.getUser(getApplicationContext()), Toast.LENGTH_LONG).show();
        }

    }

    private void login()
    {
        Button log = (Button) findViewById(R.id.loginbutton);
        final EditText psw = (EditText) findViewById(R.id.psw);
        final EditText user = (EditText) findViewById(R.id.user);
        final CheckBox ricorda = (CheckBox) findViewById(R.id.remember);

        log.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String u = user.getText().toString();
                String p = psw.getText().toString();
                String r = String.valueOf(ricorda.isChecked());

               if(checkConnection()==true) //siamo online
               {
                   ServerConnection scon = ServerConnection.getInstance(getApplicationContext());

                    boolean ris = scon.SendLoginParameters(u, p);

                   Log.i("SLP", String.valueOf(ris));

                   if (ris == true)
                   {
                       loginOK(u,p,r);
                   }
               }
               else // siamo offline
               {
                   ut = new UtenteTable(getApplicationContext());
                   //se l'utente esiste
                   if (ut.checkUser(u,p) != 0)
                   {
                       loginOK(u,p,r);
                   }
                   else{ Toast.makeText(getApplicationContext(), "ERRORE: username o password errati! ", Toast.LENGTH_LONG).show();}

               }
            }
        });
    }

    private void loginOK(String u, String p, String r)
    {
        Intent openPage1 = new Intent(LoginActivity.this, MainActivity.class);
        // passo all'attivazione dell'activity Pagina.java
        startActivity(openPage1);
        Toast.makeText(getApplicationContext(), "Bentornato " + u, Toast.LENGTH_LONG).show();
        SessionClass sc = SessionClass.getInstance();
        // crea e setta la chiave di sessione se ricordami è selezionato
        if (r.equals("true"))
        {
            //setto in una variabile globale la chiave di sessione
            sc.setRestaconnesso(ut.createSession(u, p), getApplicationContext());
        }

        // setto in una variabile globale lo username dell'utente loggato
        sc.setUser(u, getApplicationContext());
    }

    // verifica se il telefono è connesso alla rete (wifi o dati)
    private boolean checkConnection()
    {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        Log.i("is Connect", String.valueOf(isConnected));

        return  isConnected;
    }


}

