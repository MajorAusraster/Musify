package com.timon.gugb.musify.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.timon.gugb.musify.MainActivity;
import com.timon.gugb.musify.R;
import com.timon.gugb.musify.managers.NotifyManager;

/**
 * Created by Timon on 16.04.2016.
 */
public class DisplayNotification implements Runnable {

    Context mContext;
    NotificationManager mNotificationManager;
    String title,text;
    Boolean sticky;

    public DisplayNotification(Context appContext, String title, String text,Boolean sticky) {
        this.mContext = appContext;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        this.title=title;
        this.text=text;
        this.sticky=sticky;
    }

    @Override
    public void run() {
        makeNotification(mContext);
    }

    private void makeNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NotifyManager.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_audiotrack_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                ;
        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            n = builder.build();
        } else {
            n = builder.getNotification();
        }

        if(sticky){
            n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        }

        mNotificationManager.notify(NotifyManager.NOTIFICATION_ID, n);
    }
}
