package com.dzakwan.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import java.io.File;

public class SplashActivity extends AppCompatActivity {

  public static final String FILENAME = "userID";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    getSupportActionBar().hide();
    delay();
  }

  void delay() {
    new Handler()
      .postDelayed(
        () -> {
          bacaFileUserID(FILENAME);
        },
        2500
      );
  }

  void bacaFileUserID(String fileName) {
    File file = new File(getFilesDir(), fileName);
    if (file.exists()) {
      startActivity(new Intent(this, MainActivity.class));
      finish();
    } else {
      startActivity(new Intent(this, LoginActivity.class));
      finish();
    }
  }
}
