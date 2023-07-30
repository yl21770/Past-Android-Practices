package iss.workshop.servicebroadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action == null){
                return;
            }

            if(action.equals("download_completed")){
                String location = intent.getStringExtra("location");

                if(location == null){
                    return;
                }

                Bitmap bitmap = BitmapFactory.decodeFile(location);
                setImage(bitmap);
            }
        }
    };

    protected void registerReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("download_completed");

        registerReceiver(receiver, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBtn();
    }

    protected void setBtn(){
        Button downloadbtn = findViewById(R.id.btn);
        downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(downloadbtn.getText().equals("Download")){
                    Intent intent = new Intent(MainActivity.this, DownloadService.class);
                    intent.setAction("download");

                    startService(intent);
                }
                else if(downloadbtn.getText().equals("Remove")){
                    ImageView imageView = findViewById(R.id.imagedisp);
                    imageView.setImageBitmap(null);

                    downloadbtn.setText("Download");
                }
            }
        });
    }

    protected void setImage(Bitmap bitmap){
        ImageView imageView = findViewById(R.id.imagedisp);
        imageView.setImageBitmap(bitmap);

        Button btn = findViewById(R.id.btn);
        btn.setText("Remove");
    }
}