package com.dzakwan.finalproject.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelperKaryawan extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "finalproject.db";
    public static final String TABLE_KARYAWAN = "karyawan";
    public final String COLUMN_ID = "id";
    public final String COLUMN_NAMA = "nama";
    public final String COLUMN_NIP = "nip";
    public final String COLUMN_NIK = "nik";
    public final String COLUMN_DIVISI = "divisi";
    public final String COLUMN_JABATAN = "jabatan";
    public final String COLUMN_TELP = "telp";
    public final String COLUMN_USIA = "usia";
    public final String COLUMN_ALAMAT = "alamat";

    public DbHelperKaryawan(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        creatTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KARYAWAN);
        creatTable(db);
    }

    void creatTable(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE_KARYAWAN = "CREATE TABLE IF NOT EXISTS " + TABLE_KARYAWAN + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_NAMA + " TEXT NOT NULL, " +
                COLUMN_NIP + " INTEGER NOT NULL, " +
                COLUMN_NIK + " INTEGER NOT NULL, " +
                COLUMN_DIVISI + " TEXT NOT NULL, " +
                COLUMN_JABATAN + " TEXT NOT NULL, " +
                COLUMN_TELP + " INTEGER NOT NULL, " +
                COLUMN_USIA + " INTEGER NOT NULL, " +
                COLUMN_ALAMAT + " TEXT NOT NULL" +
                " )";
        db.execSQL(SQL_CREATE_TABLE_KARYAWAN);
        Log.d("create table karyawan", "" + SQL_CREATE_TABLE_KARYAWAN);
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE_KARYAWAN;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_NAMA, cursor.getString(1));
                map.put(COLUMN_NIP, cursor.getString(2));
                map.put(COLUMN_NIK, cursor.getString(3));
                map.put(COLUMN_DIVISI, cursor.getString(4));
                map.put(COLUMN_JABATAN, cursor.getString(5));
                map.put(COLUMN_TELP, cursor.getString(6));
                map.put(COLUMN_USIA, cursor.getString(7));
                map.put(COLUMN_ALAMAT, cursor.getString(8));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        Log.d("select all data", "" + wordList);
        database.close();
        return wordList;
    }

    public HashMap<String, String> getDataById(int id) {
        HashMap<String, String> map = new HashMap<String, String>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_KARYAWAN + " WHERE " + COLUMN_ID + "='" + id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            map.put(COLUMN_ID, cursor.getString(0));
            map.put(COLUMN_NAMA, cursor.getString(1));
            map.put(COLUMN_NIP, cursor.getString(2));
            map.put(COLUMN_NIK, cursor.getString(3));
            map.put(COLUMN_DIVISI, cursor.getString(4));
            map.put(COLUMN_JABATAN, cursor.getString(5));
            map.put(COLUMN_TELP, cursor.getString(6));
            map.put(COLUMN_USIA, cursor.getString(7));
            map.put(COLUMN_ALAMAT, cursor.getString(8));
        }
        Log.d("select akunbyid", "" + map);
        database.close();
        return map;
    }

    public void insert(String nama, long nip, long nik, String divisi, String jabatan, long telp, int usia, String alamat) {
        SQLiteDatabase database = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TABLE_KARYAWAN + " (nama, nip, nik, divisi, jabatan, telp, usia, alamat) VALUES ('" +
                nama + "', " + nip + ", " + nik + ", '" + divisi + "', '" + jabatan + "', " + telp + ", " + usia + ", '" + alamat + "')";
        database.execSQL(insertQuery);
        database.close();
    }

    public void update(int id, String nama, long nip, long nik, String divisi, String jabatan, long telp, long usia, String alamat) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_KARYAWAN + " SET " +
                COLUMN_NAMA + "='" + nama + "', " +
                COLUMN_NIP + "=" + nip + ", " +
                COLUMN_NIK + "=" + nik + ", " +
                COLUMN_DIVISI + "='" + divisi + "', " +
                COLUMN_JABATAN + "='" + jabatan + "', " +
                COLUMN_TELP + "=" + telp + ", " +
                COLUMN_USIA + "=" + usia + ", " +
                COLUMN_ALAMAT + "='" + alamat + "' WHERE " +
                COLUMN_ID + "='" + id + "'";
        Log.d("update akun", "" + updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_KARYAWAN + " WHERE " + COLUMN_ID + "='" + id + "'";
        Log.d("delete sqlite", "" + deleteQuery);
        database.execSQL(deleteQuery);
        database.close();
    }
}
