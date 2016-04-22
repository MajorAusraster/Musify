package com.timon.gugb.musify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.timon.gugb.musify.utils.WidgetNotification;

/**
 * Created by Timon on 18.04.2016.
 */
public class EventReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Event Receiver","Received an event"+intent.getAction());

        String action=intent.getAction();

       if(action.equals(WidgetNotification.INTENT_NEXT_BUTTON)){
           /*Next button of the notification was clicked*/

       }

    }
}
