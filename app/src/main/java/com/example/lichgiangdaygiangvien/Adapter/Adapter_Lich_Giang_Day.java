package com.example.lichgiangdaygiangvien.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lichgiangdaygiangvien.CSDL.Database_Lich_Giang_Day;
import com.example.lichgiangdaygiangvien.CSDL.Key_Database;
import com.example.lichgiangdaygiangvien.Alarm.Lop_Create_Time;
import com.example.lichgiangdaygiangvien.R;

import java.util.ArrayList;

public class Adapter_Lich_Giang_Day extends BaseAdapter {

    Context context;
    ArrayList<Lich_Giang_Day> arrayList;

    public Adapter_Lich_Giang_Day(Context context, ArrayList<Lich_Giang_Day> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHoder{
        RadioButton rbCheck;
        TextView tvLopMonHoc, tvTietHoc, tvNgay, tvPhongHoc, tvNgaySua;
        ImageView ivShow;
        Database_Lich_Giang_Day database_lich_giang_day;
        EditText etLopMonHoc, etPhongHoc, etTietHoc;
        Spinner spTinChi;
        Button btNgaySua, btSua, btHuy;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHoder hoder;

        if(view == null){
            hoder = new ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.item_lich_giang_day, null);

            anhXa(hoder, view);

            view.setTag(hoder);
        }else {
            hoder = (ViewHoder) view.getTag();
        }

        final Lich_Giang_Day giang_day = arrayList.get(i);
        final int posstion = i;
        hoder.tvLopMonHoc.setText(giang_day.getLopHocPhan());
        hoder.tvTietHoc.setText(giang_day.getTietHoc());
        hoder.tvNgay.setText(giang_day.getNgayThang());
        hoder.tvPhongHoc.setText(giang_day.getPhongHoc());

        hoder.ivShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu(hoder, giang_day, posstion);
            }
        });

        return view;
    }

    private void anhXa(ViewHoder hoder, View view){
        hoder.rbCheck = (RadioButton) view.findViewById(R.id.rbCheck);
        hoder.tvLopMonHoc = (TextView) view.findViewById(R.id.tvLopHocPhan);
        hoder.tvTietHoc = (TextView) view.findViewById(R.id.tvTietHoc);
        hoder.tvNgay = (TextView) view.findViewById(R.id.tvNgay);
        hoder.tvPhongHoc = (TextView) view.findViewById(R.id.tvPhongHoc);
        hoder.ivShow = (ImageView) view.findViewById(R.id.ivShow);
        hoder.database_lich_giang_day = new Database_Lich_Giang_Day(context, Key_Database.DATABASE_NAME, null, 1);
    }

    private void popupMenu(final ViewHoder hoder, final Lich_Giang_Day giang_day, final int tam){
        PopupMenu pop = new PopupMenu(context, hoder.ivShow);

        pop.getMenuInflater().inflate(R.menu.context, pop.getMenu());

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.itemSua:
                        sua_DL(hoder, giang_day, tam);
                        break;
                    case R.id.itemXoa:
                        xoa_DL(hoder, tam);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        pop.show();
    }

    private void xoa_DL(final ViewHoder hoder, final int tam){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Thông báo!!!");
        builder.setMessage("Bạn có muốn xóa lịch giảng dạy không ?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Xóa lịch thành công !!!", Toast.LENGTH_SHORT).show();
                arrayList.remove(tam);
                hoder.database_lich_giang_day.xoa_Du_Lieu(tam);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    private void sua_DL(final ViewHoder hoder, final Lich_Giang_Day giang_day, final int tam){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.sua_lich_giang_day);

        xuLySua(hoder, dialog, giang_day, tam);

        addTinChi(hoder);
        setNgayDialog(hoder);

        hoder.btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lopHocPhan = hoder.etLopMonHoc.getText().toString();
                int tinChi = Integer.parseInt(hoder.spTinChi.getSelectedItem().toString());
                String tietHoc = hoder.etTietHoc.getText().toString();
                String phongHoc = hoder.etPhongHoc.getText().toString();
                String ngayThang = hoder.tvNgaySua.getText().toString();

                Lich_Giang_Day giangDaySua = new Lich_Giang_Day(lopHocPhan, tinChi, tietHoc, phongHoc, ngayThang);

                giangDaySua.setId(giang_day.getId());
                Toast.makeText(context, "Sửa lịch thành công !!!", Toast.LENGTH_SHORT).show();
                arrayList.set(tam, giangDaySua);
                hoder.database_lich_giang_day.sua_Du_Lieu(giangDaySua, giang_day.getId());
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });
        hoder.btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void xuLySua(ViewHoder hoder, Dialog dialog, Lich_Giang_Day giang_day, int tam){
        hoder.etLopMonHoc = (EditText) dialog.findViewById(R.id.etLopHocPhan);
        hoder.etPhongHoc = (EditText) dialog.findViewById(R.id.etPhongHoc);
        hoder.spTinChi = (Spinner) dialog.findViewById(R.id.spTinChi);
        hoder.etTietHoc = (EditText) dialog.findViewById(R.id.etTietHoc);
        hoder.tvNgaySua = (TextView) dialog.findViewById(R.id.tvNgaySua);
        hoder.btNgaySua = (Button) dialog.findViewById(R.id.btNgaySua);
        hoder.btSua = (Button) dialog.findViewById(R.id.btSua);
        hoder.btHuy = (Button) dialog.findViewById(R.id.btHuy);

        hoder.etLopMonHoc.setText(giang_day.getLopHocPhan());
        hoder.etTietHoc.setText(giang_day.getTietHoc());
        hoder.etPhongHoc.setText(giang_day.getPhongHoc());
        hoder.tvNgaySua.setText(giang_day.getNgayThang());
        hoder.spTinChi.setSelection(tam);
    }

    private void addTinChi(ViewHoder hoder){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(10);

        hoder.spTinChi.setAdapter(new ArrayAdapter<Integer>(context, android.R.layout.simple_list_item_1, list));
    }

    private void setNgayDialog(ViewHoder hoder){
        Lop_Create_Time.ngayGioDialog(hoder.btNgaySua, hoder.tvNgaySua, context);
    }



}
