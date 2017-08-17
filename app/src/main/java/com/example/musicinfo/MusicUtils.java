package com.example.musicinfo;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by lenovo on 17-Aug-17.
 */

public class MusicUtils {

    /**
     * Scan all songs available in the device
     */
    public static List<Song> getMusicData(Context context) {
        List<Song> list = new ArrayList<Song>();
        // Check MusicUtils
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Song song = new Song();
                song.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                song.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

                song.title = isNotNullOrEmpty(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)))? cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)):"N/A" ;
                song.album = isNotNullOrEmpty(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)))? cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)):"N/A";
                song.composer = isNotNullOrEmpty(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.COMPOSER)))? cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.COMPOSER)): "N/A";
                song.year = isNotNullOrEmpty(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)))? cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)): "N/A";
                song.itemUri = MediaStore.Audio.Media.getContentUriForPath(song.path).toString() + "/" + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));

                if (song.size > 1000 * 800) {

                    // Display the Singer and Song title with -
                    if (song.song.contains("-")) {
                        String[] str = song.song.split("-");
                        song.singer = str[0];
                        song.song = str[1];
                    }
                    list.add(song);
                }
            }
            // Release resource
            cursor.close();
        }

        return list;
    }



    /**
     * Format Song Duration properly
     */
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;

        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }

    }

    private static boolean isNotNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}
