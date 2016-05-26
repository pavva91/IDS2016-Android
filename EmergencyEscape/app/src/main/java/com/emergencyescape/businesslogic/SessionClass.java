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
    private static final String SESSIONKEY = "sessionkeyserver";
    private static final String RESTACONNESSO = "restaconnesso";
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    /*
    *
    *       SEZIONE SETTER
    *
    */


    // setta lo username dell'utente loggato in un file chiamato FILENAME
    public void setUser(String user, Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(USER, user);
        editor.commit();
        Log.i("User globale", "inserito");
    }

    // setta la sessionkey che ci serve per comunicare con il server
    public void setSessionkeyserver(String sessionkeyserver, Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(SESSIONKEY, sessionkeyserver);
        editor.commit();
        Log.i("Sessionkey Server", "inserito");
    }

    // setta il valore della chiave di sessione
    public void setRestaconnesso(String restaconnesso, Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString(RESTACONNESSO, restaconnesso);
        editor.commit();
        Log.i("Sessionkey globale", "inserita");
    }


    /*
    *
    *       SEZIONE GETTER
    *
    */


    // ritorna lo username dell'utente loggato
    public String getUser (Context x)
    {
        sp = x.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(USER, null);
    }

    // ritorna il valore della chiave di sessione inviata dal server
    public String getSessionkeyserver(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(SESSIONKEY, null);
    }

    // ritorna il valore della sessione
    public String getRestaconnesso(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(RESTACONNESSO, null);
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
    public void clearSessionkeyServer(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.remove(SESSIONKEY);
        editor.commit();
        Log.i("Sessionkey Server", "cancellato");
    }

    // dopo il log out ripulisce la variebile della chiave di sessione
    public void clearRestaconnesso(Context x)
    {
        sp = x.getSharedPreferences(FILENAME, x.MODE_PRIVATE);
        editor = sp.edit();
        editor.remove(RESTACONNESSO);
        editor.commit();
        Log.i("SessionKey globale", "cancellata");
    }


}
