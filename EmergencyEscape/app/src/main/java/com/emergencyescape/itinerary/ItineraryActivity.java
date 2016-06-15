package com.emergencyescape.itinerary;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.emergencyescape.FloorBitmap;
import com.emergencyescape.FloorPathHelper;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.dijkstra.Graph;
import com.emergencyescape.greendao.Node;
import com.emergencyescape.server.ServerService;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItineraryActivity extends CommonBehaviourActivity<ItineraryView,ItineraryPresenter> implements ItineraryView {

    // TODO: Creare Background Thread (RxJava) che va a fare il calcolo prendendo i valori(Departure (e) Destination) (per ora) da CommonBehaviourViewState

    @BindView(R.id.toolbar) Toolbar toolbar;
    //@BindView(R.id.Percorso) TextView rxResponse;

    @BindView(R.id.pathListView) ListView pathListView;
    @BindView(R.id.pathImageView) ImageView pathImageView;
    private FloorBitmap floorBitmap;
    private Paint paintStyle;
    //Bitmap bitmap;


    private static final String EXTRA_RX = "EXTRA_RX";
    private ServerService service;
    private boolean rxCallInWorks = false;

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link ItineraryPresenter} for this view
     */
    @NonNull
    @Override
    public ItineraryPresenter createPresenter() {
        presenter = new ItineraryPresenter();
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);




        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*
        if(getEmergencyState()){
            showShortestPathEmergency();
        }else{
            showShortestPathNoEmergency();
        }
*/

        setPaintStyle(Color.BLUE);
        floorBitmap = new FloorBitmap(getResources(),getFloorBitmap(),getFloorPath(getShortestPath()),getPaintStyle()); // ,getFloorPath(getShortestPath()),getPaintStyle()

        pathImageView.setImageDrawable(floorBitmap);




        if(savedInstanceState!=null){
            rxCallInWorks = savedInstanceState.getBoolean(EXTRA_RX);
        }


    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Graph.CostPathPair getShortestPath(){ // Esegue il calcolo vero e proprio
        Graph.CostPathPair shortestPath;
        if(getEmergencyState()){
            shortestPath = presenter.getEmergencyShortestPath(
                    presenter.getDepartureCode(),
                    presenter.getEmergencyDestinations());
        }else{
            shortestPath = presenter.getShortestPath(
                    presenter.getDepartureCode(),
                    presenter.getDestination(),
                    false);
        }
        return shortestPath;
    }



    @Override
    public void showShortestPathNoEmergency(){ // TODO: Da eliminare
        Graph.CostPathPair shortestPath = presenter.getShortestPath(
                presenter.getDepartureCode(),
                presenter.getDestination(),
                false);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, shortestPath.getPath());

        pathListView.setAdapter(adapter);

    }

    @Override
    public void showShortestPathEmergency() { // TODO: Da eliminare
        Graph.CostPathPair shortestPath = presenter.getEmergencyShortestPath(
                presenter.getDepartureCode(),
                presenter.getEmergencyDestinations());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, shortestPath.getPath());//TODO: Far ritornare List<Edge> (Edge del greenDAO)

        pathListView.setAdapter(adapter);


    }

    protected Bitmap getFloorBitmap(){ // FUNZIONA
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.q145);
        Node departure = presenter.getDeparture();
        Integer quoteInteger = departure.getQuote();

        if(quoteInteger==145){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.q145);
        } else if(quoteInteger==150){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.q150);
        } else if(quoteInteger==155){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.q155);
        }
        return bitmap;
    }

    protected Path getFloorPath(Graph.CostPathPair shortestPath){ // TODO: Cambiare in coordinate mappa
        Path floorPath = presenter.getScaledPath(shortestPath);

        return floorPath;
    }

    protected void setPaintStyle(int paintColor){
        Paint drawPaint = new Paint();


        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        paintStyle = drawPaint;
    }

    protected Paint getPaintStyle(){
        return paintStyle;
    }




    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }
}
