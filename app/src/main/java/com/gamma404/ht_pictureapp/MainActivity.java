package com.gamma404.ht_pictureapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_READ_STORAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            readThumbnails();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE},
                    REQUEST_READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_STORAGE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            readThumbnails();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Read Storage")
                    .setMessage("要先取得外部權限 才能顯示圖檔")
                    .setPositiveButton("OK",null)
                    .show();
        }
    }

    private void readThumbnails() {
        Log.d(TAG, "readThumbnails: ");
    }
}
