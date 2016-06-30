package com.emergencyescape.businesslogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Betta73 on 28/04/2016.
 *
 * Classe che contiene i parametri della sessione
 *
 */
public class SessionClass
{
    public static SessionClass instance;

    private SessionClass(){}

    public static synchronized SessionClass getInstance()
    {
        if (instance == null)
        {
            instance = new SessionClass();
        }

        return instance;
    }


    /*
    *
    *   creiamo distinzione tra la chiave di sessione usata dal server per comunicare con noi
    *   e quella usata dall'app per vedere se effettuare o meno il login
    *
    */


    private static final String FILENAME = "SessionParameters" ;
    private static final String USER = "username";
    private static final String PASSWORD = "password";
    private static final String DOWNLOADFLAG = "downloadflag";
    private static final String REGISTRATIONIDFLAG = "registrationidflag";
    private static final String REGISTRATIONID = "registrationid";
    private static final String SERVERKEY = "sessionkeyserver";
    private static final String SESSIONKEY = "restaconnesso";
    private static final String DOWNLOADMAPFLAG = "restaconnesson";
    private static final String DATE = "lastupdate";
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    /*
    *
    *       SEZIONE SETTER
    *
    */

    // setta la data di ultimo aggiornamento mappe
    public void setDate(String date, Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(DATE, date);
        editor.commit();
        Log.i("DATE globale", "inserito");
    }

    // setta lo username dell'utente loggato in un file chiamato FILENAME
    public void setRegistrationId(String regid, Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(REGISTRATIONID, regid);
        editor.commit();
        Log.i("RegID globale", "inserito");
    }

    // setta lo username dell'utente loggato in un file chiamato FILENAME
    public void setUser(String user, Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(USER, user);
        editor.commit();
        Log.i("User globale", "inserito");
    }

    // setta la password dell'utente loggato quando il login avviene in modalità offline
    public void setPassword(String psw, Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(PASSWORD, psw);
        editor.commit();
        Log.i("Password globale", "inserito");
    }

    // setta la sessionkey che ci serve per comunicare con il server
    public void setServerKey(String sessionkeyserver, Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(SERVERKEY, sessionkeyserver);
        editor.commit();
        Log.i("Sessionkey Server", "inserito");
    }

    // setta il valore della chiave di sessione
    public void setSessionKey(String restaconnesso, Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(SESSIONKEY, restaconnesso);
        editor.commit();
        Log.i("Sessionkey globale", "inserita");
    }

    // setta il flag per vedere se devo o meno scaricare l'elenco utenti
    public void setDownloadFlag(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt(DOWNLOADFLAG, 1);
        editor.commit();
        Log.i("Flag globale", "inserito");
    }

    // setta il flag per vedere se devo o meno scaricare l'elenco utenti
    public void setRegistrationIdFlag(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt(REGISTRATIONIDFLAG, 1);
        editor.commit();
        Log.i("RegIDFLAG globale", "inserito");
    }

    // setta il flag per vedere se devo o meno scaricare l'elenco utenti
    public void setDownloadMapFlag(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt(DOWNLOADMAPFLAG, 1);
        editor.commit();
        Log.i("MapDownloadFlag", "inserito");
    }



    /*
    *
    *       SEZIONE IS IN SHARED PREFERENCES
    *
    */


    public boolean isRegistrationIDFlag(){ return sp.contains(REGISTRATIONIDFLAG);}
    public boolean isDownloadFlag(){ return sp.contains(DOWNLOADFLAG);}
    public boolean isDownloadMapFlag(){ return sp.contains(DOWNLOADMAPFLAG);}
    public boolean isDate(){ return sp.contains(DATE);}



    /*
    *
    *       SEZIONE GETTER
    *
    */

    // ritorna lo username dell'utente loggato
    public String getDate (Context x)
    {
        sp = x.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(DATE, null);
    }

    // ritorna lo username dell'utente loggato
    public String getUser (Context x)
    {
        sp = x.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(USER, null);
    }

    // ritorna il valore della chiave di sessione inviata dal server
    public String getServerKey(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(SERVERKEY, null);
    }

    // ritorna il valore della sessione
    public String getSessionKey(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(SESSIONKEY, null);
    }

    // ritorna il valore della password
    public String getPassword(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(PASSWORD, null);
    }

    // ritorna il valore della password
    public String getRegistrationId(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(REGISTRATIONID, null);
    }


    /*
    *
    *       SEZIONE CLEAR
    *
    */


    // dopo il log out ripulisce la variebile dell'username
    public void clearUser(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.remove(USER);
        editor.commit();
        Log.i("User globale", "cancellato");
    }

    // dopo il log out ripulisce la variebile della sessionkeyserver
    public void clearServerKey(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.remove(SERVERKEY);
        editor.commit();
        Log.i("Sessionkey Server", "cancellato");
    }

    // dopo il log out ripulisce la variebile della chiave di sessione
    public void clearSessionKey(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.remove(SESSIONKEY);
        editor.commit();
        Log.i("SessionKey globale", "cancellata");
    }

    // dopo che ho ottenuto l'elenco utenti e faccio il logout oppure chiudo la app lo resetto per il prossimo aggiornamento
    public void clearDownloadFlag(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.remove(DOWNLOADFLAG);
        editor.commit();
        Log.i("Falg globale", "cancellata");
    }

    // dopo che ho ottenuto il token dal server cancello la psw
    public void clearPassword(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.remove(PASSWORD);
        editor.commit();
        Log.i("Password globale", "cancellata");
    }

}
