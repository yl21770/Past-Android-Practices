package com.example.explicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();

        String quote = intent.getStringExtra("quote");

        TextView view = findViewById(R.id.display);

        view.setText(quote);

        intent = new Intent(this, MainActivity.class);
        setOKBtn(intent);
    }

    protected void setOKBtn(Intent intent){
        Button btn = findViewById(R.id.ok);
        if (btn != null)
        {
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}