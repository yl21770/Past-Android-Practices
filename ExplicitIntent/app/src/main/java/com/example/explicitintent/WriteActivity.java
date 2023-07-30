package com.example.explicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class WriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        setOKBtn();
    }

    protected void setOKBtn(){
        Button btn = findViewById(R.id.submit);
        if (btn != null)
        {
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    EditText input = findViewById(R.id.input);

                    Intent response = new Intent();
                    response.putExtra("quote", input.getText().toString());

                    setResult(RESULT_OK, response);
                    finish();
                }
            });
        }
    }
}