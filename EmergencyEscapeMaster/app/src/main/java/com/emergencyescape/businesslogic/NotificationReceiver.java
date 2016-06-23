package com.emergencyescape.businesslogic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.logo1piccolo)
                        .setContentTitle("ATTENZIONE: situazione di emergenza")
                        .setContentText(msg)
                        .setSound(sound);


        Intent notificationIntent = new Intent(ctx, MainActivity.class);
        android.support.v4.app.TaskStackBuilder sb = android.support.v4.app.TaskStackBuilder.create(ctx);
        sb.addParentStack(MainActivity.class);
        sb.addNextIntent(notificationIntent);

       notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = sb.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(intent);

        mBuilder.build().flags |= Notification.FLAG_AUTO_CANCEL;

        // effettua la notifica
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
