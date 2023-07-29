package com.dzakwan.finalproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.dzakwan.finalproject.helper.DbHelperAkun;
import com.google.android.material.snackbar.Snackbar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {

  public static final String FILENAME = "userID";
  TextView tv_id, tv_nama, tv_username, tv_password, tv_email;
  EditText et_nama, et_username, et_password, et_email;
  Button btn_login, btn_simpan;
  View layoutTextButton, layoutSetting;
  ImageView iv_logo;
  DbHelperAkun SQLakun = new DbHelperAkun(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_regis_setting);
    getSupportActionBar().setTitle(R.string.setting);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    layoutSetting = (View) findViewById(R.id.loginRegisLayout);
    tv_id = (TextView) findViewById(R.id.tv_id_regispage);
    tv_nama = (TextView) findViewById(R.id.tv_nama_regispage);
    tv_username = (TextView) findViewById(R.id.tv_username);
    tv_password = (TextView) findViewById(R.id.tv_password);
    tv_email = (TextView) findViewById(R.id.tv_email);
    et_nama = (EditText) findViewById(R.id.et_nama_regispage);
    et_username = (EditText) findViewById(R.id.et_username_regispage);
    et_password = (EditText) findViewById(R.id.et_password_regispage);
    et_email = (EditText) findViewById(R.id.et_email_regispage);
    iv_logo = (ImageView) findViewById(R.id.iv_logo);
    btn_login = (Button) findViewById(R.id.btn_login);
    btn_simpan = (Button) findViewById(R.id.btn_regis);
    layoutTextButton = (View) findViewById(R.id.layoutTextButton);

    tv_nama.setVisibility(View.VISIBLE);
    tv_username.setVisibility(View.VISIBLE);
    tv_password.setVisibility(View.VISIBLE);
    tv_email.setVisibility(View.VISIBLE);
    iv_logo.setVisibility(View.GONE);
    btn_login.setVisibility(View.GONE);
    layoutTextButton.setVisibility(View.GONE);

    bacaDataUser(FILENAME);

    btn_simpan.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          edit();
        }
      }
    );
  }

  void bacaDataUser(String fileName) {
    File file = new File(getFilesDir(), fileName);
    if (file.exists()) {
      StringBuilder text = new StringBuilder();
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        text.append(line);
        br.close();
      } catch (Exception e) {
        System.out.println("Error" + e.getMessage());
      }
      String data = text.toString();
      HashMap<String, String> dataUser = SQLakun.getDataById(
        Integer.parseInt(data)
      );
      tv_id.setText(dataUser.get(SQLakun.COLUMN_ID));
      et_nama.setText(dataUser.get(SQLakun.COLUMN_NAMA));
      et_username.setText(dataUser.get(SQLakun.COLUMN_USERNAME));
      et_password.setText(dataUser.get(SQLakun.COLUMN_PASSWORD));
      et_email.setText(dataUser.get(SQLakun.COLUMN_EMAIL));
    } else {
      finish();
      Snackbar
        .make(
          layoutSetting,
          "error data user tidak tersimpan",
          Snackbar.LENGTH_SHORT
        )
        .show();
    }
  }

  public void hideKeyboard() {
    InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(
        Activity.INPUT_METHOD_SERVICE
      );
    if (inputMethodManager.isAcceptingText()) {
      inputMethodManager.hideSoftInputFromWindow(
        this.getCurrentFocus().getWindowToken(),
        0
      );
    }
  }

  void showDialogKonfirmasiEdit() {
    new AlertDialog.Builder(this)
      .setTitle("Edit Data")
      .setMessage("yakin ingin menyimpan data?")
      .setPositiveButton(
        "Yes",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            SQLakun.update(
              Integer.parseInt(tv_id.getText().toString().trim()),
              et_nama.getText().toString().trim(),
              et_username.getText().toString().trim(),
              et_password.getText().toString().trim(),
              et_email.getText().toString().trim()
            );
            finish();
          }
        }
      )
      .setNegativeButton("No", null)
      .show();
  }

  private void edit() {
    if (
      String.valueOf(et_nama.getText()).equals(null) ||
      String.valueOf(et_nama.getText()).equals("") ||
      String.valueOf(et_username.getText()).equals(null) ||
      String.valueOf(et_username.getText()).equals("") ||
      String.valueOf(et_password.getText()).equals(null) ||
      String.valueOf(et_password.getText()).equals("") ||
      String.valueOf(et_email.getText()).equals(null) ||
      String.valueOf(et_email.getText()).equals("")
    ) {
      Snackbar
        .make(layoutSetting, "Please fill it correctly!", Snackbar.LENGTH_SHORT)
        .show();
    } else {
      hideKeyboard();
      showDialogKonfirmasiEdit();
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
