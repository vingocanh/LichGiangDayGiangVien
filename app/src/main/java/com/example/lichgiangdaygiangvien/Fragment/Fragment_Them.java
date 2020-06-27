package com.example.lichgiangdaygiangvien.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lichgiangdaygiangvien.Adapter.Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.CSDL.Database_Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.CSDL.Key_Database;
import com.example.lichgiangdaygiangvien.Alarm.Lop_Create_Time;
import com.example.lichgiangdaygiangvien.MainActivity;
import com.example.lichgiangdaygiangvien.R;

import java.util.ArrayList;

public class Fragment_Them extends Fragment {

    EditText etLopMonHoc, etPhongHoc, etTietHoc;
    Spinner spTinChi;
    TextView tvNgay;
    Button btNgay, btThem, btHuy;
    Context context;
    Database_Lich_Giang_Day database_lich_giang_day;

    public Fragment_Them(Context context) {
        this.context = context;
    }

    public Fragment_Them(int contentLayoutId, Context context) {
        super(contentLayoutId);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_lich_giang_day, container, false);

        anhXa(view);
        addTinChi();
        setNgayDialog();
        xuLy();
        return view;
    }

    public void anhXa(View view){
        etLopMonHoc = (EditText) view.findViewById(R.id.etLopHocPhan);
        etPhongHoc = (EditText) view.findViewById(R.id.etPhongHoc);
        spTinChi = (Spinner) view.findViewById(R.id.spTinChi);
        etTietHoc = (EditText) view.findViewById(R.id.etTietHoc);
        tvNgay = (TextView) view.findViewById(R.id.tvNgay);
        btNgay = (Button) view.findViewById(R.id.btNgay);
        btThem = (Button) view.findViewById(R.id.btThem);
        btHuy = (Button) view.findViewById(R.id.btHuy);
        database_lich_giang_day = new Database_Lich_Giang_Day(context, Key_Database.DATABASE_NAME, null, 1);
    }

    private void addTinChi(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(10);

        spTinChi.setAdapter(new ArrayAdapter<Integer>(context, android.R.layout.simple_list_item_1, list));
    }

    private void setNgayDialog(){
        Lop_Create_Time.ngayGioDialog(btNgay, tvNgay, context);
    }

    private void xuLy(){
        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etLopMonHoc.getText().toString().isEmpty() && etTietHoc.getText().toString().isEmpty() && etPhongHoc.getText().toString().isEmpty()){
                    Toast.makeText(context, "Bạn không được để trống !!!", Toast.LENGTH_LONG).show();
                }else {
                    String lopHocPhan = etLopMonHoc.getText().toString();
                    String tinChi = spTinChi.getSelectedItem().toString();
                    String tietHoc = etTietHoc.getText().toString();
                    String phongHoc = etPhongHoc.getText().toString();
                    String ngayThang = tvNgay.getText().toString();



                    Lich_Giang_Day giang_day = new Lich_Giang_Day(lopHocPhan, tinChi, tietHoc, phongHoc, ngayThang);

                    if(giang_day != null){
                        Toast.makeText(context, "add data successfully!!!", Toast.LENGTH_SHORT).show();

                        database_lich_giang_day.them_Du_Lieu(giang_day);
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Thông báo !!!");
                    builder.setMessage("Bạn có muốn thêm ghi chú ??");

                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            etLopMonHoc.setText("");
                            etTietHoc.setText("");
                            etPhongHoc.setText("");
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.show_Data();
                            MainActivity.fbAdd.show();
                        }
                    });
                    builder.show();
                }

            }
        });

        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.fragmentManager.popBackStack();
                MainActivity.fbAdd.show();
            }
        });
    }

}
