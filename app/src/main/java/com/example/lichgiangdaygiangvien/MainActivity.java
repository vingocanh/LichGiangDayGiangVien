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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
    Calendar ca;
    public static Fragment_Show fragment_show;
    public static ArrayList<Lich_Giang_Day> arrayList_Show;
    public static Adapter_Lich_Giang_Day adapter_giang_day_Show;
    Database_Lich_Giang_Day database_lich_giang_day;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();

        anhXa();
        xuLy();
        show_Data();
        xinQuyen();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Thoi_Gian_Thong_Bao.thongBao(this);
        }
        Thoi_Gian_Thong_Bao.thoiGianThongBao(this);


        //ThoiGian.thoiGianThongBao(Lop_Create_Time.getLongTime(), this);

    }

    private void anhXa(){
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

    private void xuLy(){
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
                ca.add(Calendar.DATE,1);
                tvThoiGian.setText(Lop_Create_Time.getStringFromCalendar(ca));
                show_Data_Ngay(ca);
            }
        });
        ivTrai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ca.add(Calendar.DATE,-1);
                tvThoiGian.setText(Lop_Create_Time.getStringFromCalendar(ca));
                show_Data_Ngay(ca);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
            fbAdd.show();
        }else {
            super.onBackPressed();
        }
    }

    private void them_DL(){
        FragmentTransaction fr = fragmentManager.beginTransaction();

        fr.replace(R.id.flMain, new Fragment_Them(this));

        fr.addToBackStack("frThem");
        fr.commit();
    }

    public static void show_Data(){
        FragmentTransaction fr = fragmentManager.beginTransaction();

        fr.replace(R.id.flMain, fragment_show);
        fr.addToBackStack("Show");

        fr.commit();
    }

    private void show_Data_Ngay(Calendar ca){
        try {
            arrayList_Show.clear();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        arrayList_Show.addAll(database_lich_giang_day.lay_Du_Lieu(Lop_Create_Time.getStringFromCalendar(ca)));
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

//    private static void readExcelFile(Context context, String filename) {
//
//        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
//        {
//            Log.w("FileUtils", "Storage not available or read only");
//            return;
//        }
//
//        try{
//            // Creating Input Stream
//            File file = new File(context.getExternalFilesDir(null), filename);
//            FileInputStream myInput = new FileInputStream(file);
//
//            // Create a POIFSFileSystem object
//            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
//
//            // Create a workbook using the File System
//            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
//
//            // Get the first sheet from workbook
//            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
//
//            /** We now need something to iterate through the cells.**/
//            Iterator<Keyboard.Row> rowIter = mySheet.rowIterator();
//
//            while(rowIter.hasNext()){
//                HSSFRow myRow = (HSSFRow) rowIter.next();
//                Iterator<Cell> cellIter = myRow.cellIterator();
//                while(cellIter.hasNext()){
//                    HSSFCell myCell = (HSSFCell) cellIter.next();
//                    Log.w("FileUtils", "Cell Value: " +  myCell.toString());
//                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }catch (Exception e){e.printStackTrace(); }
//
//        return;
//    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private void popupmenu(){
        PopupMenu pop = new PopupMenu(this, fbAdd);
        pop.getMenuInflater().inflate(R.menu.content_add, pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.itemAdd:
                        them_DL();
                        fbAdd.hide();
                        break;

                    case R.id.itemRead:
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        //intent.setAction("*/*");
                        startActivityForResult(intent, 1999);
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
        switch (requestCode){
            case 1999:
                if(resultCode == RESULT_OK){
                    String dl = data.getData().getPath();

                    Log.d("TAG1", dl);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
