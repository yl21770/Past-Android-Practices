package iss.workshop.musicservice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SongAdaptor extends ArrayAdapter<Object> {
    private final Context context;
    private ArrayList<Song> songs;

    public SongAdaptor(Context context, ArrayList<Song> songs){
        super(context, R.layout.listrows);

        this.context = context;
        this.songs = songs;

        addAll(new Object[songs.size()]);
    }

    public View getView(int pos, View view, @NotNull ViewGroup parent){
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listrows, parent, false);
        }

        Song song = songs.get(pos);

        TextView title = view.findViewById(R.id.songtitle);
        title.setText(song.getTitle());

        TextView desc = view.findViewById(R.id.songdesc);
        desc.setText(song.getDesc());

        return view;
    }
}
