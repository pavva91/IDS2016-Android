package com.emergencyescape.server;


import android.support.v4.util.LruCache;


import com.emergencyescape.server.model.MapResponse;
import com.emergencyescape.server.model.MapsResponse;
import com.emergencyescape.server.model.Node;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cteegarden on 1/25/16.
 * Questa è la classe che implementa la logica di interazione col server (polling ecc...)
 */

public class ServerService { // TODO: Per ora è grezzo e non sfrutta il caching, ogni volta esegue la get al server

    private static String baseUrl ="http://213.26.178.148/progetto/"; // Server URL
    private ServerAPI serverAPI; // Retrofit Callers Interface
    private OkHttpClient okHttpClient;
    private LruCache<Class<?>, Observable<?>> apiObservables;

    public ServerService(){
        this(baseUrl);
    } // Il costruttore senza parametri richiama quello col parametro

    public ServerService(String baseUrl){
        okHttpClient = buildClient();
        apiObservables = new LruCache<>(10);

        // Creo collegamento al server
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        serverAPI = retrofit.create(ServerAPI.class);
    }

    /**
     * Method to return the API interface.
     * @return
     */
    public ServerAPI getAPI(){
        return serverAPI;
    }


    /**
     * Method to build and return an OkHttpClient so we can set/get
     * headers quickly and efficiently.
     * @return
     */
    public OkHttpClient buildClient(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                // Do anything with response here
                //if we ant to grab a specific cookie or something..
                return response;
            }
        });

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //this is where we will add whatever we want to our request headers.
                Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
                return chain.proceed(request);
            }
        });

        return  builder.build();
    }

    /**
     * Method to clear the entire cache of observables
     */
    public void clearCache(){
        apiObservables.evictAll();
    }


    /**
     * Method to either return a cached observable or prepare a new one.
     *
     * @param unPreparedObservable
     * @param clazz
     * @param cacheObservable
     * @param useCache
     * @return Observable ready to be subscribed to
     */
    public Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache){

        Observable<?> preparedObservable = null;



        if(useCache)//this way we don't reset anything in the cache if this is the only instance of us not wanting to use it.
            preparedObservable = apiObservables.get(clazz);

        if(preparedObservable!=null)
            return preparedObservable;



        //we are here because we have never created this observable before or we didn't want to use the cache...

        preparedObservable = unPreparedObservable // TODO: Trasformare la risposta JSON (Observervble<MapsResponse>) in tanti oggetti da trattare singolarmente(Observable<List<Maps>>)

                .subscribeOn(Schedulers.newThread()) // Background Thread
                .observeOn(AndroidSchedulers.mainThread()); // UI Thread

        if(cacheObservable){
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }


        return preparedObservable;
    }


    /**
     * Funzione che serve per deserializzare la risposta JSON fornita dal server
     * e poter accedere ad un elemento alla volta del vettore JSON di risposta
     * sfruttando RxJava
     *
     * @param mapsResponseObservable Risposta JSON "grezza" fornita da Retrofit
     * @return Observable<Maps> su cui si andrà a lavorare
     */
    /*public Observable<Maps> getMap(Observable<MapsResponse> mapsResponseObservable) {

        return mapsResponseObservable
                .flatMap(new Func1<MapsResponse, Observable<Maps>>() { // TODO: Trasformare la risposta JSON (Observervble<MapsResponse>) in tanti oggetti da trattare singolarmente(Observable<List<Maps>>)
                    @Override
                    public Observable<Maps> call(MapsResponse iterable) {
                        return Observable.from(iterable.getMaps());
                    }
                })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }*/

    public Observable<Node> getNodes (Observable<MapResponse> mapResponse){ // Deserializzo la risposta JSON in tanti Nodi
        return mapResponse
                .flatMap(new Func1<MapResponse, Observable<Node>>() {
                    @Override
                    public Observable<Node> call(MapResponse mapResponse) {
                        return Observable.from(mapResponse.getNodes());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());//Qua faccio il filtro dell'Observables
    }
    public Observable<List<MapResponse>> getMaps (Observable<List<MapResponse>> mapResponse){
        return mapResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
