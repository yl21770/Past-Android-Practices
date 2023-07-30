package com.example.mymusicservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

public class MyMusicService extends Service {
    private static final String TITLE = "My Music Service";
    private static final String CHANNEL_ID = "MyMusicService_Channel";
    private static final int FOREGD_NOTIFY_ID = 1;
    private MediaPlayer player = null;
    private int currSongIdx = 0;
    private ArrayList<String> songFnames = new ArrayList<>();

    public MyMusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startInForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action != null) {
            if (action.equalsIgnoreCase("play")) {
                currSongIdx = intent.getIntExtra("song_idx", 0);

                if (player != null) {
                    stopSong();
                }
                playSong();
            }
            else if (action.equalsIgnoreCase("stop")) {
                stopSong();
            }
            else if (action.equalsIgnoreCase("init_songs")) {
                songFnames = (ArrayList<String>) intent.getSerializableExtra("song_fnames");
                int i = 1;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    protected void playSong() {
        if (player != null) {
            stopSong();
        }

        String currSongFname = songFnames.get(currSongIdx);
        int resId = getResources().getIdentifier(currSongFname, "raw", getPackageName());
        player = MediaPlayer.create(this, resId);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                currSongIdx = (currSongIdx + 1) % songFnames.size();
                playSong();
            }
        });
        player.start();

        onPlay();
    }

    protected void stopSong() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }

        onStop();
    }

    protected void onPlay()
    {
        String currSongFname = songFnames.get(currSongIdx);

        NotificationManager notifMgr = getSystemService(NotificationManager.class);
        Notification notification = createNotification(TITLE,
                "Playing \"" + currSongFname + ".mp3\"");
        notifMgr.notify(FOREGD_NOTIFY_ID, notification);
    }

    protected void onStop() {
        NotificationManager notifMgr = getSystemService(NotificationManager.class);
        Notification notification = createNotification(TITLE, "");
        notifMgr.notify(FOREGD_NOTIFY_ID, notification);
    }

    protected void startInForeground() {
        createNotificationChannel();

        Notification notification = createNotification(TITLE, "");
        startForeground(FOREGD_NOTIFY_ID, notification);
    }

    protected void createNotificationChannel() {
        NotificationManager notifMgr = getSystemService(NotificationManager.class);

        NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT);
        notifMgr.createNotificationChannel(serviceChannel);
    }

    protected Notification createNotification(String title, String text) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_adaptive_fore)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}