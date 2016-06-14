package com.emergencyescape;
/**
 * Created by Valerio Mattioli on 14/06/2016.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;

/**
 * com.emergencyescape
 * FloorBitmap - Effettua il draw del path e restituisce una bitmap
 */
public class FloorBitmap extends BitmapDrawable {
    Context context = MyApplication.getInstance().getApplicationContext();
    // Get height or width of screen
    int screenHeight = DeviceDimensionsHelper.getDisplayHeight(context);
    int screenWidth = DeviceDimensionsHelper.getDisplayWidth(context);
    // Convert dp to pixels
    float px = DeviceDimensionsHelper.convertDpToPixel(25f, context);
    // Convert pixels to dp
    float dp = DeviceDimensionsHelper.convertPixelsToDp(25f, context);

    private Path path = new Path();

    private final int paintColor = Color.RED;
    // defines paint and canvas
    private Paint drawPaint;

    private Bitmap scaledBitmap;
    private Bitmap floorImage;

    /**
     * Create drawable from a bitmap, setting initial target density based on
     * the display metrics of the resources.
     *
     * @param res
     * @param floorBitmap
     * @param floorPath
     * @param floorPaint
     */
    public FloorBitmap(Resources res, Bitmap floorBitmap, Path floorPath, Paint floorPaint) { //, Path floorPath, Paint floorPaint
        super(res, floorBitmap);
        drawPaint = floorPaint;
        path = floorPath;
    }

    private Path createCustomPath(){
        Path customPath = new Path();
        customPath.moveTo(160,150); // INIZIO PATH
        customPath.lineTo(200,300);
        customPath.lineTo(500,300);
        customPath.lineTo(100,400);
        customPath.lineTo(250,300);
        customPath.lineTo(100,120);
        return customPath;
    }

    @Override
    public void draw(Canvas canvas) {
        floorImage = getBitmap();
        canvas.drawBitmap(floorImage,0,0,drawPaint);
        canvas.drawPath(createCustomPath(), drawPaint);
    }
}
