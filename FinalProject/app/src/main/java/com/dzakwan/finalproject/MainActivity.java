package com.dzakwan.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.dzakwan.finalproject.adapter.RecyclerAdapter;
import com.dzakwan.finalproject.helper.DbHelperKaryawan;
import com.dzakwan.finalproject.model.DataDiriKaryawan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String FILENAME = "userID";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_DIVISI = "divisi";
    FloatingActionButton fab;
    List<DataDiriKaryawan> itemList = new ArrayList<>();
    RecyclerAdapter rcAdapter;
    DbHelperKaryawan SQLkaryawan;
    RecyclerView rcView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.ItemDecoration itemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.homepage);
        SQLkaryawan = new DbHelperKaryawan(getApplicationContext());

        configrecyclerview();

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddEditActivity.class));
            }
        });
        getAllData();
    }

    void configrecyclerview() {
        rcView = findViewById(R.id.lv_karyawan);

        linearLayoutManager = new LinearLayoutManager(this);
        rcView.setLayoutManager(linearLayoutManager);

        rcAdapter = new RecyclerAdapter(this, itemList);
        rcView.setAdapter(rcAdapter);

        itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcView.addItemDecoration(itemDecoration);
    }

    private void getAllData() {
        ArrayList<HashMap<String, String>> row = SQLkaryawan.getAllData();
        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String nama = row.get(i).get(TAG_NAMA);
            String divisi = row.get(i).get(TAG_DIVISI);
            DataDiriKaryawan data = new DataDiriKaryawan();
            data.setId(id);
            data.setNama(nama);
            data.setDivisi(divisi);
            itemList.add(data);
        }
        rcAdapter.notifyDataSetChanged();
    }

    void hapusFile() {
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()) {
            file.delete();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    void showDialogKonfirmasiLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("yakin ingin keluar?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapusFile();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rcAdapter = new RecyclerAdapter(this, itemList);
        rcView.setAdapter(rcAdapter);
        itemList.clear();
        getAllData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            startActivity(new Intent(this, SettingActivity.class));
        } else if (item.getItemId() == R.id.logout) {
            showDialogKonfirmasiLogout();
        }
        return true;
    }
}