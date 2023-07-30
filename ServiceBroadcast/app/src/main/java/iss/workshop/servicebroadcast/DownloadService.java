package iss.workshop.servicebroadcast;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends Service {
    private static final String imgUrl = "https://p4.wallpaperbetter.com/wallpaper/291/663/679/stones-background-stones-spa-wallpaper-preview.jpg";

    public DownloadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String action = intent.getAction();

        if(action != null){
            if(action.equals("download")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        File destFile = new File(dir, "download.jpg");

                        if(downloadImg(destFile)){
                            Intent broadcastIntent = new Intent();
                            intent.setAction("download_completed");
                            intent.putExtra("location", destFile.getAbsolutePath());

                            sendBroadcast(broadcastIntent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Download failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                }).start();
            }
        }

        return START_NOT_STICKY;
    }

    public boolean downloadImg(File destFile){
        try {
            URL url = new URL(imgUrl);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(destFile);
            byte[] buf = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = in.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
            out.close();
            in.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}