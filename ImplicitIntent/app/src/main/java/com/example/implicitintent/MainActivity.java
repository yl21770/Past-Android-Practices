package com.example.implicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBtn1();
        setBtn2();
        setBtn3();
        setBtn4();
    }

    protected void setBtn1(){
        Button btn = findViewById(R.id.visit);

        if (btn != null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("https://www.nus.edu.sg/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
            });
        }
    }

    protected void setBtn2(){
        Button btn = findViewById(R.id.locate);

        if (btn != null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("geo:1.2907, 103.7727");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
            });
        }
    }

    protected void setBtn3(){
        Button btn = findViewById(R.id.call);

        if (btn != null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("tel:(+65)97619182");
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
            });
        }
    }

    protected void setBtn4(){
        Button btn = findViewById(R.id.email);

        if (btn != null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("mailto:yl21770@gmail.com");
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

                    intent.putExtra(Intent.EXTRA_SUBJECT, "Testing");
                    intent.putExtra(Intent.EXTRA_TEXT, "Testing success.");

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
            });
        }
    }
}