package com.example.lichgiangdaygiangvien.CSDL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lichgiangdaygiangvien.Adapter.Lich_Giang_Day;

import java.util.ArrayList;

public class Database_Lich_Giang_Day extends SQLiteOpenHelper {

    public Database_Lich_Giang_Day(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String truyVan = "CREATE TABLE IF NOT EXISTS "+Key_Database.TABALE_NAME+"("+Key_Database.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Key_Database.LOP_HOC_PHAN+" VARCHAR(200) , "
                +Key_Database.TIN_CHI+" VARCHAR(200), "
                +Key_Database.TIET_HOC+" VARCHAR(50) , "
                +Key_Database.PHONG_HOC+" VARCHAR(100), "
                +Key_Database.NGAY+" VARCHAR(100) )";

        sqLiteDatabase.execSQL(truyVan);
    }

    public void them_Du_Lieu(Lich_Giang_Day giang_day){
        try {
            SQLiteDatabase database = getWritableDatabase();
            ContentValues content = new ContentValues();

            content.put(Key_Database.LOP_HOC_PHAN, giang_day.getLopHocPhan());
            content.put(Key_Database.TIN_CHI, giang_day.getTinChi());
            content.put(Key_Database.TIET_HOC, giang_day.getTietHoc());
            content.put(Key_Database.PHONG_HOC, giang_day.getPhongHoc());
            content.put(Key_Database.NGAY, giang_day.getNgayThang());

            database.insert(Key_Database.TABALE_NAME, null, content);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public ArrayList<Lich_Giang_Day> lay_Du_Lieu(String ngayThang){
        try {
            SQLiteDatabase database = getReadableDatabase();
            ArrayList<Lich_Giang_Day> arrayList = new ArrayList<>();
            String layData = "SELECT * FROM "+Key_Database.TABALE_NAME+ " WHERE "+Key_Database.NGAY+ " = "+ "'"+ngayThang+"'";


            Cursor cursor = database.rawQuery(layData, null);
            while (cursor.moveToNext()){
                Lich_Giang_Day giang_day = new Lich_Giang_Day();

                giang_day.setId(cursor.getInt(0));
                giang_day.setLopHocPhan(cursor.getString(1));
                giang_day.setTinChi(cursor.getString(2));
                giang_day.setTietHoc(cursor.getString(3));
                giang_day.setPhongHoc(cursor.getString(4));
                giang_day.setNgayThang(cursor.getString(5));

                arrayList.add(giang_day);
            }

            database.close();
            return arrayList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Lich_Giang_Day> lay_Du_Lieu_all(){
        try {
            SQLiteDatabase database = getReadableDatabase();
            ArrayList<Lich_Giang_Day> arrayList = new ArrayList<>();
            String layData = "SELECT * FROM "+Key_Database.TABALE_NAME;


            Cursor cursor = database.rawQuery(layData, null);
            while (cursor.moveToNext()){
                Lich_Giang_Day giang_day = new Lich_Giang_Day();

                giang_day.setId(cursor.getInt(0));
                giang_day.setLopHocPhan(cursor.getString(1));
                giang_day.setTinChi(cursor.getString(2));
                giang_day.setTietHoc(cursor.getString(3));
                giang_day.setPhongHoc(cursor.getString(4));
                giang_day.setNgayThang(cursor.getString(5));

                arrayList.add(giang_day);
            }

            database.close();
            return arrayList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int xoa_Du_Lieu(int id){
        try {
            SQLiteDatabase database = getWritableDatabase();

            return database.delete(Key_Database.TABALE_NAME, Key_Database.ID+ " = ?", new String[]{id+""});
        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

    public int sua_Du_Lieu(Lich_Giang_Day giang_day, int id){
        try {

            SQLiteDatabase database = getWritableDatabase();
            ContentValues content = new ContentValues();

            content.put(Key_Database.LOP_HOC_PHAN, giang_day.getLopHocPhan());
            content.put(Key_Database.TIN_CHI, giang_day.getTinChi());
            content.put(Key_Database.TIET_HOC, giang_day.getTietHoc());
            content.put(Key_Database.PHONG_HOC, giang_day.getPhongHoc());
            content.put(Key_Database.NGAY, giang_day.getNgayThang());

            return database.update(Key_Database.TABALE_NAME, content, Key_Database.ID+ " = ?", new String[]{id +""});

        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

    public void xoaData(){
        SQLiteDatabase database = getWritableDatabase();
        String xoa = " DELETE FROM "+Key_Database.TABALE_NAME;

        database.execSQL(xoa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
