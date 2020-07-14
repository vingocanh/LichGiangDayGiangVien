package com.example.lichgiangdaygiangvien.PostDL;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.lichgiangdaygiangvien.Adapter.Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.Alarm.Lop_Create_Time;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadData extends AsyncTask<Uri, HSSFWorkbook, ArrayList<Lich_Giang_Day>> {
    Context context;
    InterfaceTruyenDL truyenDL;

    public ReadData(Context context) {
        this.context = context;
    }

    public void setTruyenDL(InterfaceTruyenDL truyenDL) {
        this.truyenDL = truyenDL;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Lich_Giang_Day> doInBackground(Uri... uris) {
        ArrayList<Lich_Giang_Day> arrayList = new ArrayList<>();
        try {
            final String regex = "\\d+-\\d+-\\d+";
            //final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE );
            Pattern pattern1 = Pattern.compile(regex);

            InputStream myInput;

            myInput = context.getContentResolver().openInputStream(uris[0]);
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);


            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            Iterator rowIter = mySheet.rowIterator();

            for(int i=0;i<10;i++){
                Row row = (Row) rowIter.next();
            }
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            String start1,end1;
            String[] ngayThangNam, ngayThangNam2;
            int thu = 2;

            while (rowIter.hasNext()){
                Row row = (Row) rowIter.next();
                Matcher matcher = pattern1.matcher(row.getCell(1).getStringCellValue());
                if(row.getCell(1).getStringCellValue().isEmpty()){
                    continue;
                }
                if(row.getCell(1).toString().startsWith("TUẦN") ){

                    thu = 2;
                    matcher.find();
                    start1 = matcher.group(0);
                    ngayThangNam = matcher.group(0).split("-");
                    start.set(Integer.parseInt(ngayThangNam[2]), Integer.parseInt(ngayThangNam[1])-1, Integer.parseInt(ngayThangNam[0]));

                    matcher.find();
                    end1 = matcher.group(0);
                    ngayThangNam2 = matcher.group(0).split("-");
                    end.set(Integer.parseInt(ngayThangNam2[2]), Integer.parseInt(ngayThangNam2[1])-1, Integer.parseInt(ngayThangNam2[0]));

                    continue;
                }else {
                    int tam = (int)Double.parseDouble(String.valueOf(row.getCell(3).getNumericCellValue())) - thu;
                    //Log.d("Test", tam+"");
                    start.add( Calendar.DATE,tam);

                    String lopHocPhan = row.getCell(1).toString();
                    String tinChi = row.getCell(2).toString();
                    String tietHoc = row.getCell(4).toString();
                    String phongHoc = row.getCell(5).toString();
                    String ngayTN = Lop_Create_Time.getStringFromCalendar(start);

                    Lich_Giang_Day lich_giang_day = new Lich_Giang_Day(lopHocPhan, tinChi, tietHoc, phongHoc, ngayTN);

                    arrayList.add(lich_giang_day);
                    thu = (int) row.getCell(3).getNumericCellValue();
                }
            }
        } catch (Exception e) {
            Log.e("main", "error "+ e.toString());
        }

        return arrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Lich_Giang_Day> lich_giang_days) {
        super.onPostExecute(lich_giang_days);

        truyenDL.truyenDL(lich_giang_days);
    }
}
