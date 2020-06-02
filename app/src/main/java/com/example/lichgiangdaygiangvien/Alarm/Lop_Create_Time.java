package com.example.lichgiangdaygiangvien.Alarm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Lop_Create_Time {

    public static Calendar calendar;
    public static DateFormat fmtDateAndTime;
    public static DatePickerDialog.OnDateSetListener day;

    public static String ngayThangNam(){
        calendar  = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        int nam = calendar.get(Calendar.YEAR);
        int thang = calendar.get(Calendar.MONTH)+1 ;
        int ngay = calendar.get(Calendar.DATE);

        String ngayThangNam = String.format("%02d",ngay) +"/"+ String.format("%02d",thang) +"/"+ String.format("%04d",nam);

        return ngayThangNam;
    }

    public static String getStringFromCalendar(Calendar calendar){
        int nam = calendar.get(Calendar.YEAR);
        int thang = calendar.get(Calendar.MONTH)+1 ;
        int ngay = calendar.get(Calendar.DATE);

        String ngayThangNam = String.format("%02d",ngay) +"/"+ String.format("%02d",thang) +"/"+ String.format("%04d",nam);

        return ngayThangNam;
    }

    public static Calendar traVe(){
        calendar  = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        return  calendar;
    }


    public static void ngayGioDialog(Button btNgay, final TextView tvNgay, final Context context){
        fmtDateAndTime = DateFormat.getDateTimeInstance();
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        day = new DatePickerDialog.OnDateSetListener()
        {
            public void onDateSet(DatePicker view, int year, int month, int day) { ;
                String ngayTN =  String.format("%02d",day) +"/"+ String.format("%02d",month+1) +"/"+ String.format("%04d",year);
                tvNgay.setText(ngayTN);
            }
        };


        btNgay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(context, day,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE)).show();
            }
        });


    }

    public static long getLongTime() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
//        if (Calendar.getInstance().after(calendar)) {
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }

        return calendar.getTimeInMillis();
    }
}
