package com.emergencyescape.login;

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

import com.emergencyescape.MyApplication;
import com.emergencyescape.R;
import com.emergencyescape.businesslogic.ServerConnection;
import com.emergencyescape.businesslogic.SessionClass;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.EdgeDao;
import com.emergencyescape.greendao.MapsDao;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;
import com.emergencyescape.main.MainActivity;
import com.emergencyescape.model.UtenteTable;
import com.emergencyescape.registration.RegistraActivity;
import com.facebook.stetho.Stetho;

import java.util.List;

/**
 *
 */
public class LoginActivity extends AppCompatActivity
{
    UtenteTable ut;
    LoginPresenter lp;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_login);
        lp = new LoginPresenter(getApplicationContext());

        passaARegistra();
        setView();
        login();

        //cancello il flag del download se c'è così lo possiamo rifare
        SessionClass sc = SessionClass.getInstance();
        sc.clearDownloadFlag(getApplicationContext());

        LoginPresenter lp = new LoginPresenter(getApplicationContext());
        //se non ho ancora la chiave, mi registo
        if (sc.getRegistrationId(getApplicationContext()) == null)
        {
            lp.registerInBackground();
        }
        //task periodico che termina quando abbiamo inviato regid, preso il token e presa la lista utenti
        lp.startRepeatingTask();


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
                if (lp.checkConnection() == true) //se il telefono è connesso gli do la possibilità di farlo
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
        String ris = sc.getSessionKey(getApplicationContext());

        //se un utente è collegato passo all'altra pagina
        if(ris != null)
        {
            Intent openPage1 = new Intent(LoginActivity.this, MainActivity.class);
            // passo all'attivazione dell'activity Pagina.java
            startActivity(openPage1);
            //NOTA: non devo resettare le variabili perchè vuol dire che il file non è stato ripulito dal logout
            Toast.makeText(getApplicationContext(), "Bentornato " + sc.getUser(getApplicationContext()), Toast.LENGTH_LONG).show();
        }
        else
        {
            //se quindi non hai il resta connesso dobbiamo resettare tutte le shared preference
            //user, password, download per lista utenti, token server
            //quelli del registration id non li devi toccare perchè lo deve fare solo la prima volta che l'app è installata
            sc.clearDownloadFlag(getApplicationContext());
            sc.clearPassword(getApplicationContext());
            sc.clearServerKey(getApplicationContext());
            sc.clearUser(getApplicationContext());
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
                String userNameText = user.getText().toString();
                String passwordText = psw.getText().toString();
                String rememberLogin = String.valueOf(ricorda.isChecked());

                if (userNameText.equals("") || passwordText.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "I campi Username e Password sono obbligatori", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(lp.checkConnection()==true) //siamo online
                    {
                        ServerConnection scon = ServerConnection.getInstance(getApplicationContext());
                        scon.sendLoginParameters(userNameText, passwordText, rememberLogin);
                    }
                    else // siamo offline
                    {
                        ut = new UtenteTable(getApplicationContext());

                        //riprendo il salt dell'utente inserito
                        String salt = ut.takeSalt(userNameText);
                        if (salt.equals("niente"))
                        {
                            Toast.makeText(getApplicationContext(), "ERRORE: username o password errati! ", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            //cripto la password
                            String criptopsw = lp.cryptPassword(passwordText, salt);
                            //se l'utente esiste
                            if (ut.checkUser(userNameText, criptopsw) == true)
                            {
                                SessionClass sc = SessionClass.getInstance();
                                //salvo la password da inviare appena siamo online al server
                                sc.setPassword(passwordText, getApplicationContext());

                                lp.loginOK(userNameText, passwordText, rememberLogin);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "ERRORE: username o password errati! ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
               }
            }
        });
    }



}

