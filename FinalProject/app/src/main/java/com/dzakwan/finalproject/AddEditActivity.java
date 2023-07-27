package com.dzakwan.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dzakwan.finalproject.adapter.RecyclerAdapter;
import com.dzakwan.finalproject.helper.DbHelperKaryawan;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class AddEditActivity extends AppCompatActivity {

    public static TextView tv_nama, tv_nip, tv_nik, tv_divisi, tv_jabatan, tv_telp, tv_usia, tv_alamat;
    public static EditText et_nama, et_nip, et_nik, et_divisi, et_jabatan, et_telp, et_usia, et_alamat;
    ExtendedFloatingActionButton fab;
    View addEditLayout;
    DbHelperKaryawan SQLkaryawan;
    public static String id;
    public static int isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Log.d("AddEditactivity", "activity jalan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SQLkaryawan = new DbHelperKaryawan(this);
        addEditLayout = findViewById(R.id.addEditLayout);
        tv_nama = findViewById(R.id.tv_nama_basic);
        tv_nip = findViewById(R.id.tv_nip);
        tv_nik = findViewById(R.id.tv_nik);
        tv_divisi = findViewById(R.id.tv_divisi);
        tv_jabatan = findViewById(R.id.tv_jabatan);
        tv_telp = findViewById(R.id.tv_telp);
        tv_usia = findViewById(R.id.tv_usia);
        tv_alamat = findViewById(R.id.tv_alamat);
        et_nama = findViewById(R.id.et_nama_basic);
        et_nip = findViewById(R.id.et_nip);
        et_nik = findViewById(R.id.et_nik);
        et_divisi = findViewById(R.id.et_divisi);
        et_jabatan = findViewById(R.id.et_jabatan);
        et_telp = findViewById(R.id.et_telp);
        et_usia = findViewById(R.id.et_usia);
        et_alamat = findViewById(R.id.et_alamat);
        fab = findViewById(R.id.fab_add_karyawan);
        id = getIntent().getStringExtra(RecyclerAdapter.TAG_ID);
        isEdit = getIntent().getIntExtra(RecyclerAdapter.TAG_ISEDIT, 0);
        Log.d("id", "" + id);
        Log.d("isEdit", "" + isEdit);

        if (id == null || id.equals("")) {
            setTitle("Add Karyawan");
            tv_nama.setVisibility(View.GONE);
            tv_nip.setVisibility(View.GONE);
            tv_nik.setVisibility(View.GONE);
            tv_divisi.setVisibility(View.GONE);
            tv_jabatan.setVisibility(View.GONE);
            tv_telp.setVisibility(View.GONE);
            tv_usia.setVisibility(View.GONE);
            tv_alamat.setVisibility(View.GONE);
        } else {
            if (isEdit == 0) {
                setTitle("Detail Karyawan");
                et_nama.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                et_nip.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                et_nik.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                et_divisi.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                et_jabatan.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                et_telp.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                et_usia.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                et_alamat.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                et_nama.setTextColor(Color.BLACK);
                et_nip.setTextColor(Color.BLACK);
                et_nik.setTextColor(Color.BLACK);
                et_divisi.setTextColor(Color.BLACK);
                et_jabatan.setTextColor(Color.BLACK);
                et_telp.setTextColor(Color.BLACK);
                et_usia.setTextColor(Color.BLACK);
                et_alamat.setTextColor(Color.BLACK);
                et_nama.setEnabled(false);
                et_nip.setEnabled(false);
                et_nik.setEnabled(false);
                et_divisi.setEnabled(false);
                et_jabatan.setEnabled(false);
                et_telp.setEnabled(false);
                et_usia.setEnabled(false);
                et_alamat.setEnabled(false);
                fab.setVisibility(View.GONE);
                bacaDataKaryawan();
            } else {
                setTitle("Edit Karyawan");
                bacaDataKaryawan();
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == null || id.equals("")) {
                    addKaryawan();
                } else {
                    editKaryawan(Integer.parseInt(AddEditActivity.id));
                }
            }
        });
        closeKeyboardOutsideEdittext(addEditLayout);
        Log.d("AddEditactivity", "activity selesai");
    }

    void bacaDataKaryawan() {
        HashMap<String, String> data = SQLkaryawan.getDataById(Integer.parseInt(AddEditActivity.id));
        et_nama.setText(data.get(SQLkaryawan.COLUMN_NAMA));
        et_nip.setText(data.get(SQLkaryawan.COLUMN_NIP));
        et_nik.setText(data.get(SQLkaryawan.COLUMN_NIK));
        et_divisi.setText(data.get(SQLkaryawan.COLUMN_DIVISI));
        et_jabatan.setText(data.get(SQLkaryawan.COLUMN_JABATAN));
        et_telp.setText("0" + data.get(SQLkaryawan.COLUMN_TELP));
        et_usia.setText(data.get(SQLkaryawan.COLUMN_USIA));
        et_alamat.setText(data.get(SQLkaryawan.COLUMN_ALAMAT));
    }

    private void addKaryawan() {
        if (String.valueOf(et_nama.getText()).equals(null) || String.valueOf(et_nama.getText()).equals("") ||
                String.valueOf(et_nip.getText()).equals(null) || String.valueOf(et_nip.getText()).equals("") ||
                String.valueOf(et_nik.getText()).equals(null) || String.valueOf(et_nik.getText()).equals("") ||
                String.valueOf(et_divisi.getText()).equals(null) || String.valueOf(et_divisi.getText()).equals("") ||
                String.valueOf(et_jabatan.getText()).equals(null) || String.valueOf(et_jabatan.getText()).equals("") ||
                String.valueOf(et_telp.getText()).equals(null) || String.valueOf(et_telp.getText()).equals("") ||
                String.valueOf(et_usia.getText()).equals(null) || String.valueOf(et_usia.getText()).equals("") ||
                String.valueOf(et_alamat.getText()).equals(null) || String.valueOf(et_alamat.getText()).equals("")) {
            Snackbar.make(addEditLayout, "Please fill it correctly!", Snackbar.LENGTH_SHORT).show();
            hideKeyboard();
        } else {
            SQLkaryawan.insert(
                    et_nama.getText().toString().trim(),
                    Long.parseLong(et_nip.getText().toString().trim()),
                    Long.parseLong(et_nik.getText().toString().trim()),
                    et_divisi.getText().toString().trim(),
                    et_jabatan.getText().toString().trim(),
                    Long.parseLong(et_telp.getText().toString().trim()),
                    Integer.parseInt(et_usia.getText().toString().trim()),
                    et_alamat.getText().toString().trim()
            );
            finish();
        }
    }

    private void editKaryawan(int id) {
        if (String.valueOf(et_nama.getText()).equals(null) || String.valueOf(et_nama.getText()).equals("") ||
                String.valueOf(et_nip.getText()).equals(null) || String.valueOf(et_nip.getText()).equals("") ||
                String.valueOf(et_nik.getText()).equals(null) || String.valueOf(et_nik.getText()).equals("") ||
                String.valueOf(et_divisi.getText()).equals(null) || String.valueOf(et_divisi.getText()).equals("") ||
                String.valueOf(et_jabatan.getText()).equals(null) || String.valueOf(et_jabatan.getText()).equals("") ||
                String.valueOf(et_telp.getText()).equals(null) || String.valueOf(et_telp.getText()).equals("") ||
                String.valueOf(et_usia.getText()).equals(null) || String.valueOf(et_usia.getText()).equals("") ||
                String.valueOf(et_alamat.getText()).equals(null) || String.valueOf(et_alamat.getText()).equals("")) {
            Snackbar.make(addEditLayout, "Please fill it correctly!", Snackbar.LENGTH_SHORT).show();
            hideKeyboard();
        } else {
            SQLkaryawan.update(
                    id,
                    et_nama.getText().toString().trim(),
                    Long.parseLong(et_nip.getText().toString().trim()),
                    Long.parseLong(et_nik.getText().toString().trim()),
                    et_divisi.getText().toString().trim(),
                    et_jabatan.getText().toString().trim(),
                    Long.parseLong(et_telp.getText().toString().trim()),
                    Integer.parseInt(et_usia.getText().toString().trim()),
                    et_alamat.getText().toString().trim()
            );
            finish();
        }
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    this.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public void closeKeyboardOutsideEdittext(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard();
                    return false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}