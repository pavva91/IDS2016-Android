package com.emergencyescape.login;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.emergencyescape.businesslogic.SessionClass;

/**
 * Created by Betta73 on 12/05/2016.
 */
public class LoginPresenter
{
    private int mInterval = 10000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    Context context;


    public LoginPresenter(Context context)
    {
        mHandler = new Handler();
        this.context = context;
    }

    // task periodico che ogni tot va a verificare la connessione con il server
    Runnable mStatusChecker = new Runnable()
    {
        @Override
        public void run()
        {
            SessionClass sc = SessionClass.getInstance();

            if (checkConnection() == false)
            {
                mHandler.postDelayed(mStatusChecker, mInterval);
                Log.i("Task Periodico", "Non siamo connessi, mi ripeto");//10 sec
            }
            else if (checkConnection() == true && sc.getRestaconnesso(context) != null)
            {
                //chiedo aggiornamenti server
                //riprendi valori mappa
                //aumento il delay senza modificare la variabile
                Log.i("Task Periodico", "Siamo connessi. Ho chisto aggiornamenti, mi ripeto più tardi");
                mHandler.postDelayed(mStatusChecker, mInterval+10000);//20 sec

            }
            else if (checkConnection() == true && sc.getSessionkeyserver(context) == null)
            {
                //chiedo la variabile di sessione
                Log.i("Task Periodico", "Siamo connessi. Ho chisto la chiave, mi ripeto poco più tardi");
                mHandler.postDelayed(mStatusChecker, mInterval+5000);//15 sec
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
    private boolean checkConnection()
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        Log.i("is Connect", String.valueOf(isConnected));

        return  isConnected;
    }
}
