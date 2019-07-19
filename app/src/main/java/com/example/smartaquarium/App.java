package com.example.smartaquarium;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String PEMBERITAHUAN = "PEMBERITAHUAN";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotification();
    }
    private void createNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    PEMBERITAHUAN,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("PEMBERITAHUAN 1");



            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
