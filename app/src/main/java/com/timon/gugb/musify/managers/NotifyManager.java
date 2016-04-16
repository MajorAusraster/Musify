package com.timon.gugb.musify.managers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;

import com.timon.gugb.musify.MainActivity;
import com.timon.gugb.musify.R;
import com.timon.gugb.musify.utils.DisplayNotification;
import com.timon.gugb.musify.utils.WidgetNotification;

/**
 * Created by Timon on 16.04.2016.
 */
public class NotifyManager {

    public final static int NOTIFICATION_ID=21;

    private Context context;
    private WidgetNotification widgetNotification;

    public NotifyManager(Context context){
        this.context=context;
    }

    public void postMessage(String title,String text){
        Handler mHandler = new Handler();
        Context appContext = context.getApplicationContext();
        mHandler.post(new DisplayNotification(appContext,title,text, false));
    }

    public void postWidgetNotification(){
        Handler mHandler = new Handler();
        Context appContext = context.getApplicationContext();
        widgetNotification=new WidgetNotification(appContext);
        mHandler.post(widgetNotification);
    }

    public WidgetNotification getWidgetNotification(){
        return widgetNotification;
    }




}//end class
