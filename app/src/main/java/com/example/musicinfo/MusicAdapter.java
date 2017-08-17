package com.example.musicinfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

/**
 * Created by lenovo on 17-Aug-17.
 */

public class MusicAdapter extends BaseAdapter {
    private Context context;
    private List<Song> list;
    public MusicAdapter(MainActivity mainActivity, List<Song> list) {
        this.context = mainActivity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            //Set up the item list
            view = View.inflate(context, R.layout.item_music_listview, null);

            //get the song object
            holder.song = (TextView) view.findViewById(R.id.item_mymusic_song);
            holder.singer = (TextView) view.findViewById(R.id.item_mymusic_singer);
            holder.duration = (TextView) view.findViewById(R.id.item_mymusic_duration);
            holder.position = (TextView) view.findViewById(R.id.item_mymusic_postion);

            holder.title = (TextView) view.findViewById(R.id.item_mymusic_title);
            holder.album = (TextView) view.findViewById(R.id.item_mymusic_album);
           // holder.composer = (TextView) view.findViewById(R.id.item_mymusic_composer);
           // holder.year = (TextView) view.findViewById(R.id.item_mymusic_year);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //Populate result to the item listview
        holder.song.setText(list.get(i).song.toString());
        holder.singer.setText(list.get(i).singer.toString());

        holder.title.setText(list.get(i).title.toString());
        holder.album.setText(list.get(i).album.toString());
       // holder.composer.setText(list.get(i).composer.toString());
        //holder.year.setText(list.get(i).year.toString());

        //change the duration info
        int duration = list.get(i).duration;
        String time = MusicUtils.formatTime(duration);
        holder.duration.setText(time);
        holder.position.setText(i+1+"");

        return view;
    }

    class ViewHolder{
        TextView song;//Title
        TextView singer;//Singer
        TextView duration;//Duration
        TextView position;

        TextView title;
        TextView album;
        TextView composer;
        TextView year;

    }

}
