package com.example.musicinfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<Song> list = new ArrayList<>();
    private MusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * Initialize view
     */
    private void initView() {
        mListView = (ListView) findViewById(R.id.main_listview);

        //Check permission
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {

            Log.d("photo read******", "external storage access Denied");
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }

        //Scan Music array to the list
        list = MusicUtils.getMusicData(this);

        adapter = new MusicAdapter(this, list);
        adapter.notifyDataSetChanged();

        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intentUpdate = new Intent(MainActivity.this,Update.class);
              ///intentUpdate.putExtra("ItemURI", mListView.getAdapter().getItemId(position));
                intentUpdate.putExtra("title", list.get(position).title);
                intentUpdate.putExtra("singer", list.get(position).singer);
                intentUpdate.putExtra("album", list.get(position).album);
                intentUpdate.putExtra("composer", list.get(position).composer);
                intentUpdate.putExtra("year", list.get(position).year);
                intentUpdate.putExtra("ItemURI", list.get(position).path);

                startActivity(intentUpdate);
            }


        });



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
