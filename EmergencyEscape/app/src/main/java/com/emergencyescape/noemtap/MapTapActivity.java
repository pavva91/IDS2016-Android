package com.emergencyescape.noemtap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.emergencyescape.utility.CommonMenuActivity;
import com.emergencyescape.utility.DestinationActivity;
import com.emergencyescape.R;


public class MapTapActivity extends CommonMenuActivity {

    private Bitmap bitmap;


int q145dicea =-8388896;
int q145rg1= -12204496;
int q145rg2= -14913778;
int q145ram= -8355841;
int q145wc1= -8320;
int q145r1= -15864725;
int q145r3= -15486891;
/*int q145s1= ;
int q145s2= ;
int q145s3= ;*/

int q150dicea= -5111816;
int q150dicea1= -4128776;
int q150strade= -9446035;
int q150g1= -8462337;
int q150g2= -13396555;
//int q150g1g2= ;
int q150r2= -16584198;
//int q150wc1= ;
int q150ram= -5263617;
int q150bib= -1082939;
int q150rl= -32832;
int q150r1= -59530;
int q150s1= -9842;

int q155dicea = -4129024;
int q155r567 = -2946;
int q155r4 = -592842;
int q155r4d3 = -4217;
int q155r23d2 = -2646;
int q155rd1 = -10173;
int q155ecdl = -22907;
int q155wc1 = -20307;
int q155wc2 = -20413;
int q155bar = -8388704;
int q155cesmi = -2237184;
int q155up = -8791909;
int q155acq = -2243281;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maptap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quote();

    }

    private void quote(){

        String quota = getIntent().getExtras().getString("quota");
        System.out.println("quota " + String.valueOf(quota));


        if (quota.equals("q145")) {
            final ImageView imgView = (ImageView) findViewById(R.id.image);
            imgView.setImageResource(getIntent().getIntExtra("id", R.id.image));
            imgView.setDrawingCacheEnabled(true);
            imgView.buildDrawingCache(true);

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

                                if (pixel == q145ram) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "Aula magna");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == q145dicea) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "DACS");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == q145wc1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "WC1");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == q145r1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "145/1");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                if (pixel == q145rg1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "G1");
                                    a.putExtra("quotap", "145");
                                    startActivity(a);
                                }
                                    if (pixel == q145rg2) {
                                        Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                        a.putExtra("aula", "G2");
                                        a.putExtra("quotap", "145");
                                        startActivity(a);
                                    }
                                    if (pixel == q145r3) {
                                        Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                        a.putExtra("aula", "145/3");
                                        a.putExtra("quotap", "145");
                                        startActivity(a);
                                    }
                                  /*  if (pixel == q145s1) {
                                        Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                        a.putExtra("aula", "");
                                        a.putExtra("quotap", "145");
                                        startActivity(a);
                                    }
                                    if (pixel == q145s2) {
                                        Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                        a.putExtra("aula", "");
                                        a.putExtra("quotap", "145");
                                        startActivity(a);
                                    }
                                    if (pixel == q145s3) {
                                        Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                        a.putExtra("aula", "");
                                        a.putExtra("quotap", "145");
                                        startActivity(a);
                                    }*/

                        }
                    }
                    return true;
                }
            });


        }
        if (quota.equals("q150")) {

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

                                if (pixel == q150ram) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "Aula magna");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == q150dicea) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "dicea");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == q150dicea1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "dicea1");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == q150g1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "G1");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                    if (pixel == q150g2) {
                                        Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                        a.putExtra("aula", "G2");
                                        a.putExtra("quotap", "150");
                                        startActivity(a);
                                    }
                                   /* if (pixel == q150g1g2) {
                                        Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                        a.putExtra("aula", "g1g2");
                                         a.putExtra("quotap", "150");
                                        startActivity(a);
                                    }*/
                                if (pixel == q150rl) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "Sala lettura");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == q150r2) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "150/2");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == q150s1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "Atelier informatica");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }

                                if (pixel == q150r1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "150/1");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == q150bib) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "Biblioteca");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                                if (pixel == q150strade) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "isac");
                                    a.putExtra("quotap", "150");
                                    startActivity(a);
                                }
                               /* if (pixel == q150wc1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "wc1");
                                    a.putExtra("quota", "150");
                                    startActivity(a);
                                }*/

                        }

                    }

                    return true;
                }
            });


        }
        if (quota.equals("q155")) {
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

                                if (pixel == q155dicea) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "dardus");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155r567) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "155/5-6 155/7");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155r4) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "155/d4");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155r4d3) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "155/d3 155/4");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155up) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "Banca");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155acq) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "cesmi basso");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                    if (pixel == q155ecdl) {
                                        Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                        a.putExtra("aula", "Aula ECDL, 155/10");
                                        a.putExtra("quotap", "155");
                                        startActivity(a);
                                    }
                                if (pixel == q155cesmi) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "CeSMI alto");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155r23d2) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "155/d2 155/2-3");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155rd1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "155/d1");
                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155wc1) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "wc1");
                                    a.putExtra("quotap", "155");

                                    startActivity(a);
                                }
                                if (pixel == q155wc2) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "wc2");

                                    a.putExtra("quotap", "155");
                                    startActivity(a);
                                }
                                if (pixel == q155bar) {
                                    Intent a = new Intent(MapTapActivity.this, DestinationActivity.class);
                                    a.putExtra("aula", "bar");
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








    //action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


}