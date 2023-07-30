package iss.workshop.musicservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Song> songs = createSongList();

        setList(songs);
        setBtn();
        initializeSvc(songs);
    }

    protected void setList(ArrayList<Song> songs){
        SongAdaptor adaptor = new SongAdaptor(this, songs);

        ListView listView = findViewById(R.id.songlist);
        if(listView != null){
            listView.setAdapter(adaptor);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                    Intent intent = new Intent(MainActivity.this, PlayerService.class);

                    intent.setAction("play");
                    intent.putExtra("songIndex", pos);

                    startService(intent);
                }
            });
        }
    }

    protected void setBtn(){
        Button stopbtn = findViewById(R.id.stopbtn);
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayerService.class);

                intent.setAction("stop");

                startService(intent);
            }
        });
    }

    protected void initializeSvc(ArrayList<Song> songs){
        Intent intent = new Intent(MainActivity.this, PlayerService.class);

        intent.setAction("initialize");
        intent.putExtra("songs", songs);

        startService(intent);
    }

    protected ArrayList<Song> createSongList(){
        ArrayList<Song> songs = new ArrayList<>();

        Field[] fields = R.raw.class.getFields();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        for(int i = 0; i < fields.length; i++){
            Uri songPath = Uri.parse("android.resource://" + getPackageName() + "/raw/" + fields[i].getName());
            mmr.setDataSource(getApplication(), songPath);

            songs.add(new Song(
                    mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM) + ", " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
                    fields[i].getName())
            );
        }

        return songs;
    }
}