package iss.workshop.permissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_AUDIO = 1;
    private final int RECEIVE_SMS = 2;

    private String[] audio_perm = {Manifest.permission.RECORD_AUDIO};
    private String[] sms_perm = {Manifest.permission.RECEIVE_SMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton audiobtn = findViewById(R.id.audio_perm);
        AppCompatButton smsbtn = findViewById(R.id.sms_perm);

        audiobtn.setOnClickListener(this::onClick);

        smsbtn.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();

        if(id == R.id.audio_perm){
            if (checkSelfPermission(audio_perm[0]) == PackageManager.PERMISSION_GRANTED) {
                Toast msg = Toast.makeText(this, "Permission to record audio already granted", Toast.LENGTH_LONG);
                msg.show();
            }
            else{
                ActivityCompat.requestPermissions(this, audio_perm, REQUEST_AUDIO);
            }
        }
        else if(id == R.id.sms_perm){
            if(checkSelfPermission(sms_perm[0]) == PackageManager.PERMISSION_GRANTED){
                Toast msg = Toast.makeText(this, "Permission to receive SMS already granted", Toast.LENGTH_LONG);
                msg.show();
            }
            else{
                ActivityCompat.requestPermissions(this, sms_perm, RECEIVE_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_AUDIO){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast msg = Toast.makeText(this, "Granted permission to record audio", Toast.LENGTH_LONG);
                msg.show();
            }
            else{
                Toast msg = Toast.makeText(this, "Denied permission to record audio", Toast.LENGTH_LONG);
                msg.show();
            }
        }
        else if(requestCode == RECEIVE_SMS){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast msg = Toast.makeText(this, "Granted permission to receive SMS", Toast.LENGTH_LONG);
                msg.show();
            }
            else{
                Toast msg = Toast.makeText(this, "Denied permission to receive SMS", Toast.LENGTH_LONG);
                msg.show();
            }
        }
    }
}