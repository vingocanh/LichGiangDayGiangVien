package com.example.lichgiangdaygiangvien.Alarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lichgiangdaygiangvien.Adapter.Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.CSDL.Database_Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.CSDL.Key_Database;
import com.example.lichgiangdaygiangvien.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        StringBuffer tongHop = new StringBuffer();
        Database_Lich_Giang_Day database_lich_giang_day;
        ArrayList<Lich_Giang_Day> arrayList = new ArrayList<>();
        Calendar ca;
        ca = Lop_Create_Time.traVe();
        ca.add(Calendar.DATE, 1);

        database_lich_giang_day = new Database_Lich_Giang_Day(context, Key_Database.DATABASE_NAME, null, 1);
        //arrayList.addAll(database_lich_giang_day.lay_Du_Lieu(Lop_Create_Time.getStringFromCalendar(ca)));
        arrayList.addAll(database_lich_giang_day.lay_Du_Lieu(Lop_Create_Time.getStringFromCalendar(ca)));
        //arrayList.add(database_lich_giang_day.lay_Du_Lieu(Lop_Create_Time.getStringFromCalendar(ca)));

        if(arrayList.size() == 0){
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager. TYPE_NOTIFICATION );
            MediaPlayer mediaPlayer = MediaPlayer.create(context, alarmSound);
            mediaPlayer.start();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Thông báo !!!\n")
                    .setContentText("Ngày mai bạn rảnh ")
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

//            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
//            managerCompat.notify(1, builder.build());

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify((int) System.currentTimeMillis(), builder.build());


        }else {

            String lopHocPhan = "";
            String tietHoc = "";
            String phongHoc = "";

            for(Lich_Giang_Day item : arrayList){
                lopHocPhan = item.getLopHocPhan();
                tietHoc = item.getTietHoc();
                phongHoc = item.getPhongHoc();

                tongHop.append(lopHocPhan +" " +tietHoc+" " +phongHoc+"\n");
                //dlTongHop += lopHocPhan +" " +tietHoc+" "+phongHoc+"\n";
            }

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager. TYPE_NOTIFICATION );
            MediaPlayer mediaPlayer = MediaPlayer.create(context, alarmSound);
            mediaPlayer.start();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Thông báo !!!\n")
                    .setContentText(tongHop.toString())
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

//            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
//            managerCompat.notify(1, builder.build());

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify((int) System.currentTimeMillis(), builder.build());


        }

    }



}
