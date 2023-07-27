package com.dzakwan.finalproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class DbHelperAkun extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "finalproject.db";
    public static final String TABLE_AKUN = "akun";
    public final String COLUMN_ID = "id";
    public final String COLUMN_NAMA = "nama";
    public final String COLUMN_USERNAME = "username";
    public final String COLUMN_PASSWORD = "password";
    public final String COLUMN_EMAIL = "email";
    public static final String TABLE_KARYAWAN = "karyawan";
    public final String COLUMN_NIP = "nip";
    public final String COLUMN_NIK = "nik";
    public final String COLUMN_DIVISI = "divisi";
    public final String COLUMN_JABATAN = "jabatan";
    public final String COLUMN_TELP = "telp";
    public final String COLUMN_USIA = "usia";
    public final String COLUMN_ALAMAT = "alamat";
    public int idUser;

    public DbHelperAkun(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE_AKUN = "CREATE TABLE IF NOT EXISTS " + TABLE_AKUN + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_NAMA + " TEXT NOT NULL, " +
                COLUMN_USERNAME + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT NOT NULL" +
                " )";
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
        db.execSQL(SQL_CREATE_TABLE_AKUN);
        db.execSQL(SQL_CREATE_TABLE_KARYAWAN);
        Log.d("create table akun", "" + SQL_CREATE_TABLE_AKUN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AKUN);
        onCreate(db);
    }

    public HashMap<String, String> getDataById(int id) {
        HashMap<String, String> map = new HashMap<String, String>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_AKUN + " WHERE " + COLUMN_ID + "='" + id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            map.put(COLUMN_ID, cursor.getString(0));
            map.put(COLUMN_NAMA, cursor.getString(1));
            map.put(COLUMN_USERNAME, cursor.getString(2));
            map.put(COLUMN_PASSWORD, cursor.getString(3));
            map.put(COLUMN_EMAIL, cursor.getString(4));
        }
        Log.d("select akunbyid", "" + map);
        database.close();
        return map;
    }

    public int isLogin(String username, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        String loginQuery = "SELECT * FROM " + TABLE_AKUN + " WHERE " +
                COLUMN_USERNAME + "='" + username + "' AND " +
                COLUMN_PASSWORD + "='" + password + "'";
        Cursor cursor = database.rawQuery(loginQuery, null);
        if (cursor.moveToFirst()) {
            database.close();
            return cursor.getInt(0);
        } else {
            database.close();
            return 0;
        }
    }

    public int insert(String nama, String username, String password, String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAMA, nama);
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_EMAIL, email);
        idUser = (int) database.insert(TABLE_AKUN, null, contentValues);
        database.close();
        return idUser;
    }

    public void update(int id, String nama, String username, String password, String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_AKUN + " SET " +
                COLUMN_NAMA + "='" + nama + "', " +
                COLUMN_USERNAME + "='" + username + "', " +
                COLUMN_PASSWORD + "='" + password + "', " +
                COLUMN_EMAIL + "='" + email + "' WHERE " +
                COLUMN_ID + "='" + id + "'";
        Log.d("update akun", "" + updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
}
