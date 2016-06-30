package com.emergencyescape.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emergencyescape.MyApplication;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;
import com.emergencyescape.model.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Betta73 on 05/04/2016.
 *
 * Classe che serve per interagire con la tabella user del db
 */
public class UserUtil
{
    private static final String TABLENAME = "utente";
    private static final String DBCOLUMNUSER = "user";
    private static final String DBCOLUMNPSW = "psw";
    private static final String DBCOLUMNSALT = "salt";
    private DataBaseHelper dbh;

    private DaoSession daoSession = MyApplication.getSession();
    private UserDao userDao = daoSession.getUserDao();

    public UserUtil(Context context)
    {
        dbh = new DataBaseHelper(context);
    }
    Server2Db server2Db = new Server2Db();


    //verifica se l'utente è registrato
    public boolean checkUser (String userName, String psw)
    {
        boolean userExist = false;

        UserDao userDao = daoSession.getUserDao();
        List<User> userList = userDao.loadAll();

        for (User user : userList){
            if (user.getName().equals(userName) && user.getPassword().equals(psw)) {
                userExist = true;
                break;
            }
        }

        Log.i("Result checkUser", String.valueOf(userExist));
        return userExist;
    }


    //prende il salt dell'utente per il calcolo della password
    public String takeSalt (String userName) // ok, funziona
    {
        String salt = "not value";

        UserDao userDao = daoSession.getUserDao();
        List<User> userList = userDao.loadAll();

        for (User user : userList){
            if (user.getName().equals(userName)) {
                salt = user.getSalt();
                break;
            }
        }

        Log.i("Result takeSalt", String.valueOf(salt));
        return salt;
    }



    //viene fatto la prima volta dopo il login
    //setta la chiave di sessione come user+password
    public String createSession(String user, String psw)
    {

        String session = user+psw;
        return session;
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

