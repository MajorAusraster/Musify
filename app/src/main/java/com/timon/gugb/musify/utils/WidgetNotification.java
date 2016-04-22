package com.timon.gugb.musify.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;


import com.timon.gugb.musify.EventReceiver;
import com.timon.gugb.musify.MainActivity;
import com.timon.gugb.musify.R;
import com.timon.gugb.musify.managers.NotifyManager;

/**
 * Created by Timon on 16.04.2016.
 */
public class WidgetNotification {

    public static final String INTENT_NEXT_BUTTON=WidgetNotification.class.getName()+".next_button";

    Context context;
    NotificationManager mNotificationManager;
    RemoteViews notificationView;
    RemoteViews bigNotificationView;
    Notification n;

    public WidgetNotification(Context appContext) {
        this.context = appContext;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationView =  new RemoteViews(context.getPackageName(), R.layout.widget_notification);
        bigNotificationView= new RemoteViews(context.getPackageName(), R.layout.widget_notification_expanded);

    }

    public void makeNotification() {
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NotifyManager.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                // Set Icon
                .setSmallIcon(R.mipmap.ic_launcher)
                        // Set Ticker Message
                .setTicker("asd")

                        .setOngoing(true)
                        // Set PendingIntent into Notification
                .setContentIntent(pendingIntent);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // build a complex notification, with buttons and such
            //
            builder = builder.setContent(notificationView);

        } else {
            // Build a simpler notification, without buttons
            //
            builder = builder.setContentTitle("not compatible")
                    .setContentText("not compatible")
                    .setSmallIcon(android.R.drawable.ic_menu_gallery)
            ;
        }
        n=builder.build();

        //Intent for the button click
        Intent switchIntent = new Intent(context, EventReceiver.class);
        switchIntent.setAction(INTENT_NEXT_BUTTON);

        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0,
                switchIntent, 0);
        notificationView.setOnClickPendingIntent(R.id.notification_next,pendingSwitchIntent);

      //expandeble notification
        n.bigContentView=bigNotificationView;
       n.priority= Notification.PRIORITY_HIGH;

        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(NotifyManager.NOTIFICATION_ID, n);
    }

    public void update(){
        mNotificationManager.notify(NotifyManager.NOTIFICATION_ID, n);
    }

    public void setText(String title,String artist){
        notificationView.setTextViewText(R.id.notification_title, title);
        notificationView.setTextViewText(R.id.notification_artist,artist);
    }

    public void setCover(@Nullable Bitmap cover){
        if(cover!=null){
            notificationView.setImageViewBitmap(R.id.notification_cover,cover);
        }else{
            notificationView.setImageViewResource(R.id.notification_cover,R.drawable.no_cover);
        }
    }
}
