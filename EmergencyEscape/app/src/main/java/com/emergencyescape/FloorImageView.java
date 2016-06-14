package com.emergencyescape;
/**
 * Created by Valerio Mattioli on 10/06/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.emergencyescape.dijkstra.Graph;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape
 * FloorImageView
 */
public class FloorImageView extends ImageView {
    Context context = MyApplication.getInstance().getApplicationContext();
    // Get height or width of screen
    int screenHeight = DeviceDimensionsHelper.getDisplayHeight(context);
    int screenWidth = DeviceDimensionsHelper.getDisplayWidth(context);
    // Convert dp to pixels
    float px = DeviceDimensionsHelper.convertDpToPixel(25f, context);
    // Convert pixels to dp
    float dp = DeviceDimensionsHelper.convertPixelsToDp(25f, context);

    private Path path = new Path();

    private final int paintColor = Color.BLACK;
    // defines paint and canvas
    private Paint drawPaint;

    private Bitmap scaledBitmap;
    Bitmap image;
    FloorBitmap floorBitmap;

    private int mWidth;
    private int mHeight;
    private float mAngle;

    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w    Current width of this view.
     * @param h    Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public FloorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setFocusable(true);
        setVerticalScrollBarEnabled(true);

        setFocusableInTouchMode(true);
        setBitmap();

        lineStylePaint();
    }

    private void setBitmap(){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        image = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.q145,options); // TODO: Devo lavorare, misure originali: image.getWidth()
        //floorBitmap = new FloorBitmap(getResources(),image);
        if(context.getResources().getConfiguration().orientation==1) {
            scaledBitmap = ScalingUtilities.createScaledBitmap(image, screenWidth, screenHeight, ScalingUtilities.ScalingLogic.FIT);
        }else scaledBitmap = ScalingUtilities.createScaledBitmap(image, screenWidth, screenHeight, ScalingUtilities.ScalingLogic.FIT); // TODO: Sistemare scaling immagine

    }

    // Setup paint with color and stroke styles
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(image,0,0,drawPaint);
        canvas.drawPath(path, drawPaint);
        canvas.drawText("PROVA TEXT",DeviceDimensionsHelper.convertDpToPixel(150f,context),DeviceDimensionsHelper.convertDpToPixel(200f,context),drawPaint);
        canvas.drawLine(130f,140f,170f,135f,drawPaint);
        // TODO: Così funzionerebbe perfettamente lo scaling del draw se solo trovassi un modo per scalare ora il risultato (Canvas)

        // Non si riesce a scalare direttamente Canvas, provo:
        // TODO: Salvare Canvas come immagine e ricreare Bitmap con quell'immagine "disegnata"
    }

    public Bitmap saveSignature(){

        Bitmap  bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);

        File file = new File(Environment.getExternalStorageDirectory() + "/sign.png");

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }



}
