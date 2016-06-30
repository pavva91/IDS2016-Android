package com.emergencyescape.businesslogic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.emergencyescape.ISO8601DateParser;
import com.emergencyescape.MyApplication;
import com.emergencyescape.R;
import com.emergencyescape.Server2Db;
import com.emergencyescape.greendao.Maps;
import com.emergencyescape.login.LoginActivity;
import com.emergencyescape.login.LoginPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

//utilizzare la liberia VOLLEY ci permette di non doverci preoccupare di
//creare thread a parte o gestire le risorse per la rete! FA TUTTO LUI!

/**
 * Created by Betta73 on 10/05/2016.
 */
public class ServerConnection
{
    private Server2Db server2Db = new Server2Db();
    private MyApplication myApplication = MyApplication.getInstance();
    //classe singleton
    public static ServerConnection instance;
    private Context context = MyApplication.context;
    // variabile che uso se devo vedere se l'operazione è andata a buon fine o meno per continuare a fare coperazioni nell'activity
    private boolean result;
    RequestQueue coda;
    String u;
    String p;
    String r;

    private ServerConnection(Context context)
    {
        this.context = MyApplication.context; // inizializzo la variabile
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

    // prendo i parametri passati al login
    private void setParam(String u, String p, String r)
    {
        this.u = u;
        this.p = p;
        this.r = r;
    }


    // inizializza la coda
    private RequestQueue getRequestQueue()
    {
        if (coda == null)
        {
            InputStream keyStore = context.getResources().openRawResource(R.raw.keystoreprogettoandroid);
            coda = Volley.newRequestQueue(context.getApplicationContext(),new ExtHttpClientStack(new SslHttpClient(keyStore, "123456789")));
            //da usare se non prende il certificato commentando le due qui sopra
            //coda = Volley.newRequestQueue(context.getApplicationContext());
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

    private JSONObject createJsonObject(String id)
    {
        JSONObject jo = new JSONObject();
        try
        {
            jo.put("registrationID", id);
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

    private static final String GETELENCOUTENTI = "http://213.26.178.148/progetto/users/list"; //aggiungi token
    private static final String SENDREGISTRAZIONE = "http://213.26.178.148/progetto/users/"; //per inviare i dati della registrazioni (POST)
    private static final String SENDLOGIN = "http://213.26.178.148/progetto/users/";
    private static final String SENDREGISTRATIONID = "http://213.26.178.148/progetto/devices/";
    private static final String SENDPOSITION = "http://213.26.178.148/progetto/users/";
    private static final String GETMAP = "http://213.26.178.148/progetto/maps";


    /*
    *
    *       SEZIONE COMPLETAMENTO URI
    *
    */


    protected String setLoginUri(String user, String psw) {   return SENDLOGIN+user+"/login?password="+psw;  }
    protected String setPositionUri(String user, String token) {   return SENDPOSITION+user+"/position?token="+token;  }
    protected String setMapNameUri(String mapName, String token){      return GETMAP + "/" + mapName + "?token="+token;    }
    protected String setMapListUri(String token){      return GETMAP + "?token="+token;    }

    /*
    *
    *       SEZIONE FUNZIONE DI POST
    *
    */

    public void updateUserPosition(final String nodeId, final String userName, final String token)
    {
        JSONObject jo = new JSONObject();

        try {    jo.put("position", nodeId);    }
        catch (Exception e){    System.out.println(e);    }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, //tipo richiesta
                setPositionUri(userName,token), //uri richiesta
                jo, //passo il jsonobject con i parametri
                successListenerPosition, //cosa fare in caso di risposta corretta
                errorListenerPosition); //cosa fare in caso di risposta errata

        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

        Log.i("Position", jsonObjReq.getBodyContentType());

    }

    //da utilizzare quando vogliamo inviare idati al server per la REGISTRAZIONE
    public void sendRegistraParameters(final String user, final String psw)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, //tipo richiesta
                SENDREGISTRAZIONE, //uri richiesta
                createJsonObject(user,psw), //passo il jsonobject con i parametri
                successListenerRegistrazione, //cosa fare in caso di risposta corretta
                errorListenerRegistrazione); //cosa fare in caso di risposta errata

        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

        Log.i("Registrazione", jsonObjReq.getBodyContentType());

    }

    //da utilizzare quando vogliamo inviare il registrationID per le notifiche push
    public void sendRegistrationID(final String id)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, //tipo richiesta
                SENDREGISTRATIONID, //uri richiesta
                createJsonObject(id), //passo il jsonobject con i parametri
                successListenerRegistrationID, //cosa fare in caso di risposta corretta
                errorListenerRegistrationID); //cosa fare in caso di risposta errata

        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

        Log.i("RegistrationID", jsonObjReq.getBodyContentType());

    }



    /*
    *
    *       SEZIONE FUNZIONE DI GET
    *
    */


    //da utilizzare quando vogliamo prendere i dati di una mappa dal nome della stessa
    public void getMapByName(String mapname, String token)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest (Request.Method.GET, //tipo richiesta
                setMapNameUri(mapname, token),//uri richiesta
                successListenergetMapByName, //cosa fare in caso di risposta corretta
                errorListenergetMapByName); //cosa fare in caso di risposta errata

        Log.i("getMapbynome", setMapNameUri(mapname, token));

        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

    }

    //da utilizzare quando vogliamo prendere le mappi disponibili sul server
    public void getElencoMappe(String token)
    {
        JsonArrayRequest jsonObjReq = new JsonArrayRequest (Request.Method.GET, //tipo richiesta
                setMapListUri(token),//uri richiesta
                successListenerElencoMappe, //cosa fare in caso di risposta corretta
                errorListenerListMap); //cosa fare in caso di risposta errata

        Log.i("getElencoMappe", setMapListUri(token));

        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

    }

    //da utilizzare quando vogliamo inviare idati al server per il LOGIN
    public void sendLoginParameters(final String user, final String psw, final String ricorda)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest (Request.Method.GET, //tipo richiesta
                setLoginUri(user,psw),//uri richiesta
                successListenerLogin, //cosa fare in caso di risposta corretta
                errorListenerLogin); //cosa fare in caso di risposta errata

        Log.i("SLP", setLoginUri(user,psw));

        setParam(user,psw,ricorda); //per riprenderli dopo nel login success

        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

    }

    //da utilizzare quando vogliamo ricevere solo il token doo che ci siamo autenticati offline
    public void getToken (final String user, final String psw)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest (Request.Method.GET, //tipo richiesta
                setLoginUri(user,psw),//uri richiesta
                successListenergetToken, //cosa fare in caso di risposta corretta
                errorListenerGetToken); //cosa fare in caso di risposta errata

        Log.i("Get Token", setLoginUri(user,psw));

        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

    }


    //da utilizzare quando vogliamo riprendere tutti gli utenti nel db
    public void getUsersList()
    {
        JsonArrayRequest jsonObjReq = new JsonArrayRequest (Request.Method.GET, //tipo richiesta
                GETELENCOUTENTI,//uri richiesta
                successListenerUtenti, //cosa fare in caso di risposta corretta
                errorListenerUtenti); //cosa fare in caso di risposta errata

        Log.i("Get Utenti", GETELENCOUTENTI);

        addToRequestQueue(jsonObjReq); // aggiungo la richiesta alla coda delle richieste

    }



    /*
    *
    *       SEZIONE LISTENER SUCCESSO
    *
    */


    //in caso di RISPOSTA CORRETTA all'invio della posizione
    private Response.Listener<JSONObject> successListenerPosition = new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            Log.v("Position", "ok");
            Log.v("Position", response.toString());
        }

    };

    //in caso di RISPOSTA CORRETTA al LOGIN riprendo dal file che mi rinvia il server la sessionkey server
    private Response.Listener<JSONObject> successListenerLogin = new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            Log.v("SLP", "ok");
            Log.v("SLP", response.toString());

            String token = null;
            try { token = response.getString("token");  }
            catch (JSONException e) {   e.printStackTrace();  }

            SessionClass sc = SessionClass.getInstance();
            sc.setServerKey(token, context); //setto il token nella classe di sessione
            LoginPresenter lp = new LoginPresenter(context);
            lp.loginOK(u, p, r);
        }

    };

    //in caso di RISPOSTA CORRETTA per PRENDERE IL TOKEN
    private Response.Listener<JSONObject> successListenergetToken = new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            Log.v("Preso Token", "ok");
            Log.v("Preso TOken", response.toString());

            String token = null;
            try { token = response.getString("token");  }
            catch (JSONException e) {   e.printStackTrace();  }

            SessionClass sc = SessionClass.getInstance();
            //setto il token nella classe di sessione
            sc.setServerKey(token, context);
            //cancello password da shared preferences
            sc.clearPassword(context);
        }

    };

    //in caso di RISPOSTA CORRETTA alla REGISTRAZIONE vedo se il server mi rimanda qualcosa
    private Response.Listener<JSONObject> successListenerRegistrazione= new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            Log.v("SRP", "ok");
            Log.i("SRP", response.toString());

            Toast.makeText(context, "Registrazione avvenuta con successo!", Toast.LENGTH_LONG).show();
            // definisco l'intenzione
            Intent openPage1 = new Intent(context, LoginActivity.class);
            openPage1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // passo all'attivazione dell'activity Pagina.java
            context.startActivity(openPage1);

        }
    };

    //in caso di RISPOSTA CORRETTA alla REGISTRAZIONEID
    private Response.Listener<JSONObject> successListenerRegistrationID= new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            //setto il flag per dire che abbiamo mandato il regID e non lo faremo più
            SessionClass sc = SessionClass.getInstance();
            sc.setRegistrationIdFlag(context);
            Log.v("RegistazionID", "ok");
        }
    };

    //in caso di RISPOSTA CORRETTA al DOWNLOAD UTENTI vedo se il server mi rimanda qualcosa
    private Response.Listener<JSONArray> successListenerUtenti= new Response.Listener<JSONArray>()
    {
        @Override
        public void onResponse(JSONArray response)
        {
            Log.v("Get Utenti", "ok");
            Log.i("Get Utenti", response.toString());

            if (response!= null)
                //server2Db.dropUserTable(); // Todo: reset table user
            {

                for (int i = 0; i < response.length(); i++)
                {
                    JSONObject jo = new JSONObject();
                    String user = null;
                    String salt = null;
                    String password = null;

                    try
                    {
                        jo = response.getJSONObject(i);
                        user = jo.getString("username");
                        password = jo.getString("password");
                        salt = jo.getString("salt");
                        /*
                        UtenteTable ut = new UtenteTable(context);

                        ut.inserisciUtente(user,salt,password);
*/
                        // TODO: Spostare da BETTA A valerio
                        server2Db.addUser(user,password,salt); // Funziona

                    }
                    catch (JSONException e) { e.printStackTrace(); }
                }

                SessionClass sc = SessionClass.getInstance();
                sc.setDownloadFlag(context);
            }
        }
    };

    //in caso di RISPOSTA CORRETTA quando si riprende l'elenco delle mappe
    private Response.Listener<JSONArray> successListenerElencoMappe= new Response.Listener<JSONArray>()
    {
        @Override
        public void onResponse(JSONArray response)
        {
            Log.v("Get Map Response", "ok");
            Log.i("Get Map Response", response.toString());

            if (response!= null)
            {
                server2Db.dropMapsTable();
                server2Db.loadMapsTable();

                for (int i = 0; i < response.length(); i++)
                {
                    JSONObject jo = new JSONObject();
                    String nomemappa = null;
                    String data_aggiornamento = null;

                    try
                    {
                        jo = response.getJSONObject(i);
                        nomemappa = jo.getString("name");
                        data_aggiornamento = jo.getString("lastUpdateMap");

                        //TODO: salva le stringhe nel db (tabella mappe)

                        server2Db.addMap(nomemappa,data_aggiornamento);
                        SessionClass sc = SessionClass.getInstance();
                        sc.setDownloadMapFlag(MyApplication.context);
                    }
                    catch (JSONException e) { e.printStackTrace(); }
                }

            }
        }
    };

    //in caso di RISPOSTA CORRETTA quando si riprende l'elenco delle mappe
    private Response.Listener<JSONObject> successListenergetMapByName= new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            Log.v("Get Map Response", "ok");
            Log.i("Get Map Response", response.toString());

            if (response!= null)
            {
                //riprendo l'array nodo
                JSONArray nodo = new JSONArray();
                try {    nodo = response.getJSONArray("nodes");    }
                catch (JSONException e) {  e.printStackTrace();     }

                //riprendo l'array arco
                JSONArray arco = new JSONArray();
                try {    arco = response.getJSONArray("edges");    }
                catch (JSONException e) {  e.printStackTrace();     }

                //ciclo sui nodi
                for (int i = 0; i < nodo.length(); i++)
                {
                    JSONObject jo = new JSONObject();

                    String nomemappa = null;
                    Long id = null;
                    String code = null;
                    String descr = null;
                    Integer quota = null;
                    Integer x = null;
                    Integer y = null;
                    Double width = null;
                    String type = null;

                    try
                    {
                        jo = nodo.getJSONObject(i);
                        nomemappa = jo.getString("mapName");
                        id = jo.getLong("id");
                        code = jo.getString("code");
                        descr = jo.getString("descr");
                        quota = jo.getInt("quota");
                        x = jo.getInt("x");
                        y = jo.getInt("y");
                        width = jo.getDouble("width");
                        type = jo.getString("type");

                        //TODO: salva le stringhe nel db (tabella nodi)

                    }
                    catch (JSONException e) { e.printStackTrace(); }
                }

                //ciclo sugli archi
                for (int z = 0; z < arco.length(); z++)
                {
                    JSONObject jo = new JSONObject();

                    String v = null;
                    String i = null;
                    String los = null;
                    String c = null;
                    String length = null;
                    String from = null;
                    String to = null;
                    String area = null;
                    String numpers = null;
                    String emgcost = null;

                    try
                    {
                        jo = arco.getJSONObject(z);
                        v = jo.getString("v");
                        i = jo.getString("i");
                        los = jo.getString("los");
                        c = jo.getString("c");
                        length = jo.getString("length");
                        from = jo.getString("from");
                        to = jo.getString("to");
                        area = jo.getString("area");
                        emgcost = jo.getString("emgcost");

                        //TODO: salva le stringhe nel db (tabella archi)

                    }
                    catch (JSONException e) { e.printStackTrace(); }
                }

            }
        }
    };


    /*
    *
    *       SEZIONE LISTENER ERRORE
    *
    */


    //cosa fare in caso di ERRORE POSIZIONE
    private Response.ErrorListener errorListenerPosition = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            Log.i("Position", "errore");
            if(error.getMessage() != null)
                Log.i("Position", error.getMessage());

        }
    };


    //cosa fare in caso di ERRORE LOGIN
    private Response.ErrorListener errorListenerLogin = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            Log.i("SLP", "errore");
            if(error.getMessage() != null)
                Log.i("SLP", error.getMessage());

            Toast.makeText(context, "ERRORE: username o password errati! ", Toast.LENGTH_LONG).show();
        }
    };

    //cosa fare in caso di ERRORE getToken)
    private Response.ErrorListener errorListenerGetToken = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            Log.i("Get Token", "errore");
            if(error.getMessage() != null)
                Log.i("SLP", error.getMessage());
        }
    };

    //cosa fare in caso di ERRORE REGISTRAZIONE
    private Response.ErrorListener errorListenerRegistrazione = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            Log.i("SRP", "errore");
            if(error.getMessage() != null)
                Log.i("SRP", error.getMessage());

            Toast.makeText(context, "ERRORE: username già utilizzato!", Toast.LENGTH_LONG).show();
        }
    };

    //cosa fare in caso di ERRORE GET ELENCO UTENTI
    private Response.ErrorListener errorListenerUtenti = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            Log.i("Get Utenti", "errore");
            if(error.getMessage() != null)
                Log.i("Get Utenti", error.getMessage());

        }
    };

    private Response.ErrorListener errorListenerListMap = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            Log.i("GETLISTMAP", "errore");
            if(error.getMessage() != null)
                Log.i("GETLISTMAP", error.getMessage());

        }
    };

    private Response.ErrorListener errorListenergetMapByName = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            Log.i("getMapByName", "errore");
            if(error.getMessage() != null)
                Log.i("getMapByName", error.getMessage());

        }
    };

    //cosa fare in caso di ERRORE SEND REGISTRATIONID
    private Response.ErrorListener errorListenerRegistrationID = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            Log.i("RegistrationID", "errore");
            if(error.getMessage() != null)
                Log.i("Registration ID", error.getMessage());

        }
    };


}
