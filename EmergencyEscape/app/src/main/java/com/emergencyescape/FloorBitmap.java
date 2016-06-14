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

import com.emergencyescape.dijkstra.Graph;

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
    Bitmap image;
    Canvas canvas;

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
        //draw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        //lineStylePaint();
        image = getBitmap();
        canvas.drawBitmap(image,0,0,drawPaint);
        //canvas.drawPath(path, drawPaint);
        canvas.drawText("PROVA TEXT",DeviceDimensionsHelper.convertDpToPixel(150f,context),DeviceDimensionsHelper.convertDpToPixel(200f,context),drawPaint);
        canvas.drawLine(130f,140f,170f,135f,drawPaint);
    }

    private void lineStylePaint() {
        // Setup paint with color and stroke styles
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }
}
