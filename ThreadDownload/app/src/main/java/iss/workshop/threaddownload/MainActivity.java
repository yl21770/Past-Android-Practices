package iss.workshop.threaddownload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Thread bckgthread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        AppCompatButton btn = findViewById(R.id.btn);
        ImageView imageView = findViewById(R.id.imagedisp);

        if(btn.getText().equals("Download")){
            bckgthread = new Thread(new Runnable() {
                @Override
                public void run() {
                    File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File destFile = new File(dir, "download.jpg");

                    if(downloadImage("https://p4.wallpaperbetter.com/wallpaper/291/663/679/stones-background-stones-spa-wallpaper-preview.jpg", destFile)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap = BitmapFactory.decodeFile(destFile.getAbsolutePath());

                                imageView.setImageBitmap(bitmap);
                                btn.setText("Remove");
                            }
                        });
                    }
                }
            });

            bckgthread.start();
        }
        else if(btn.getText().equals("Remove")){
            imageView.setImageBitmap(null);
            btn.setText("Download");
        }
    }

    protected boolean downloadImage(String imgUrl, File destFile){
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
}