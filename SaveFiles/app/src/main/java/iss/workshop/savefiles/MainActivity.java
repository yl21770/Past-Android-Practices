package iss.workshop.savefiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    EditText inputbox;
    Button savebtn, readbtn;
    AppCompatButton ASExternal, PExternal;

    File targetFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputbox = findViewById(R.id.inputbox);

        savebtn = findViewById(R.id.savebtn);
        readbtn = findViewById(R.id.readbtn);

        String filepath = "data";
        String filename = "mydata.txt";

        ASExternal = findViewById(R.id.ASExternal);
        PExternal = findViewById(R.id.PExternal);

        targetFile = new File(getFilesDir(), filepath + "/" + filename);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputbox.getText().toString();
                writedata(input);
            }
        });

        readbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readdata();
            }
        });

        ASExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startASExternal();
            }
        });

        PExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPExternal();
            }
        });
    }

    protected void writedata(String input){
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

    protected void startASExternal(){
        Intent intent = new Intent(this, ASExternalActivity.class);
        startActivity(intent);
        finish();
    }

    protected void startPExternal(){
        Intent intent = new Intent(this, PExternalActivity.class);
        startActivity(intent);
        finish();
    }
}