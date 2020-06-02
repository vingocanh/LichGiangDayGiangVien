package com.example.lichgiangdaygiangvien.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lichgiangdaygiangvien.R;

public class Fragment_Show_Item extends Fragment {

    Context context;
    TextView tvLopHP, tvSoTC, tvSoTiet, tvPhongHoc;

    public Fragment_Show_Item(Context context) {
        this.context = context;
    }

    public Fragment_Show_Item(int contentLayoutId, Context context) {
        super(contentLayoutId);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_item_to_do_list, container, false);
        anhXa(view);

        return view;
    }

    private void anhXa(View view){
        tvLopHP = (TextView) view.findViewById(R.id.tvLopHP);
        tvSoTC = (TextView) view.findViewById(R.id.tvSoTC);
        tvSoTiet = (TextView) view.findViewById(R.id.tvSoTiet);
        tvPhongHoc = (TextView) view.findViewById(R.id.tvPhongHocc);
    }
}
