package com.emergencyescape.itinerary;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.emergencyescape.Coordinate2D;
import com.emergencyescape.DeviceDimensionsHelper;
import com.emergencyescape.FloorBitmap;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.dijkstra.Graph;
import com.emergencyescape.greendao.Node;
import com.emergencyescape.server.ServerService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ItineraryActivity extends CommonBehaviourActivity<ItineraryView,ItineraryPresenter> implements ItineraryView {

    // TODO: Creare Background Thread (RxJava) che va a fare il calcolo prendendo i valori(Departure (e) Destination) (per ora) da CommonBehaviourViewState

    @BindView(R.id.toolbar) Toolbar toolbar;
    //@BindView(R.id.Percorso) TextView rxResponse;
    @BindView(R.id.pathImageView) ImageView pathImageView;
    private FloorBitmap floorBitmap;
    private Paint paintStyle;

    private Coordinate2D startingNode = new Coordinate2D();
    private static final String STARTING_NODE_X = "textValueX";
    private static final String STARTING_NODE_Y = "textValueY";
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

        if (savedInstanceState != null) {// Non funziona
            Float savedX = savedInstanceState.getFloat(STARTING_NODE_X);
            startingNode.setX(savedX);
            Float savedY = savedInstanceState.getFloat(STARTING_NODE_Y);
            startingNode.setY(savedY);
        }

/*
        if(getEmergencyState()){
            showShortestPathEmergency();
        }else{
            showShortestPathNoEmergency();
        }
*/
        setPaintStyle(Color.RED);
        floorBitmap = new FloorBitmap(getResources(),getFloorBitmap(),getFloorPath(getShortestPath()),getPaintStyle());
        startingNode = presenter.getStartingNode(); // TODO: Vedere se funziona

        floorBitmap.setPlaceIconNode(startingNode);

        pathImageView.setImageDrawable(floorBitmap);

        if(savedInstanceState!=null){
            rxCallInWorks = savedInstanceState.getBoolean(EXTRA_RX);
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(STARTING_NODE_X, startingNode.getX());
        outState.putFloat(STARTING_NODE_Y, startingNode.getY());
    }

    /**
     * Aggiorna posizione successiva ed effettua il ricalcolo (comprese modifiche FloorBitmap
     */
    @OnClick(R.id.forwardButton)
    public void clickForward(){
        List<Graph.Edge> shortestPath = getShortestPath().getPath();
        if (shortestPath.size()>0) {
            Graph.Vertex nextDepartureDijkstra = shortestPath.get(0).getToVertex();
            String DepNextString = nextDepartureDijkstra.getValue().toString();
            presenter.setUserDeparture(DepNextString);
            floorBitmap = new FloorBitmap(getResources(),getFloorBitmap(),getFloorPath(getShortestPath()),getPaintStyle());

            startingNode = presenter.getStartingNode();
            floorBitmap.setPlaceIconNode(startingNode);

            pathImageView.setImageDrawable(floorBitmap);
            Integer departureQuote = presenter.getDeparture().getQuote();
            Integer destinationQuote = presenter.getDestinationNodeDao().getQuote();
            if(presenter.getBooleanPrintStairsMessage()){
                Toast.makeText(this,getResources().getString(R.string.stairs_message) + " " + presenter.getPrintStairsMessage(), Toast.LENGTH_LONG).show();
            }

        }
        if(shortestPath.size()<=1){
            startingNode = presenter.getDestinationNode();
            floorBitmap.setPlaceIconNode(startingNode);
            Toast.makeText(this, getResources().getString(R.string.arrived_message), Toast.LENGTH_LONG).show();

        }
    }


    @Override
    protected void onPause() {
        super.onPause();

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
        /*
        Graph.CostPathPair shortestPath = presenter.getShortestPath(
                presenter.getDepartureCode(),
                presenter.getDestination(),
                false);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, shortestPath.getPath());

        pathListView.setAdapter(adapter);
        */

    }

    @Override
    public void showShortestPathEmergency() { // TODO: Da eliminare
        /*
        Graph.CostPathPair shortestPath = presenter.getEmergencyShortestPath(
                presenter.getDepartureCode(),
                presenter.getEmergencyDestinations());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, shortestPath.getPath());//TODO: Far ritornare List<Edge> (Edge del greenDAO)

        pathListView.setAdapter(adapter);

*/

    }

    protected Bitmap getFloorBitmap(){
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

    protected Path getFloorPath(Graph.CostPathPair shortestPath){
        Path floorPath = presenter.getScaledPath(shortestPath);
        return floorPath;
    }

    protected void setPaintStyle(int paintColor){
        // TODO: Sarebbe bello permettere all'utente di modificare lo stile del path da Setting
        Paint drawPaint = new Paint();


        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(DeviceDimensionsHelper.convertDpToPixel(13f,getApplicationContext())); // 10
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
