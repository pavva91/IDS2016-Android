package com.emergencyescape.mymodel.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emergencyescape.mymodel.DataBaseHelper;

import java.util.ArrayList;

/**
 * Created by Betta73 on 05/04/2016.
 *
 * Classe che serve per interagire con la tabella user del db
 */
public class User
{
    private static final String TABLENAME = "utente";
    private static final String DBCOLUMNUSER = "user";
    private static final String DBCOLUMNPSW = "psw";
    private static final String DBCOLUMNSESSIONDEFAULTVALUE = "0";
    private static final String DBCOLUMNSESSION = "session";
    private DataBaseHelper dbh;


    public User(Context context)
    {
        dbh = new DataBaseHelper(context);
    }

    //inserisce l'utente
    public long inserisciUtente (String user, String psw)
    {
         long ris;
         dbh.openDB();

        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBCOLUMNUSER, user);
        cv.put(DBCOLUMNPSW, psw);

        Log.i("Nome inserito", user);
        Log.i("Password inserita", psw);

        ris = db.insert(TABLENAME, null, cv);

        dbh.close();

        return ris;
    }

    //verifica se l'utente è registrato
    public int login (String user, String psw)
    {
        int ris = 0;
        dbh.openDB();

        SQLiteDatabase db = dbh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + DBCOLUMNUSER + ", " + DBCOLUMNPSW + " " +
                               "FROM " + TABLENAME + " " +
                               "WHERE " + DBCOLUMNUSER + " = '" + user+ "' " +
                               "AND " + DBCOLUMNPSW + " = '" + psw + "'", null);

        ris = c.getCount();
        Log.i("Result login", String.valueOf(ris));
        dbh.close();
        return ris;
    }

    //viene fatto la prima volta dopo il login
    //setta la chiave di sessione come user+password
    public int createSession(String user, String psw)
    {
        int ris = 0;
        dbh.openDB();
        String session = user+psw;
        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBCOLUMNSESSION, session);
        String [] args = {user, psw};
        ris = db.update(TABLENAME,cv, DBCOLUMNUSER + "=? AND " + DBCOLUMNPSW +"=?", args);

        return ris;
    }

    //verifico se c'è un utente nella tabella con la session != 0
    //restituisce lo user (1a posizione array) e la chiave(2a poszione array)
    // di sessione dell'utente loggato
    //altrimenti la stringa nessuno
    public ArrayList<String> checkSession ()
    {
        ArrayList<String> ris = new ArrayList<String>();

        //valori di dafault
        ris.add("nessuno");
        ris.add("0");

        int n;
        dbh.openDB();

        SQLiteDatabase db = dbh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + DBCOLUMNUSER + ", " + DBCOLUMNSESSION + " " +
                               "FROM " + TABLENAME + " " +
                               "WHERE " + DBCOLUMNSESSION + " NOT LIKE '%0%'", null);

        n = c.getCount();

        // se esiste sostituisco i valori di default con quelli della query
        if(c.moveToFirst())
        {
            ris.set(0, c.getString(0));
            ris.set(1, c.getString(1));
        }

        Log.v("Risultato", String.valueOf(n));
        dbh.close();
        return ris;
    }

    //riprende la chiave di sessione appena generata
    public String getSessionKey (String user, String psw)
    {
        String ris = null;
        int n;
        dbh.openDB();

        SQLiteDatabase db = dbh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + DBCOLUMNSESSION + " " +
                               "FROM " + TABLENAME + " " +
                               "WHERE " + DBCOLUMNUSER + " = '" + user+ "' " +
                               "AND " + DBCOLUMNPSW + " = '" + psw + "'", null);

        if(c.moveToFirst())
        {
            ris = c.getString(0);
        }

        Log.v("Session key", ris);
        dbh.close();
        return ris;
    }

    //viene fatto durante il logout
    //rimette il campo session al valore di default cioè 0
    public int destroySession(String session)
    {
        int ris = 0;
        dbh.openDB();
        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBCOLUMNSESSION, DBCOLUMNSESSIONDEFAULTVALUE);
        String [] args = {session};
        ris = db.update(TABLENAME,cv, DBCOLUMNSESSION + "=?", args);

        Log.i("Destorykey", String.valueOf(ris));

        return ris;
    }

    public ArrayList<ArrayList<String>> getAllFromUser()
    {
        dbh.openDB();
        SQLiteDatabase db = dbh.getReadableDatabase();

        ArrayList<ArrayList<String>> ris = new ArrayList<ArrayList<String>>();

        ArrayList <String> nome = new ArrayList<String>();
        ArrayList <String> ps = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLENAME, null);

        //serve per prendere tutti i dati provenienti da una query  0 = 1a colonna
        if(c.moveToFirst())
        {
            while (c.isAfterLast() == false)
            {
                nome.add(c.getString(0));
                ps.add(c.getString(1));

                c.moveToNext();
            }
        }

        int listSize = nome.size();
        int listSize1 = ps.size();

        //mi permette di creare un log per ogni elemento dell' arraylist
        for (int i = 0; i<listSize; i++)
        {
            Log.i("name: ", nome.get(i));
        }
        for (int i = 0; i<listSize1; i++)
        {
            Log.i("psw: ", ps.get(i));
        }

        ris.add(nome);
        ris.add(ps);

        return ris;

    }
}

