package com.example.explicitintent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> WriteActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WriteActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == AppCompatActivity.RESULT_OK){
                        Intent data = result.getData();
                        String quote = data.getStringExtra("quote");

                        Intent intentx = new Intent(this, ShowActivity.class);
                        intentx.putExtra("quote", quote);

                        setShowBtn(intentx);
                    }
                });

        Intent intent = new Intent(this, WriteActivity.class);

        setWriteBtn(intent);
    }

    protected void setWriteBtn(Intent intent){
        Button btn = findViewById(R.id.write);
        if (btn != null)
        {
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    WriteActivity.launch(intent);
                }
            });
        }
    }

    protected void setShowBtn(Intent intent){
        Button btn = findViewById(R.id.show);
        if (btn != null)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        }
    }
}