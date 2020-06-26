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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thoi_Gian_Thong_Bao.thoiGianThongBao(this);

        return START_NOT_STICKY;
    }


}
