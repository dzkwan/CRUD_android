package com.dzakwan.finalproject.model;

public class DataDiriKaryawan {
    private String id,nama,nip,nik,divisi,jabatan,telp,usia,alamat;

    public DataDiriKaryawan(){}
    public DataDiriKaryawan(String id,String nama,String nip,String nik,String divisi,String jabatan,String telp,String usia,String alamat){
        this.id = id;
        this.nama = nama;
        this.nip = nip;
        this.nik = nik;
        this.divisi = divisi;
        this.jabatan = jabatan;
        this.telp = telp;
        this.usia = usia;
        this.alamat = alamat;
    }

    public String getId() {
        return id;
    }
    public String getNama() {
        return nama;
    }
    public String getNip() {
        return nip;
    }
    public String getNik() {
        return nik;
    }
    public String getDivisi() {
        return divisi;
    }
    public String getJabatan() {
        return jabatan;
    }
    public String getTelp() {
        return telp;
    }
    public String getUsia() {
        return usia;
    }
    public String getAlamat() {
        return alamat;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setNip(String nip) {
        this.nip = nip;
    }
    public void setNik(String nik) {
        this.nik = nik;
    }
    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }
    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
    public void setTelp(String telp) {
        this.telp = telp;
    }
    public void setUsia(String usia) {
        this.usia = usia;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
