package com.emergencyescape.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.emergencyescape.MyApplication;
import com.emergencyescape.Server2Db;
import com.emergencyescape.businesslogic.ServerConnection;
import com.emergencyescape.businesslogic.SessionClass;
import com.emergencyescape.main.MainActivity;
import com.emergencyescape.model.UtenteTable;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Betta73 on 12/05/2016.
 */
public class LoginPresenter
{
    private int mInterval = 20000; // 1 minute by default, can be changed later
    private Handler mHandler;
    private String regid;
    private String SENDER_ID = "684028198613";
    GoogleCloudMessaging gcm;
    Context context;
    Server2Db s2d;


    public LoginPresenter(Context context)
    {
        mHandler = new Handler();
        this.context = context;
        s2d = new Server2Db();
        gcm = GoogleCloudMessaging.getInstance(context);
    }

    // task periodico che ogni tot va a verificare la connessione con il server
    Runnable mStatusChecker = new Runnable()
    {
        @Override
        public void run()
        {
            SessionClass sc = SessionClass.getInstance();
            ServerConnection sv = ServerConnection.getInstance(context);

            if (checkConnection() == false)
            {
                Log.i("Task Periodico", "Non siamo connessi, mi ripeto");
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
            else if(sc.getServerKey(context) != null && sc.isDownloadFlag() == true && sc.isRegistrationIDFlag()== true && sc.isDownloadMapFlag() == true) {
                Log.i("Task Periodico", "Abbiamo tutto; ho finito il lavoro; ci vediamo la prossima volta");
                stopRepeatingTask();
            }else if (checkConnection() == true && sc.isDownloadFlag() == false)
            {
                //chiedo utenti al server
                sv.getUsersList();
                Log.i("Task Periodico", "Siamo connessi. Ho chisto tabella utenti");
                mHandler.postDelayed(mStatusChecker, mInterval);
            }else if(checkConnection() == true && sc.isDownloadMapFlag() == false)
            {
                //chiedo le mappe al server
                s2d.setToken();
                sv.getElencoMappe(s2d.getToken());
                s2d.initializeDb();
                /*
                s2d.loadMaps();
                s2d.loadNodes();
                s2d.loadEdges();
                s2d.loadImages();
                */
                // Todo: lo faccio qua che s2d è un casino
                Log.i("Task Periodico", "Siamo connessi. Scarico le mappe...");
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
            else if (checkConnection() == true && sc.isRegistrationIDFlag() == false)
            {
                //prendo il registration id e lo invio al server
                String id = sc.getRegistrationId(context);
                sv.sendRegistrationID(id);
                Log.i("Task Periodico", "Siamo connessi. Sto inviando la chiave al server..."); // si blocca qua
                mHandler.postDelayed(mStatusChecker, mInterval);
            }/*
            else if (checkConnection() == true && sc.isDownloadMapFlag() == false)
            {
                //chiedo le mappe al server
                s2d.setToken();
                s2d.loadMaps();
                s2d.loadNodes();
                s2d.loadEdges();
                s2d.loadImages();
                sc.setDownloadMapFlag(MyApplication.context); // Todo: lo faccio qua che s2d è un casino
                Log.i("Task Periodico", "Siamo connessi. Scarico le mappe...");
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
            else if (checkConnection() == true && sc.isDownloadFlag() == false)
            {
                //chiedo utenti al server
                sv.getUsersList();
                Log.i("Task Periodico", "Siamo connessi. Ho chisto tabella utenti");
                mHandler.postDelayed(mStatusChecker, mInterval);
            }*/
            else if (checkConnection() == true && sc.getServerKey(context) == null)
            {
                if(sc.getUser(context) != null && sc.getPassword(context) != null)
                {
                    String user = sc.getUser(context);
                    String psw = sc.getPassword(context);
                    //chiedo la variabile di sessione
                    sv.getToken(user,psw);
                }
                Log.i("Task Periodico", "Siamo connessi. Ho chisto la chiave, mi ripeto poco più tardi");
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    public void startRepeatingTask()
    {
        mStatusChecker.run();
    }

    private void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mStatusChecker);
    }

    // verifica se il telefono è connesso alla rete (wifi o dati)
    public boolean checkConnection()
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return  isConnected;
    }

    //da eseguire quando il LOGIN è andato a buon fine
    public void loginOK(String u, String p, String r)
    {
        Intent openPage1 = new Intent(context, MainActivity.class);
        openPage1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(openPage1);

        Toast.makeText(context, "Bentornato " + u, Toast.LENGTH_LONG).show();

        SessionClass sc = SessionClass.getInstance();
        // crea e setta la chiave di sessione se ricordami è selezionato
        if (r.equals("true"))
        {
            UtenteTable ut = new UtenteTable(context);
            //setto in una variabile globale la chiave di sessione
            sc.setSessionKey(ut.createSession(u, p), context);
        }

        // setto in una variabile globale lo username dell'utente loggato
        sc.setUser(u, context);
    }

    //eseguito nel login offline per verificare la password
    public String cryptPassword(String password, String salt)
    {
        password=password.concat(salt);
        MessageDigest messageDigest;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte arrayDigest[] = messageDigest.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<arrayDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & arrayDigest[i]));
            password=hexString.toString();
        } catch (NoSuchAlgorithmException ex) { ex.printStackTrace(); }
        Log.i("Password", password);
        return password;
    }


    public void registerInBackground()
    {
        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... params)
            {
                String msg = "";
                try
                {
                    if (gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                }
                catch (IOException ex) {    return null;      }

                return regid;
            }

            @Override
            protected void onPostExecute(String regid)
            {
                if (regid!=null)
                {
                    //mi salvo la chiave da mandare
                    SessionClass sc = SessionClass.getInstance();
                    sc.setRegistrationId(regid, context);
                }
                else {  Log.i("Registrazione gcm", "errore");   }
            }

        }.execute();
    }



}
