package iss.workshop.savefiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PExternalActivity extends AppCompatActivity {
    EditText inputbox;
    Button savebtn, readbtn;
    AppCompatButton returnbtn;

    File targetFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pexternal);

        inputbox = findViewById(R.id.inputbox);

        savebtn = findViewById(R.id.savebtn);
        readbtn = findViewById(R.id.readbtn);

        String filepath = "data";
        String filename = "mydata.txt";

        returnbtn = findViewById(R.id.returnbtn);

        File externalpath = getExternalFilesDir(null);
        targetFile = new File(externalpath, filepath + "/" + filename);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    String input = inputbox.getText().toString();
                    writedata(input);
                }
                else{
                    getPermission();
                }
            }
        });

        readbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readdata();
            }
        });

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMain();
            }
        });
    }

    protected boolean checkPermission(){
        int writePermit = ContextCompat.checkSelfPermission(PExternalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(writePermit == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    protected void getPermission(){
        int writeExtStrgPer = ContextCompat.checkSelfPermission(
                PExternalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ActivityCompat.requestPermissions(
                PExternalActivity.this, permissions, 1);
    }

    protected void writedata(String input){
        if(isReadOnly()){
            Toast.makeText(this, "Disk is read-only.", Toast.LENGTH_LONG).show();
            return;
        }

        if(!isAvailable()){
            Toast.makeText(this, "Disk is unavailable.", Toast.LENGTH_LONG).show();
            return;
        }

        try{
            File parent = targetFile.getParentFile();

            if(!parent.exists() && !parent.mkdir()){
                throw new IllegalStateException("Couldn't create dir: " + parent);
            }

            FileOutputStream fos = new FileOutputStream(targetFile);

            fos.write(input.getBytes());
            fos.close();

            inputbox.setText("");
            Toast.makeText(this, "Write Success.", Toast.LENGTH_LONG).show();
        }
        catch(IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Write Failed.", Toast.LENGTH_LONG).show();
        }
    }

    protected void readdata(){
        if(!isAvailable()){
            Toast.makeText(this, "Disk is unavailable.", Toast.LENGTH_LONG).show();
            return;
        }

        String data = "";

        try{
            FileInputStream fis = new FileInputStream(targetFile);
            DataInputStream dis = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));

            String temp;

            while((temp = br.readLine()) != null){
                data = data + temp;
            }

            dis.close();

            inputbox.setText(data);
            Toast.makeText(this, "Read Success.", Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Read Failed.", Toast.LENGTH_LONG).show();
        }
    }

    protected void startMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isReadOnly(){
        String storageState = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState);
    }

    private boolean isAvailable(){
        String storageState = Environment. getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(storageState);
    }
}