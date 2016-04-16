package com.timon.gugb.musify.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;


import com.timon.gugb.musify.MainActivity;
import com.timon.gugb.musify.R;
import com.timon.gugb.musify.managers.NotifyManager;

/**
 * Created by Timon on 16.04.2016.
 */
public class WidgetNotification implements Runnable {

    Context mContext;
    NotificationManager mNotificationManager;
    String title,text;
    RemoteViews notificationView;

    public WidgetNotification(Context appContext) {
        this.mContext = appContext;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationView =  new RemoteViews(mContext.getPackageName(), R.layout.widget_notification);
    }

    @Override
    public void run() {
        makeNotification(mContext);
    }

    private void makeNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NotifyManager.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                // Set Icon
                .setSmallIcon(R.mipmap.ic_launcher)
                        // Set Ticker Message
                .setTicker("asd")
                        // Dismiss Notification
                .setAutoCancel(true)
                        // Set PendingIntent into Notification
                .setContentIntent(pendingIntent);

        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // build a complex notification, with buttons and such
            //
            builder = builder.setContent(notificationView);

        } else {
            // Build a simpler notification, without buttons
            //
            builder = builder.setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(android.R.drawable.ic_menu_gallery);
        }
        n=builder.build();
        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(NotifyManager.NOTIFICATION_ID, n);
    }

    public void setFlags(Bitmap cover,String title,String artist){
        //notificationView.setImageViewBitmap(R.id.notification_cover,cover);
        notificationView.setTextViewText(R.id.notification_title, title);
        notificationView.setTextViewText(R.id.notification_artist,artist);
    }
}
