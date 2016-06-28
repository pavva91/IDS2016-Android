package com.emergencyescape.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Betta73 on 05/04/2016.
 *
 * Classe che serve per interagire con la tabella user del db
 */
public class UtenteTable
{
    private static final String TABLENAME = "utente";
    private static final String DBCOLUMNUSER = "user";
    private static final String DBCOLUMNPSW = "psw";
    private static final String DBCOLUMNSALT = "salt";
    private DataBaseHelper dbh;


    public UtenteTable (Context context)
    {
        dbh = new DataBaseHelper(context);
    }

    //inserisce gli utentei passati dal db
    public long inserisciUtente (String user, String salt,String psw)
    {
         long ris;
         dbh.openDB();

        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBCOLUMNUSER, user);
        cv.put(DBCOLUMNPSW, psw);
        cv.put(DBCOLUMNSALT, salt);

        Log.i("Nome inserito", user);
        Log.i("Password inserita", psw);
        Log.i("Salt inserito", salt);

        ris = db.insert(TABLENAME, null, cv);

        dbh.close();

        return ris;
    }

    //verifica se l'utente è registrato
    public int checkUser (String user, String psw)
    {
        int ris = 0;
        dbh.openDB();

        SQLiteDatabase db = dbh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + DBCOLUMNUSER + ", " + DBCOLUMNPSW + " " +
                               "FROM " + TABLENAME + " " +
                               "WHERE " + DBCOLUMNUSER + " = '" + user+ "' " +
                               "AND " + DBCOLUMNPSW + " = '" + psw + "'", null);

        ris = c.getCount();

        Log.i("Result checkUser", String.valueOf(ris));
        dbh.close();
        return ris;
    }

    //prende il salt dell'utente per il calcolo della password
    public String takeSalt (String user)
    {
        String ris;
        dbh.openDB();

        SQLiteDatabase db = dbh.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + DBCOLUMNSALT + " " +
                "FROM " + TABLENAME + " " +
                "WHERE " + DBCOLUMNUSER + " = '" + user + "'", null);

        if(cursor.moveToFirst())
        {
            ris = cursor.getString(cursor.getColumnIndex(DBCOLUMNSALT));
        }
        else  { ris = "niente";  }

        Log.i("Result takeSalt", String.valueOf(ris));
        dbh.close();
        return ris;
    }

    //viene fatto la prima volta dopo il login
    //setta la chiave di sessione come user+password
    public String createSession(String user, String psw)
    {

        String session = user+psw;
        return session;
    }


    public ArrayList<ArrayList<String>> getAllFromUser() // TODO: Usare questa per copiare Tabella utenti
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

