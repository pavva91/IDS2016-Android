package com.emergencyescape.businesslogic;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.emergencyescape.R;
import com.emergencyescape.main.MainActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;


/**
 * Created by Betta73 on 19/06/2016.
 */
public class NotificationReceiver extends BroadcastReceiver
{
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public NotificationReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent)
    {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        Bundle extras = intent.getExtras();
        String msg = intent.getStringExtra("message");

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty())
        {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
            {
                // emette una notifica sul dispositivo
                sendNotification(context,msg);

            }
        }
    }

    private void sendNotification(Context ctx,String msg)
    {
        mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        // scelta suoneria per notifica
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(),
                R.drawable.logo1piccolo);

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.logo1piccolo)
                        .setLargeIcon(icon)
                        .setContentTitle("Emergency Escape")
                        .setContentText(msg)
                        .setSound(sound)
                        .setAutoCancel(true);

        Intent notificationIntent = new Intent(ctx, MainActivity.class);
        android.support.v4.app.TaskStackBuilder sb = android.support.v4.app.TaskStackBuilder.create(ctx);
        sb.addParentStack(MainActivity.class);
        sb.addNextIntent(notificationIntent);

        PendingIntent intent = sb.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(intent);

        // effettua la notifica
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
