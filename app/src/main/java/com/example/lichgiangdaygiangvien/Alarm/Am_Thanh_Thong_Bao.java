package com.example.lichgiangdaygiangvien.Alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lichgiangdaygiangvien.R;

public class Am_Thanh_Thong_Bao extends Service {

    public static String CHANNEL_ID = "channelID";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager. TYPE_NOTIFICATION );
                MediaPlayer mediaPlayer = MediaPlayer.create(Am_Thanh_Thong_Bao.this, alarmSound);
                mediaPlayer.start();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Am_Thanh_Thong_Bao.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Thông báo !!!\n")
                        .setContentText("Dữ liệu")
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Am_Thanh_Thong_Bao.this);
                managerCompat.notify(1, builder.build());

            }
        };
        handler.postDelayed(runnable, 86400000);


        return START_NOT_STICKY;
    }


}
