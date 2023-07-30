package iss.workshop.bootquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(count == 0){
            TextView textView = findViewById(R.id.textdisp);
            textView.setText("Enjoy a free Quote on every boot!");
            count++;
        }
    }
}