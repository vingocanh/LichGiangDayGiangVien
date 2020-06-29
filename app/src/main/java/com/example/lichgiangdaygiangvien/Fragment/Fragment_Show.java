package com.example.lichgiangdaygiangvien.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lichgiangdaygiangvien.CSDL.Database_Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.CSDL.Key_Database;
import com.example.lichgiangdaygiangvien.Alarm.Lop_Create_Time;
import com.example.lichgiangdaygiangvien.MainActivity;
import com.example.lichgiangdaygiangvien.R;

public class Fragment_Show extends Fragment {

    Context context;
    public static FrameLayout flShow;
    public static TextView tvKhongDL;
    public static ListView lvDuLieu;
    Database_Lich_Giang_Day database_lich_giang_day;

    public Fragment_Show(Context context) {
        this.context = context;
    }

    public Fragment_Show(int contentLayoutId, Context context) {
        super(contentLayoutId);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_to_do_list, container, false);

        anhXa(view);
        hienThiDL();
        return view;
    }

    private void anhXa(View view){
        flShow = (FrameLayout) view.findViewById(R.id.flShow);
        tvKhongDL = (TextView) view.findViewById(R.id.tvKhongDL);
        lvDuLieu = (ListView) view.findViewById(R.id.lvDanhSachGhiChu);

        database_lich_giang_day = new Database_Lich_Giang_Day(context, Key_Database.DATABASE_NAME, null, 1);
        lvDuLieu.setAdapter(MainActivity.adapter_giang_day_Show);
    }

    private void hienThiDL(){
        try {
            MainActivity.arrayList_Show.clear();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        MainActivity.arrayList_Show.addAll(database_lich_giang_day.lay_Du_Lieu(Lop_Create_Time.getStringFromCalendar(MainActivity.ca)));

        if(MainActivity.arrayList_Show.size() <= 0){
            flShow.bringChildToFront(tvKhongDL);
        }else {
            flShow.bringChildToFront(lvDuLieu);
        }


        xuLy();
        MainActivity.adapter_giang_day_Show.notifyDataSetChanged();
    }

    public static void xuLy(){
        if(MainActivity.arrayList_Show.size() <= 0){
            flShow.bringChildToFront(tvKhongDL);
        }else {
            flShow.bringChildToFront(lvDuLieu);
        }
    }

}
