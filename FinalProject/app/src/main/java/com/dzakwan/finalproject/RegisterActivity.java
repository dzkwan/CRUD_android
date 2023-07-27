package com.dzakwan.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dzakwan.finalproject.helper.DbHelperAkun;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;

public class RegisterActivity extends AppCompatActivity {
    public static final String FILENAME = "userID";
    EditText et_nama, et_username, et_password, et_email;
    TextView tv_login, tv_regis;
    Button btn_login, btn_regis;
    View regisLayout;
    DbHelperAkun SQLakun = new DbHelperAkun(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_regis_setting);
        getSupportActionBar().hide();

        regisLayout = (View) findViewById(R.id.loginRegisLayout);
        et_nama = (EditText) findViewById(R.id.et_nama_regispage);
        et_username = (EditText) findViewById(R.id.et_username_regispage);
        et_password = (EditText) findViewById(R.id.et_password_regispage);
        et_email = (EditText) findViewById(R.id.et_email_regispage);
        tv_login = (TextView) findViewById(R.id.tv_login_regispage);
        tv_regis = (TextView) findViewById(R.id.tv_register_regispage);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_regis = (Button) findViewById(R.id.btn_regis);

        btn_login.setVisibility(View.GONE);
        tv_regis.setVisibility(View.GONE);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    regis();
                } catch (Exception e) {
                    Log.e("regis", "" + e.toString());
                }
            }
        });
        closeKeyboardOutsideEdittext(regisLayout);
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

    private void regis() {
        if (String.valueOf(et_nama.getText()).equals(null) || String.valueOf(et_nama.getText()).equals("") ||
                String.valueOf(et_username.getText()).equals(null) || String.valueOf(et_username.getText()).equals("") ||
                String.valueOf(et_password.getText()).equals(null) || String.valueOf(et_password.getText()).equals("") ||
                String.valueOf(et_email.getText()).equals(null) || String.valueOf(et_email.getText()).equals("")) {
            Snackbar.make(regisLayout, "Please fill it correctly!", Snackbar.LENGTH_SHORT).show();
            hideKeyboard();
            et_username.clearFocus();
            et_password.clearFocus();
        } else {
            int id = SQLakun.insert(
                    et_nama.getText().toString().trim(),
                    et_username.getText().toString().trim(),
                    et_password.getText().toString().trim(),
                    et_email.getText().toString().trim()
            );
            simpanFileUserID(id);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }

    void simpanFileUserID(int id) {
        String isiFile = String.valueOf(id);
        File file = new File(getFilesDir(), FILENAME);
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}