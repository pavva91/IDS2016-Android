package com.emergencyescape;

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
    private String sessionvalue;
    private String user;

    private SessionClass(){}

    public static synchronized SessionClass getInstance()
    {
        if (instance == null)
        {
            instance = new SessionClass();
        }

        return instance;
    }

    // setta lo username dell'utente loggato
    public void setUser(String user)
    {
        this.user = user;
        Log.i("User globale", "inserito");
    }

    // setta il valore della chiave di sessione
    public void setSessionvalue(String sessionvalue)
    {
        this.sessionvalue = sessionvalue;
        Log.i("Sessionkey globale", "inserita");
    }

    // ritorna lo username dell'utente loggato
    public String getUser ()
    {
        return this.user;
    }

    // ritorna il valore della sessione
    public String getSessionvalue ()
    {
        return this.sessionvalue;
    }

    // dopo il log out ripulisce la variebile dell'username
    public void clearUser()
    {
        this.user = null;
        Log.i("User globale", "cancellato");
    }

    // dopo il log out ripulisce la variebile della chiave di sessione
    public void clearSessionvalue()
    {
        this.sessionvalue = null;
        Log.i("SessionKey globale", "cancellata");
    }
}
