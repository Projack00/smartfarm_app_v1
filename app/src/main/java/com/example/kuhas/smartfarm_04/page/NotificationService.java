package com.example.kuhas.smartfarm_04.page;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.kuhas.smartfarm_04.R;
import com.example.kuhas.smartfarm_04.models.Alert_Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Alert_equipment");
    private Timer mTime;
    String content = "Success ";
    String title = "Show";

    private int alarm_Value_count, status;
    private String equipment, time;
    Context ct;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();
        mTime = new Timer();
        mTime.schedule(timerTask, 2000, 2 * 10000);
        super.onCreate();
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            refreshData();
            Log.i("notification", "notiShow =>" + status);
            if (status == 0) {

                reference.child("status").setValue(1);

                showNotificationAlert();
            }
//            showNotificationAlert();
        }
    };

    private void showNotificationAlert() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
// Check Version Android

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle(time) // title for notification
                    .setSound(Uri.EMPTY)
                    .setVibrate(null)
                    .setContentText(equipment)// message for notification
                    .setAutoCancel(true); // clear notification after click

            Intent intent = new Intent(getApplicationContext(), AlertNotification.class);
            intent.putExtra("time", time);
            intent.putExtra("equipment", equipment);
            intent.putExtra("alarm_Value_count", alarm_Value_count);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pi);
            mNotificationManager.notify(0, mBuilder.build());


    }

    public void refreshData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alarm_Value_count = Integer.parseInt(dataSnapshot.child("alarm_Value_count").getValue().toString());
                equipment = dataSnapshot.child("equipment").getValue().toString();
                time = dataSnapshot.child("time").getValue().toString();
                status = Integer.parseInt(dataSnapshot.child("status").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("notification", "Fail console=>");
            }
        });


    }

    @Override
    public void onDestroy() {
//        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

        try {
//            mTime.cancel();
//            timerTask.cancel();

        } catch (Exception e) {
            e.printStackTrace();
//            Intent intent = new Intent("com.example.kuhas");
//            intent.putExtra("value", "torestore");
//            sendBroadcast(intent);
        }
    }
}
