package com.emergencyescape.tap;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;


import com.emergencyescape.DeviceDimensionsHelper;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.greendao.Node;
import com.emergencyescape.itinerary.ItineraryActivity;
import com.emergencyescape.text.TextDestinationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;


public class MapActivity extends CommonBehaviourActivity<TapView,MapPresenter> {

    @BindView(R.id.floorImage) ImageView floorImage;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @NonNull
    @Override
    public MapPresenter createPresenter() {
        return new MapPresenter();
    }



    private String LOG = this.toString();
    private Bitmap bitmap;


    // TODO:  va bene
    int aulamagna=-8355841;
    int dacs= -8388896;
    int dardus=-4129024;
    int g1= -8462337;
    int g2= -8462337;
    /*int 1454= ; #7edfff
    int 1455= ; #7edfff
    int 150st2= ;#81deff*/
    int dipmeccanica= -2293595;
    int isac= -9446035;
  //  int q1501= -8462337; #7edfff
   // int 1502= ; #7cdfff
    int salalettura= -32832;
    int atelierinformatica= -2302976;
    int csal= -11889571;
    int biblioteca= -4629321;
    int dippatologia= -2200064;
    int dipbiochimica= -32866;
   // int assstud= ; //#a501db
    int fimet= -16741704;
    int serfotocopie= -8388704;
    int dipscmat= -33024;
    int at1= -8462337;
    int at2= -8462337;
   // int aulafisica= ; #7de1ff
  //  int s8= ;#7fddf9
    int bar= -8388704;
    int cesmi= -2237184;
    int banca= -9648936;
  //  int 15510= ;
  //  int ecdl= ;#80deff
    int q155d1= -8527106;  //d2 d3 d4 2-3 4 5-6 7
    int dipbiomed =-8388609;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_maptap);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        ButterKnife.bind(this);

        populateImageView(getIntent().getExtras().getString("floor"));
        //quote();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    public void populateImageView(String floor){
        floorImage.setImageDrawable(presenter.getFloorImage(floor));
    }




    @OnTouch(R.id.floorImage)
    public boolean floorImageClick(MotionEvent event){

        Drawable drawable = floorImage.getDrawable();
        floorImage.buildDrawingCache();
        Bitmap bmap = floorImage.getDrawingCache();
        Integer imageViewWidthpx = bmap.getScaledWidth(getResources().getDisplayMetrics().densityDpi);
        Integer imageViewHeightpx = bmap.getScaledHeight(getResources().getDisplayMetrics().densityDpi);

        Float imageWidthpx;
        Float imageHeightpx;

        Integer bitmapWidth = drawable.getIntrinsicWidth(); //this is the original bitmap's width (818px)
        Integer bitmapHeight = drawable.getIntrinsicHeight(); //this is the original bitmap's height (477px)

        Float originalBitmapWidth = DeviceDimensionsHelper.convertPixelsToDp(bitmapWidth,this);
        Float originalBitmapHeight = DeviceDimensionsHelper.convertPixelsToDp(bitmapHeight,this);
        Float imageRatio = originalBitmapWidth/originalBitmapHeight; // W/H

        Float tappedImageViewXpx = event.getX(); // grandezza ImageView nello schermo
        Float tappedImageViewYpx = event.getY();

        Float paddingTopY = 0f;
        Float paddingLeftX = 0f;

        Float imageXcoord; // coordinate rispetto misure schermo
        Float imageYcoord;

        Float tappedXdp; // coordinate rispetto .png originale
        Float tappedYdp;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){ // Calcolo le grandezze in pixel dell'immagine stampata a schermo
            imageWidthpx = (float)imageViewWidthpx;
            imageHeightpx = (float)imageViewWidthpx / imageRatio;
            paddingTopY = (imageViewHeightpx-imageHeightpx)/2; // calcolo padding in pixel dello schermo
        }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageWidthpx = (float)imageViewHeightpx * imageRatio;
            imageHeightpx = (float)imageViewHeightpx;
            paddingLeftX = (imageViewWidthpx-imageWidthpx)/2; // calcolo paddin in pixel dello schermo
        }else{
            imageWidthpx = (float)imageViewWidthpx;
            imageHeightpx = (float)imageViewWidthpx / imageRatio;
            paddingTopY = (imageViewHeightpx-imageHeightpx)/2;

        }

        // tolti i padding ho le coordinate in px schermo del tap sull'immagine
        imageXcoord = tappedImageViewXpx - paddingLeftX;
        imageYcoord = tappedImageViewYpx - paddingTopY;
        if (imageXcoord>imageWidthpx||imageYcoord>imageHeightpx||imageXcoord < 0 || imageYcoord < 0){ // escludo tap nei punti a sx, dx, sopra e sotto l'immagine
            return false;
        }

        // trasformo ora queste coordinate nei pixel rispetto l'immagine originale (818x477)
        tappedXdp = (imageXcoord/imageWidthpx)*originalBitmapWidth;
        tappedYdp = (imageYcoord/imageHeightpx)*originalBitmapHeight;

        Node nodeTapped = presenter.getNodeTapped(tappedXdp,tappedYdp,getIntent().getExtras().getString("floor"));
        presenter.setUserDeparture(nodeTapped);


        final Intent intentToStart;
        if (this.getEmergencyState()) {
            intentToStart = new Intent(MapActivity.this, ItineraryActivity.class);
        }else{
            intentToStart = new Intent(MapActivity.this,TextDestinationActivity.class);
        }
        startActivity(intentToStart);

        return false;
    }





    private void quote(){ // TODO: Spostare questa funzione secondo il pattern utilizzando il model, fanculo siamo indietro come le palle dei cani
        //final Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
        final Intent a;
        if (this.getEmergencyState()) {
             a = new Intent(MapActivity.this, ItineraryActivity.class);
        }else{
             a = new Intent(MapActivity.this,TextDestinationActivity.class);
        }

        String quota = getIntent().getExtras().getString("floor");
        System.out.println("quota " + String.valueOf(quota));

        floorImage.setDrawingCacheEnabled(true);
        floorImage.buildDrawingCache(true);


        if (quota.equals("145")) {
            /*
            final ImageView imgView = (ImageView) findViewById(R.id.image);
            imgView.setImageResource(getIntent().getIntExtra("id", R.id.image)); // ImageView
            imgView.setDrawingCacheEnabled(true);
            imgView.buildDrawingCache(true);
            Drawable d = getResources().getDrawable(R.drawable.q145); // Drawable
            */

            floorImage.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bitmap = floorImage.getDrawingCache();
                        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());


                            /*
                            int r = Color.red(pixel);
                            int b = Color.blue(pixel);
                            int g = Color.green(pixel);
                            */

                        System.out.println("pixel " + String.valueOf(pixel));

                        String hexColor = String.format("#%06X", (0xFFFFFF & pixel));
                        System.out.println("hex " + String.valueOf(hexColor));

                        switch (event.getActionMasked()) {

                            case MotionEvent.ACTION_DOWN:

                                if (pixel == aulamagna) {
                                    a.putExtra("aula", "Aula magna");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == dacs) {
                                    a.putExtra("aula", "DACS");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == dardus) {
                                    a.putExtra("aula", "DARDUS");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == dacs) {
                                    a.putExtra("aula", "DACS");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == g1) {
                                    a.putExtra("aula", "G1");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                  /*  if (pixel == g2) {
                                        Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                        a.putExtra("aula", "G2");
                                        startActivity(a);
                                    }
                                    if (pixel == q1454) {
                                        Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                        a.putExtra("aula", "145/4");
                                        startActivity(a);
                                    }
                                    if (pixel == q1455) {
                                        Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                        a.putExtra("aula", "145/5");
                                        startActivity(a);
                                    }
                                    if (pixel == q1451) {
                                        Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                        a.putExtra("aula", "145/1");
                                        startActivity(a);
                                    }
                                    if (pixel == q1453) {
                                        Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                        a.putExtra("aula", "145/3");
                                        startActivity(a);
                                    }*/
                                if (pixel == dipmeccanica) {
                                    a.putExtra("aula", "Dipartimento di meccanica");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == isac) {
                                    a.putExtra("aula", "ISAC");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                        }
                    }
                return true;
                }
            });


        }
        if (quota.equals("150")) {

            /*
            final ImageView imgView = (ImageView) findViewById(R.id.image);

            imgView.setImageResource(getIntent().getIntExtra("id", R.id.image));
            imgView.setDrawingCacheEnabled(true);
            imgView.buildDrawingCache(true);
            */

            floorImage.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bitmap = floorImage.getDrawingCache();

                        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());
                        System.out.println("pixel " + String.valueOf(pixel));
                        String hexColor = String.format("#%06X", (0xFFFFFF & pixel));
                        System.out.println("hex " + String.valueOf(hexColor));

                        switch (event.getActionMasked()) {

                            case MotionEvent.ACTION_DOWN:

                                if (pixel == aulamagna) {
                                    a.putExtra("aula", "Aula magna");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == dacs) {
                                    a.putExtra("aula", "DACS");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == g1) {
                                    a.putExtra("aula", "G1");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                  /*  if (pixel == g2) {
                                        Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                        a.putExtra("aula", "G2");
                                        startActivity(a);
                                    }
                                    if (pixel == q1502) {
                                        Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                        a.putExtra("aula", "150/2");
                                        startActivity(a);
                                    }*/
                                if (pixel == salalettura) {
                                    a.putExtra("aula", "Sala lettura");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                               /* if (pixel == q1501) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "150/1");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }*/
                                if (pixel == atelierinformatica) {
                                    a.putExtra("aula", "Atelier informatica");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }

                                if (pixel == csal) {
                                    a.putExtra("aula", "CSAL");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == biblioteca) {
                                    a.putExtra("aula", "Biblioteca");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == dipbiochimica) {
                                    a.putExtra("aula", "Dipartimento biochimica");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == dippatologia) {
                                    a.putExtra("aula", "Dipartimento patologia");
                                    a.putExtra("quota", "150");
                                    startActivity(a);
                                }
                                /*if (pixel == assstud) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Associazioni studentesche");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }*/
                        }

                    }

                    return true;
                }
            });


        }
        if (quota.equals("155")) {
            /*
            final ImageView imgView = (ImageView) findViewById(R.id.image);
            imgView.setImageResource(getIntent().getIntExtra("id", R.id.image));
            imgView.setDrawingCacheEnabled(true);
            imgView.buildDrawingCache(true);
            */

            floorImage.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bitmap = floorImage.getDrawingCache();
                        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

                        System.out.println("pixel " + String.valueOf(pixel));
                        String hexColor = String.format("#%06X", (0xFFFFFF & pixel));
                        System.out.println("hex " + String.valueOf(hexColor));

                        switch (event.getActionMasked()) {

                            case MotionEvent.ACTION_DOWN:

                                if (pixel == dacs) {
                                    a.putExtra("aula", "DACS");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == dardus) {
                                    a.putExtra("aula", "DARDUS");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == isac) {
                                    a.putExtra("aula", "ISAC");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == bar) {
                                    a.putExtra("aula", "Bar");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == banca) {
                                    a.putExtra("aula", "Banca");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == serfotocopie) {
                                    a.putExtra("aula", "Servizio fotocopie");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                 /*   if (pixel == ecdl) {
                                        Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                        a.putExtra("aula", "Aula ECDL");
                                        startActivity(a);
                                    }*/
                                if (pixel == cesmi) {
                                    a.putExtra("aula", "CeSMI");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == fimet) {
                                    a.putExtra("aula", "FIMeT");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == salalettura) {
                                    a.putExtra("aula", "Sala lettura");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == at1) {
                                    a.putExtra("aula", "AT1");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == dipscmat) {
                                    a.putExtra("aula", "Dipartimento scienze matematiche");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == dipmeccanica) {
                                    a.putExtra("aula", "Dipartimento meccanica");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == dipbiomed) {
                                    a.putExtra("aula", "Dipartimento scienze biomediche");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155d1) {

                                    a.putExtra("aula", "155/d1");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }


                        }

                    }


                    return true;
                }
            });

        }
    }



    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }
}