package com.example.musicinfo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Update extends AppCompatActivity {

       String itemURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String singer = intent.getStringExtra("singer");
        String album = intent.getStringExtra("album");
        String composer = intent.getStringExtra("composer");
        String year = intent.getStringExtra("year");
         itemURI = intent.getStringExtra("ItemURI");

        TextView tv_singer = (TextView)findViewById(R.id.title_singer);
        TextView tv_title = (TextView)findViewById(R.id.title);
        TextView tv_album = (TextView)findViewById(R.id.title_album);
        TextView tv_composer = (TextView)findViewById(R.id.title_composer);
        TextView tv_year = (TextView)findViewById(R.id.title_year);

        tv_singer.setText(singer);
        tv_title.setText(title);
        tv_album.setText(album);
        tv_composer.setText(composer);
        tv_year.setText(year);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {

            Log.d("photo write******", "external storage access Denied");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }


    }

    public void updateInfo(View view){


        EditText et_singer = (EditText)findViewById(R.id.title_singer);
        EditText et_album = (EditText)findViewById(R.id.title_album);
        EditText et_composer = (EditText)findViewById(R.id.title_composer);
        EditText et_year = (EditText)findViewById(R.id.title_year);

        Context c=getApplicationContext();
        ContentResolver mCr = c.getContentResolver();
        ContentValues values = new ContentValues();

        values.put(MediaStore.Audio.Media.ARTIST, et_singer.getText().toString());
        values.put(MediaStore.Audio.Media.ALBUM, et_album.getText().toString());
        values.put(MediaStore.Audio.Media.COMPOSER, et_composer.getText().toString());
        values.put(MediaStore.Audio.Media.YEAR, et_year.getText().toString());

         Uri myUri = Uri.parse(itemURI);
         int s = mCr.update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values, MediaStore.MediaColumns.DATA + "='" + itemURI + "'", null);

          if (s == 1){
              Toast.makeText(this, "Music update Successfully",5).show();
              Intent back = new Intent (this, MainActivity.class);
              startActivity(back);
          }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "music library permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "music library permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }
}
