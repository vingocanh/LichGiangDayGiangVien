package com.example.lichgiangdaygiangvien.CSVData;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFile {

    Context context;
    String fileName;
    ArrayList<String[]> rows = new ArrayList<>();


    public CSVFile(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public ArrayList<String[]> readCSV() throws IOException {
        InputStream is = context.getAssets().open(fileName);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = "-";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            rows.add(row);
        }
        return rows;
    }
}
