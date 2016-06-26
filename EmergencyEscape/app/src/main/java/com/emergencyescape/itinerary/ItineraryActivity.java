package com.emergencyescape.itinerary;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.emergencyescape.Coordinate2D;
import com.emergencyescape.DeviceDimensionsHelper;
import com.emergencyescape.FloorBitmap;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.dijkstra.Graph;
import com.emergencyescape.greendao.Node;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;


public class ItineraryActivity extends CommonBehaviourActivity<ItineraryView,ItineraryPresenter> implements ItineraryView {

    // TODO: Creare Background Thread (RxJava) che va a fare il calcolo prendendo i valori(Departure (e) Destination) (per ora) da CommonBehaviourViewState

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pathImageView) ImageView pathImageView;
    @BindView(R.id.bestPathButton) Button bestPathButton;
    @BindView(R.id.alterntivePathButton) Button alternativePathButton;
    @BindView(R.id.forwardBestButton) ImageButton forwardBestButton;
    @BindView(R.id.forwardAlternativeButton) ImageButton forwardAlternativeButton;
    private FloorBitmap floorBitmap;
    private Paint paintStyle;

    private Coordinate2D startingNode = new Coordinate2D();



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


        // Leggo stato Best/Alternative Path
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this); // TODO: Usare shared Betta
        boolean showBestPath = wmbPreference.getBoolean("BEST_PATH_UI", true);

        Graph.CostPathPair shortestPath = getShortestPath();

        if (showBestPath) {
            getBestPath();
        }else {
            getAlternativePath();
        }




        if(presenter.getBooleanPrintStairsMessage()){
            Toast.makeText(this,getResources().getString(R.string.stairs_message) + " " + presenter.getPrintStairsMessage(), Toast.LENGTH_LONG).show();
        }

        if(shortestPath.getCost()==0){
            Toast.makeText(this, getResources().getString(R.string.arrived_message), Toast.LENGTH_LONG).show();
        }

    }


    /**
     * Stampa percorso alternativo (Alternative Path)
     */
    @OnClick(R.id.alterntivePathButton)
    public void getAlternativePath(){

        // Salvo stato Best/Alternative Path
        setAlternativePathUI();

        Graph.CostPathPair alternativePath = presenter.getAlternativePath();
        setPaintStyle(Color.BLUE);
        floorBitmap = new FloorBitmap(getResources(),getFloorBitmap(),getFloorPath(alternativePath),getPaintStyle());
        startingNode = presenter.getStartingNode();
        floorBitmap.setPlaceIconNode(startingNode);
        pathImageView.setImageDrawable(floorBitmap);

        showAlternativeButton();
    }

    /**
     * Stampa percorso migliore (Shortest/Best Path)
     */
    @OnClick(R.id.bestPathButton)
    public void getBestPath(){
        // Salvo stato Best/Alternative Path
        setBestPathUI();

        setPaintStyle(Color.RED);
        floorBitmap = new FloorBitmap(getResources(), getFloorBitmap(), getFloorPath(getShortestPath()), getPaintStyle());
        startingNode = presenter.getStartingNode();
        floorBitmap.setPlaceIconNode(startingNode);
        pathImageView.setImageDrawable(floorBitmap);

        showBestButton();
    }

    /**
     * Aggiorna posizione successiva ed effettua il ricalcolo (comprese modifiche FloorBitmap
     */
    @OnClick(R.id.forwardBestButton)
    public void clickForward(){
        setPaintStyle(Color.RED);
        List<Graph.Edge> shortestPath = getShortestPath().getPath();
        if (shortestPath.size()>0) {
            Graph.Vertex nextDepartureDijkstra = shortestPath.get(0).getToVertex();
            String DepNextString = nextDepartureDijkstra.getValue().toString();
            presenter.setUserDeparture(DepNextString);

            setPaintStyle(Color.RED);
            Graph.CostPathPair forwardShortestPath = getShortestPath();
            floorBitmap = new FloorBitmap(getResources(),getFloorBitmap(),getFloorPath(forwardShortestPath),getPaintStyle());
            startingNode = presenter.getStartingNode();
            floorBitmap.setPlaceIconNode(startingNode);
            pathImageView.setImageDrawable(floorBitmap);

            if(presenter.getBooleanPrintStairsMessage()){
                Toast.makeText(this,getResources().getString(R.string.stairs_message) + " " + presenter.getPrintStairsMessage(), Toast.LENGTH_LONG).show();
            }

        }
        if(shortestPath.size()<=1){
            setPaintStyle(Color.RED);

            if(shortestPath.size()==0) {
                startingNode = presenter.getDestinationNode();
            }
            floorBitmap.setPlaceIconNode(startingNode);
            Toast.makeText(this, getResources().getString(R.string.arrived_message), Toast.LENGTH_LONG).show();

        }
    }

    @OnClick(R.id.forwardAlternativeButton)
    public void clickAlternativeForward() {
        setPaintStyle(Color.BLUE);
        // TODO: Scorrere (non ricalcolo) alternative path precedentemente calcolato
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Esegue il calcolo vero e proprio del percorso richiamando le rispettive funzioni Dijkstra
     * a seconda dello stato di emergenza (emergencyState)
     * @return
     */
    @Override
    public Graph.CostPathPair getShortestPath(){
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

    private void showBestButton(){
        forwardBestButton.setVisibility(View.VISIBLE);
        forwardAlternativeButton.setVisibility(View.GONE);
        bestPathButton.setVisibility(View.GONE);
        alternativePathButton.setVisibility(View.VISIBLE);
    }

    private void showAlternativeButton(){
        forwardBestButton.setVisibility(View.GONE);
        forwardAlternativeButton.setVisibility(View.VISIBLE);
        bestPathButton.setVisibility(View.VISIBLE);
        alternativePathButton.setVisibility(View.GONE);
    }
}
