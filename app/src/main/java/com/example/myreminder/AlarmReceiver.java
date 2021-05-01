package com.example.myreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        int notificationID=intent.getIntExtra("notoficationID",0);
        String message=intent.getStringExtra("todo");
        Intent mainIntent=new Intent(context,MainActivity.class);
        PendingIntent contentIntent=PendingIntent.getActivity(context,0,mainIntent,0);
        NotificationManager myNotoficationManager=(NotificationManager) context.getSystemService
                (Context.NOTIFICATION_SERVICE);
        Notification.Builder builder= new Notification.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("Its time")
                .setContentText(message).setWhen(System.currentTimeMillis()).setAutoCancel(true)
                .setContentIntent(contentIntent);
        myNotoficationManager.notify(notificationID,builder.build());


    }
}
