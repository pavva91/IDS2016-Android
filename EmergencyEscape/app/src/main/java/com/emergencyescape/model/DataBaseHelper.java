package com.emergencyescape.model;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DataBaseHelper extends SQLiteOpenHelper
{

   private SQLiteDatabase myDataBase;
   private final Context myContext;
   private static final String DATABASE_NAME = "UNIVPMLabEscape.db";
   public static final String DATABASE_PATH = "/data/data/com.emergencyescape/databases/";
   public static final int DATABASE_VERSION = 1;
   //public static final int DATABASE_VERSION_old = 1;

   //Constructor
   public DataBaseHelper(Context context)
   {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
       this.myContext = context;
   }

    //Create a empty database on the system
    private void createDatabase() throws IOException
    {
        boolean dbExist = checkDataBase();
        if(dbExist)
        {
            Log.v("DB Exists", "db exists");
        }
        else
        {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try
            {
                this.close();
                copyDataBase();
                Log.v("DB copiato in createDb", "db copiato");
            }
            catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;

        try
        {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e)
        {
            //database does't exist yet.
        }

        if(checkDB != null)
        {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /*
    *  Copies your database from your local assets-folder to the just created empty database in the
    *  system folder, from where it can be accessed and handled.
    *  This is done by transfering bytestream.
    */
    private void copyDataBase() throws IOException
    {
       //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

       // Path to the just created empty db
       String outFileName = DATABASE_PATH + DATABASE_NAME;

       //Open the empty db as the output stream
       OutputStream myOutput = new FileOutputStream(outFileName);

       //transfer bytes from the inputfile to the outputfile
       byte[] buffer = new byte[1024];
       int length;
       while ((length = myInput.read(buffer))>0)
       {
            myOutput.write(buffer, 0, length);
       }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //delete database
    private void db_delete()
    {
        File file = new File(DATABASE_PATH);
        if(file.exists())
        {
            file.delete();
            Log.v("DB cancellato", "db cancellato");
        }
    }

    //Open database
    public void openDatabase() throws SQLException
    {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.v("DB Open", "db open");
    }

    public synchronized void closeDataBase()throws SQLException
    {
        if(myDataBase != null)
        {
            myDataBase.close();
        }
        super.close();
    }

    public void onCreate(SQLiteDatabase db)
    {
        //metodo che viene chiamato solo la prima volta che il database deve essere creato
        //qui dentro dovrebbero essere messere tutte le cose per creare le varie tabelle
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //metodo che serve, una volta crato il db, per apportare modifiche quali:
        //alert table, drop table, aggiungere nuove tabelle, aggiungere nuovi campi ad una tabella...
       if (newVersion > oldVersion)
       {
           Log.v("Database Upgrade", "Database version higher than old.");
           db_delete();
       }
    }

    //da fare in ogni metodo di query per verificare se db eiste ed aprirlo
    public void openDB()
    {
        try
        {
            this.createDatabase();
        }
        catch (IOException ioe)
        {
            throw new Error("Unable to create database");
        }
        try
        {
            this.openDatabase();
        }
        catch(SQLException sqle)
        {
            throw sqle;
        }
    }

    /* I metodi che si devono usare sono:
    *  - open db
    *
    */

}