package com.example.lichgiangdaygiangvien.Alarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class Thoi_Gian_Thong_Bao {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void thongBao(Context context){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            String name = "ten";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel  channel = new NotificationChannel("CHANNEL_ID", name, importance);
            notificationManager.createNotificationChannel(channel);


        }
    }


    public static void thoiGianThongBao(Context context){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 20);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        //tồn tại khi thoát ứng dụng
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }



}
