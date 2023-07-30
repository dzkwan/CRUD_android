package com.dzakwan.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.dzakwan.finalproject.adapter.RecyclerAdapter;
import com.dzakwan.finalproject.helper.DbHelperKaryawan;
import com.dzakwan.finalproject.model.DataDiriKaryawan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  public static final String FILENAME = "userID";
  public static final String TAG_ID = "id";
  public static final String TAG_NAMA = "nama";
  public static final String TAG_DIVISI = "divisi";
  FloatingActionButton fab;
  List<DataDiriKaryawan> itemList = new ArrayList<>();
  public List<Integer> listIdDelete = new ArrayList<>();
  public List<Integer> getposisicheckbox = new ArrayList<>();
  RecyclerAdapter rcAdapter;
  DbHelperKaryawan SQLkaryawan;
  RecyclerView rcView;
  LinearLayoutManager linearLayoutManager;
  RecyclerView.ItemDecoration itemDecoration;
  boolean ismovepage = false;
  public Menu menu;
  public SwipeRefreshLayout layoutMain;
  public boolean backpress = false;
  public boolean backSelectItem = false;
  long lastPress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setTitle(R.string.homepage);

    SQLkaryawan = new DbHelperKaryawan(getApplicationContext());
    layoutMain = findViewById(R.id.layoutMain);

    configrecyclerview();

    fab = (FloatingActionButton) findViewById(R.id.fab_add);
    fab.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(new Intent(MainActivity.this, AddEditActivity.class));
        }
      }
    );
    layoutMain.setOnRefreshListener(
      new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
          rcAdapter = new RecyclerAdapter(MainActivity.this, itemList);
          rcView.setAdapter(rcAdapter);
          itemList.clear();
          getAllData();
        }
      }
    );

    getAllData();
  }

  void configrecyclerview() {
    rcView = findViewById(R.id.lv_karyawan);

    linearLayoutManager = new LinearLayoutManager(this);
    rcView.setLayoutManager(linearLayoutManager);

    rcAdapter = new RecyclerAdapter(this, itemList);
    rcView.setAdapter(rcAdapter);

    itemDecoration =
      new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
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
    layoutMain.setRefreshing(false);
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
      .setPositiveButton(
        "Yes",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            hapusFile();
          }
        }
      )
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
    layoutMain.setEnabled(true);
    invalidateOptionsMenu();
  }

  @Override
  public void onBackPressed() {
    long currentTime = System.currentTimeMillis();
    if (backSelectItem) {
      menu.findItem(R.id.account).setVisible(true);
      menu.findItem(R.id.selectItem).setVisible(true);
      menu.findItem(R.id.logout).setVisible(true);
      menu.findItem(R.id.icondelete).setVisible(false);
      menu.findItem(R.id.closCheckbox).setVisible(false);
      rcAdapter.setShowAllCheckbox(false);
      layoutMain.setEnabled(true);
      listIdDelete.clear();
      getposisicheckbox.clear();
      backSelectItem = false;
    } else {
      if (currentTime - lastPress > 2500) {
        Toast
          .makeText(
            getApplicationContext(),
            "Press back again to exit",
            Toast.LENGTH_SHORT
          )
          .show();
        lastPress = currentTime;
      } else {
        super.onBackPressed();
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    this.menu = menu;
    return true;
  }

  @Override
  public boolean onMenuOpened(int featureId, Menu menu) {
    rcAdapter.closeLayoutSwipe(true);

    return super.onMenuOpened(featureId, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    MenuItem account = menu.findItem(R.id.account);
    MenuItem selectItem = menu.findItem(R.id.selectItem);
    MenuItem logout = menu.findItem(R.id.logout);
    MenuItem trash = menu.findItem(R.id.icondelete);
    MenuItem closeCheckbox = menu.findItem(R.id.closCheckbox);

    if (item.getItemId() == R.id.account) {
      startActivity(new Intent(this, SettingActivity.class));
    } else if (item.getItemId() == R.id.logout) {
      showDialogKonfirmasiLogout();
    } else if (item.getItemId() == R.id.selectItem) {
      backSelectItem = true;
      account.setVisible(false);
      selectItem.setVisible(false);
      logout.setVisible(false);
      closeCheckbox.setVisible(true);
      rcAdapter.setShowAllCheckbox(true);
      layoutMain.setEnabled(false);
    } else if (item.getItemId() == R.id.closCheckbox) {
      account.setVisible(true);
      selectItem.setVisible(true);
      logout.setVisible(true);
      trash.setVisible(false);
      closeCheckbox.setVisible(false);
      rcAdapter.setShowAllCheckbox(false);
      listIdDelete.clear();
      getposisicheckbox.clear();
      layoutMain.setEnabled(true);
    } else if (item.getItemId() == R.id.icondelete) {
      if (!(listIdDelete.isEmpty())) {
        new AlertDialog.Builder(this)
          .setTitle("Delete")
          .setMessage("yakin ingin dihapus?")
          .setPositiveButton(
            "Yes",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                getposisicheckbox.sort(Collections.reverseOrder());
                for (int i = 0; i < listIdDelete.size(); i++) {
                  SQLkaryawan.delete(listIdDelete.get(i));
                  rcAdapter.notifymultipleremove(getposisicheckbox.get(i));
                }
                account.setVisible(true);
                selectItem.setVisible(true);
                logout.setVisible(true);
                trash.setVisible(false);
                closeCheckbox.setVisible(false);
                rcAdapter.setShowAllCheckbox(false);
                listIdDelete.clear();
                getposisicheckbox.clear();
              }
            }
          )
          .setNegativeButton("No", null)
          .show();
      }
    }
    return true;
  }
}
