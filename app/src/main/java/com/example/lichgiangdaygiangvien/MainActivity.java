package com.example.lichgiangdaygiangvien;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lichgiangdaygiangvien.Adapter.Adapter_Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.Adapter.Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.Alarm.Am_Thanh_Thong_Bao;
import com.example.lichgiangdaygiangvien.Alarm.Thoi_Gian_Thong_Bao;
import com.example.lichgiangdaygiangvien.CSDL.Database_Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.CSDL.Key_Database;
import com.example.lichgiangdaygiangvien.Alarm.Lop_Create_Time;
import com.example.lichgiangdaygiangvien.Fragment.Fragment_Show;
import com.example.lichgiangdaygiangvien.Fragment.Fragment_Them;
import com.example.lichgiangdaygiangvien.PostDL.InterfaceTruyenDL;
import com.example.lichgiangdaygiangvien.PostDL.ReadData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvGiangDay, tvThoiGian;
    ImageView ivTrai, ivPhai;
    public static FloatingActionButton fbAdd;
    public static FrameLayout flMain;
    public static FragmentManager fragmentManager;
    public static Calendar ca;
    public static Fragment_Show fragment_show;
    public static ArrayList<Lich_Giang_Day> arrayList_Show;
    public static Adapter_Lich_Giang_Day adapter_giang_day_Show;
    Database_Lich_Giang_Day database_lich_giang_day;
    Uri uri;
    public  static int REQUES_CODE = 123;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();


        xinQuyen();
        anhXa();

        xuLy();
        show_Data();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Thoi_Gian_Thong_Bao.thongBao(this);
        }
        Intent intent = new Intent(this, Am_Thanh_Thong_Bao.class);
        this.startService(intent);

    }
    private void anhXa() {
        tvGiangDay = (TextView) findViewById(R.id.tvGiangDay);
        tvThoiGian = (TextView) findViewById(R.id.tvThoiGian);
        ivPhai = (ImageView) findViewById(R.id.ivPhai);
        ivTrai = (ImageView) findViewById(R.id.ivTrai);
        fbAdd = (FloatingActionButton) findViewById(R.id.fbAdd);
        flMain = (FrameLayout) findViewById(R.id.flMain);
        fragment_show = new Fragment_Show(this);
        arrayList_Show = new ArrayList<>();
        adapter_giang_day_Show = new Adapter_Lich_Giang_Day(this, arrayList_Show);
        database_lich_giang_day = new Database_Lich_Giang_Day(this, Key_Database.DATABASE_NAME, null, 1);

        tvThoiGian.setText(Lop_Create_Time.ngayThangNam());
    }

    private void xuLy() {
        ca = Lop_Create_Time.traVe();

        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupmenu();

            }
        });
        ivPhai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ca.add(Calendar.DATE, 1);
                tvThoiGian.setText(Lop_Create_Time.getStringFromCalendar(ca));
                //show_Data_Ngay(ca);
                show_Data_Read(ca);
            }
        });
        ivTrai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ca.add(Calendar.DATE, -1);
                tvThoiGian.setText(Lop_Create_Time.getStringFromCalendar(ca));
                //show_Data_Ngay(ca);
                show_Data_Read(ca);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            fbAdd.show();
        } else {
            super.onBackPressed();
        }
    }
    private void them_DL() {
        FragmentTransaction fr = fragmentManager.beginTransaction();

        fr.replace(R.id.flMain, new Fragment_Them(this));

        fr.addToBackStack("frThem");
        fr.commit();
    }
    public static void show_Data() {
        FragmentTransaction fr = fragmentManager.beginTransaction();

        fr.replace(R.id.flMain, fragment_show);
        fr.addToBackStack("Show");

        fr.commit();
    }
    private void show_Data_Read(Calendar start){
        try {
            arrayList_Show.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        arrayList_Show.addAll(database_lich_giang_day.lay_Du_Lieu(Lop_Create_Time.getStringFromCalendar(start)));
        Fragment_Show.xuLy();
        adapter_giang_day_Show.notifyDataSetChanged();
    }
    private void xinQuyen() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
    private void popupmenu() {
        PopupMenu pop = new PopupMenu(this, fbAdd);
        pop.getMenuInflater().inflate(R.menu.content_add, pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.itemAdd:
                        them_DL();
                        fbAdd.hide();
                        break;

                    case R.id.itemRead:
                        arrayList_Show.clear();
                        database_lich_giang_day.xoaData();

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/*");

                        startActivityForResult(intent, REQUES_CODE);
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

        pop.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUES_CODE){
            if(resultCode == RESULT_OK){
                uri = data.getData();
                readExcelData(uri);
            }
        }
    }

    public void readExcelData(Uri uri){
        ReadData read = new ReadData(this);
        read.execute(uri);
        read.setTruyenDL(new InterfaceTruyenDL() {
            @Override
            public void truyenDL(ArrayList<Lich_Giang_Day> arrayList) {
                for (Lich_Giang_Day item : arrayList){
                    database_lich_giang_day.them_Du_Lieu(item);

                    Log.d("abcdef", item.toString());
                }
                show_Data_Read(ca);
            }
        });
    }


}
