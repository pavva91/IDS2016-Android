package com.emergencyescape.utils;
/**
 * Created by Valerio Mattioli on 14/06/2016.
 */

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;

import com.emergencyescape.MyApplication;
import com.emergencyescape.R;

/**
 * com.emergencyescape
 * FloorBitmap - Effettua il draw del path e restituisce una bitmap in modo da poterla poi
 * richiamare con una semplice ImageView
 * prende in input una bitmap e fornisce come output la bitmap dopo le modifiche (draw)
 */
public class FloorBitmap extends BitmapDrawable {

    private Path path = new Path();
    private Paint drawPaint;
    private Bitmap floorImage;
    private Coordinate2D placeIconNode = new Coordinate2D(); // Coordinate del Place Icon (io sono qui)

    /**
     * Create drawable from a bitmap, setting initial target density based on
     * the display metrics of the resources.
     *
     * @param res
     * @param floorBitmap
     * @param floorPath - graphics.Path da stampare
     * @param floorPaint - Paint (stile path)
     */
    public FloorBitmap(Resources res, Bitmap floorBitmap, Path floorPath, Paint floorPaint) {
        super(res, floorBitmap);
        drawPaint = floorPaint;
        path = floorPath;
        placeIconNode.setX(0f);
        placeIconNode.setY(0f);
    }

    @Override
    public void draw(Canvas canvas) {
        floorImage = getBitmap();
        canvas.drawBitmap(floorImage,0,0,drawPaint);
        canvas.drawPath(path, drawPaint);
        canvas.drawBitmap(BitmapFactory.decodeResource(MyApplication.context.getResources(), R.drawable.ic_place_red_900_48dp),placeIconNode.getX()-DeviceDimensionsHelper.convertDpToPixel(24f,MyApplication.context),placeIconNode.getY()-DeviceDimensionsHelper.convertDpToPixel(48f,MyApplication.context),null);
    }

    public void setPlaceIconNode(Coordinate2D placeIconNode){
        this.placeIconNode = placeIconNode;
    }
}
