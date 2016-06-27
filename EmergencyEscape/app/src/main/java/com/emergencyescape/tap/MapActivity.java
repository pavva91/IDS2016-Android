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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    public void populateImageView(String floor){
        floorImage.setImageDrawable(presenter.getFloorImage(floor));
    }


    /**
     * Effettua il riconoscimento del touch sullo schermo nelle coordinate (in metri) rispetto la mappa
     * @param event
     * @return
     */
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

        // trasformo ora queste coordinate nei pixel rispetto l'immagine originale (818x477)(dp)
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

    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }
}