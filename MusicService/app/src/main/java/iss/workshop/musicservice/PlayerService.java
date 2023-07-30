package iss.workshop.musicservice;

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

public class PlayerService extends Service {
    private static final String NOTI_TITLE = "My Music Player";
    private static final String CHANNEL_ID = "MusicPlayer_Chnl";
    private static int FOREGROUND_ID = 1;

    private MediaPlayer player = null;
    private ArrayList<Song> songs = new ArrayList<>();
    private int songIndex = 0;

    public PlayerService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        startInForeground();
    }

    protected void startInForeground(){
        createNotificationChannel();

        Notification notification = createNotification(NOTI_TITLE, "Welcome!");
        startForeground(FOREGROUND_ID, notification);
    }

    protected void createNotificationChannel(){
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);

        channel.setSound(null, null);

        notificationManager.createNotificationChannel(channel);
    }

    protected Notification createNotification(String title, String text){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String action = intent.getAction();

        if(action != null){
            if(action.equals("initialize")){
                songs = (ArrayList<Song>) intent.getSerializableExtra("songs");
            }
            else if(action.equals("play")){
                songIndex = intent.getIntExtra("songIndex", 0);

                if(player != null){
                    stopSong();
                }

                startSong();
            }
            else if(action.equals("stop")){
                stopSong();
            }
        }

        return START_NOT_STICKY;
    }

    public void startSong(){
        if(player != null){
            stopSong();
        }

        Song song = songs.get(songIndex);
        int resourceId = getResources().getIdentifier(song.getFilename(), "raw", getPackageName());

        player = MediaPlayer.create(this, resourceId);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                songIndex = (songIndex + 1) % songs.size();
                startSong();
            }
        });

        player.start();
        setNotification();
    }

    public void stopSong(){
        if(player != null){
            player.stop();
            player.release();
            player = null;
        }

        clearNotification();
    }

    public void setNotification(){
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        Notification notification = createNotification(NOTI_TITLE, "Playing: " + songs.get(songIndex).getTitle());
        notificationManager.notify(FOREGROUND_ID, notification);
    }

    public void clearNotification(){
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        Notification notification = createNotification(NOTI_TITLE, "Stopped");
        notificationManager.notify(FOREGROUND_ID, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}