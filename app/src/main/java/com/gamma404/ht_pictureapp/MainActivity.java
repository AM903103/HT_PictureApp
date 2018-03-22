package com.gamma404.ht_pictureapp;

import android.Manifest;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_READ_STORAGE = 3;
    private SimpleCursorAdapter adapter;

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
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    private void readThumbnails() {
        Log.d(TAG, "readThumbnails: ");
        GridView gridView = findViewById(R.id.grid);
        String[] from = {MediaStore.Images.Thumbnails.DATA,
                MediaStore.Images.Media.DISPLAY_NAME};
        int[] to = new int[]{R.id.thumb_image, R.id.thumb_text};
        adapter = new SimpleCursorAdapter(this,
                R.layout.thumb_item, null, from, to, 0);
        gridView.setAdapter(adapter);
        getLoaderManager().initLoader(3, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        return new CursorLoader(this, uri,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
