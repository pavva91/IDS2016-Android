package com.emergencyescape.businesslogic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

//utilizzare la liberia VOLLEY ci permette di non doverci preoccupare di
//creare thread a parte o gestire le risorse per la rete! FA TUTTO LUI!
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.emergencyescape.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

/**
 * Created by Betta73 on 10/05/2016.
 */
public class ServerConnection
{
    //classe singleton
    public static ServerConnection instance;
    private Context context;
    // variabile che uso se devo vedere se l'operazione è andata a buon fine o meno per continuare a fare coperazioni nell'activity
    private boolean result;
    RequestQueue coda;

    private ServerConnection(Context context)
    {
        this.context = context; // inizializzo la variabile
        coda = getRequestQueue(); //inizializzo la coda
    }

    //passo al singleton il contesto
    public static synchronized ServerConnection getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new ServerConnection(context);
        }

        return instance;
    }

    // inizializza la coda
    private RequestQueue getRequestQueue()
    {
        if (coda == null)
        {

            //InputStream keyStore = context.getResources().openRawResource(R.raw.keystoreprogettoandroid);
            //coda = Volley.newRequestQueue(context.getApplicationContext(),new ExtHttpClientStack(new SslHttpClient(keyStore, "123456789", 443)));
            coda = Volley.newRequestQueue(context.getApplicationContext());
        }
        return coda;
    }

    //aggiunge la richiesta alla coda delle richieste
    private <T> void addToRequestQueue(Request<T> req) {   getRequestQueue().add(req);    }

    private JSONObject createJsonObject(String u, String p)
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("username", u);
            jo.put("password", p);
        }
        catch (JSONException e) {   Log.v("ERRORE JSONOBJECT", e.getMessage());    }

        Log.v("CREAZIONE JSON", jo.toString());

        return jo;
    }


    /*
    *
    *       SEZIONE DEFINIZIONE URI
    *
    */

    private static final String GETELENCOMAPPE = "https://213.26.178.148/progetto/maps?token="; //aggiungi token
    private static final String GETMAPPABYNOME = "https://213.26.178.148/progetto/maps/";
    private static final String SENDREGISTRAZIONE = "http://213.26.178.148/progetto/users/"; //per inviare i dati della registrazioni (POST)
    private static final String SENDLOGIN = "http://213.26.178.148/progetto/users/";


    /*
    *
    *       SEZIONE COMPLETAMENTO URI
    *
    */

    protected String setElencoMappeUri(String token) {  return GETELENCOMAPPE+token;    }

    protected String setMappabyNomeUri(String token, String nomemapp) {   return GETMAPPABYNOME+nomemapp+"?token="+token; }

    protected String setLoginUri(String user, String psw) {   return SENDLOGIN+user+"/login?password="+psw;  }


    /*
    *
    *       SEZIONE FUNZIONE DI POST
    *
    */


    //da utilizzare quando vogliamo inviare idati al server per il login
    public boolean SendRegistraParameters(final String user, final String psw)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, //tipo richiesta
                SENDREGISTRAZIONE,//uri richiesta
                createJsonObject(user,psw),
                successListenerRegistrazione, //cosa fare in caso di risposta corretta
                errorListenerRegistrazione); //cosa fare in caso di risposta errata


        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

        Log.i("Registrazione", jsonObjReq.getBodyContentType());

        return result;
    }



    /*
    *
    *       SEZIONE FUNZIONE DI GET
    *
    */


    //da utilizzare quando vogliamo inviare idati al server per il login
    public boolean SendLoginParameters(final String user, final String psw)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, //tipo richiesta
                setLoginUri(user,psw),//uri richiesta
                successListenerLogin, //cosa fare in caso di risposta corretta
                errorListenerLogin); //cosa fare in caso di risposta errata

        Log.i("SLP", setLoginUri(user,psw));

        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

        return result;
    }



    /*
    *
    *       SEZIONE LISTENER SUCCESSO
    *
    */


    // listener generale d successo
    private Response.Listener<JSONObject> successGeneralListener = new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            Log.i("Comunicazione Server", "buon fine");
        }
    };

    //in caso di RISPOSTA CORRETTA al LOGIN riprendo dal file che mi rinvia il server la sessionkey server
    private Response.Listener<JSONObject> successListenerLogin = new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            Log.v("SLP", "ok");
            String token = null;

            try //riprendo il token che mi rimanda il server
            {
                Log.i("SLP", response.toString());
                token = response.getString("token");
                SessionClass sc = SessionClass.getInstance();
                sc.setSessionkeyserver(token, context); //setto il token nella classe di sessione
                result = true;
            }
            catch (JSONException e)
            {
                Log.i("SLP", e.getMessage());
                result = false;
            }

        }
    };

    //in caso di RISPOSTA CORRETTA al LOGIN riprendo dal file che mi rinvia il server la sessionkey server
    private Response.Listener<JSONObject> successListenerRegistrazione= new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            Log.v("SRP", "ok");
            String token = null;

            try //riprendo il token che mi rimanda il server
            {
                Log.i("SRP", response.toString());
                token = response.getString("token");
                SessionClass sc = SessionClass.getInstance();
                sc.setSessionkeyserver(token, context); //setto il token nella classe di sessione
                result = true;
            }
            catch (JSONException e)
            {
                Log.i("SRP", e.getMessage());
                result = false;
            }

        }
    };


    /*
    *
    *       SEZIONE LISTENER ERRORE
    *
    */


    // listener generale di errore
    private Response.ErrorListener errorGeneralListener = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError err)
        {
            Log.i("Comunicazione Server", err.getMessage());
        }
    };

    //cosa fare in caso di ERRORE LOGIN
    private Response.ErrorListener errorListenerLogin = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.statusCode == 403)
            {
                Toast.makeText(context, "ERRORE: username o password errati! ", Toast.LENGTH_LONG).show();
            }
            result = false;
        }
    };

    //cosa fare in caso di ERRORE REGISTRAZIONE
    private Response.ErrorListener errorListenerRegistrazione = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            Log.i("SRP", "errore");
            result = false;
        }
    };


}
