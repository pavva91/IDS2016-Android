package com.emergencyescape.tap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;
import com.emergencyescape.text.TextDestinationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapActivity extends CommonBehaviourActivity<TapView,MapPresenter> {

    @BindView(R.id.floorImage) ImageView floorImage;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @NonNull
    @Override
    public MapPresenter createPresenter() {
        return new MapPresenter();
    }



    private Bitmap bitmap;

    // TODO: Schifo assoluto
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
        setContentView(R.layout.activity_maptap);

        ButterKnife.bind(this);

        populateImageView(getIntent().getExtras().getString("floor"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void populateImageView(String floor){
        floorImage.setImageDrawable(presenter.getFloorImage(floor));
    }

    private void quote(){ // TODO: Spostare questa funzione secondo il pattern utilizzando il model

        String quota = getIntent().getExtras().getString("floor");
        System.out.println("quota " + String.valueOf(quota));


        if (quota.equals("145")) {
            final ImageView imgView = (ImageView) findViewById(R.id.image);
            imgView.setImageResource(getIntent().getIntExtra("id", R.id.image));
            imgView.setDrawingCacheEnabled(true);
            imgView.buildDrawingCache(true);
            Drawable d = getResources().getDrawable(R.drawable.q145);
            imgView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bitmap = imgView.getDrawingCache();
                        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

                       /*     int r = Color.red(pixel);
                            int b = Color.blue(pixel);
                            int g = Color.green(pixel);*/

                        System.out.println("pixel " + String.valueOf(pixel));

                        String hexColor = String.format("#%06X", (0xFFFFFF & pixel));
                        System.out.println("hex " + String.valueOf(hexColor));

                        switch (event.getActionMasked()) {

                            case MotionEvent.ACTION_DOWN:

                                if (pixel == aulamagna) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Aula magna");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == dacs) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "DACS");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == dardus) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "DARDUS");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == dacs) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "DACS");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == g1) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
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
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Dipartimento di meccanica");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == isac) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
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

            final ImageView imgView = (ImageView) findViewById(R.id.image);
            imgView.setImageResource(getIntent().getIntExtra("id", R.id.image));
            imgView.setDrawingCacheEnabled(true);
            imgView.buildDrawingCache(true);

            imgView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bitmap = imgView.getDrawingCache();

                        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());
                        System.out.println("pixel " + String.valueOf(pixel));
                        String hexColor = String.format("#%06X", (0xFFFFFF & pixel));
                        System.out.println("hex " + String.valueOf(hexColor));

                        switch (event.getActionMasked()) {

                            case MotionEvent.ACTION_DOWN:

                                if (pixel == aulamagna) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Aula magna");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == dacs) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "DACS");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == g1) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
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
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
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
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Atelier informatica");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }

                                if (pixel == csal) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "CSAL");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == biblioteca) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Biblioteca");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == dipbiochimica) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Dipartimento biochimica");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == dippatologia) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
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
            final ImageView imgView = (ImageView) findViewById(R.id.image);
            imgView.setImageResource(getIntent().getIntExtra("id", R.id.image));
            imgView.setDrawingCacheEnabled(true);
            imgView.buildDrawingCache(true);

            imgView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bitmap = imgView.getDrawingCache();
                        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

                        System.out.println("pixel " + String.valueOf(pixel));
                        String hexColor = String.format("#%06X", (0xFFFFFF & pixel));
                        System.out.println("hex " + String.valueOf(hexColor));

                        switch (event.getActionMasked()) {

                            case MotionEvent.ACTION_DOWN:

                                if (pixel == dacs) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "DACS");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == dardus) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "DARDUS");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == isac) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "ISAC");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == bar) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Bar");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == banca) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Banca");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == serfotocopie) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
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
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "CeSMI");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == fimet) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "FIMeT");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == salalettura) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Sala lettura");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == at1) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "AT1");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == dipscmat) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Dipartimento scienze matematiche");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == dipmeccanica) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Dipartimento meccanica");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == dipbiomed) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
                                    a.putExtra("aula", "Dipartimento scienze biomediche");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155d1) {
                                    Intent a = new Intent(MapActivity.this, TextDestinationActivity.class);
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