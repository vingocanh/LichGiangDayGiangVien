package com.example.lichgiangdaygiangvien.Adapter;

public class Lich_Giang_Day {

    private int id;
    private String lopHocPhan;
    private int tinChi;
    private String tietHoc;
    private String phongHoc;
    private String ngayThang;

    public Lich_Giang_Day() {
    }

    public Lich_Giang_Day(String lopHocPhan, int tinChi, String tietHoc, String phongHoc, String ngayThang) {
        this.lopHocPhan = lopHocPhan;
        this.tinChi = tinChi;
        this.tietHoc = tietHoc;
        this.phongHoc = phongHoc;
        this.ngayThang = ngayThang;
    }

    public Lich_Giang_Day(int id, String lopHocPhan, int tinChi, String tietHoc, String phongHoc, String ngayThang) {
        this.id = id;
        this.lopHocPhan = lopHocPhan;
        this.tinChi = tinChi;
        this.tietHoc = tietHoc;
        this.phongHoc = phongHoc;
        this.ngayThang = ngayThang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLopHocPhan() {
        return lopHocPhan;
    }

    public void setLopHocPhan(String lopHocPhan) {
        this.lopHocPhan = lopHocPhan;
    }

    public int getTinChi() {
        return tinChi;
    }

    public void setTinChi(int tinChi) {
        this.tinChi = tinChi;
    }

    public String getTietHoc() {
        return tietHoc;
    }

    public void setTietHoc(String tietHoc) {
        this.tietHoc = tietHoc;
    }

    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    @Override
    public String toString() {
        return "Lich_Giang_Day{" +
                "lopHocPhan='" + lopHocPhan + '\'' +
                ", tinChi=" + tinChi +
                ", tietHoc='" + tietHoc + '\'' +
                ", phongHoc='" + phongHoc + '\'' +
                ", ngayThang='" + ngayThang + '\'' +
                '}';
    }
}
