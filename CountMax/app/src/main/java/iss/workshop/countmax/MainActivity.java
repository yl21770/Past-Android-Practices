package iss.workshop.countmax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Thread backgroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton startbtn = findViewById(R.id.startbtn);

        startbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        EditText inputbox = findViewById(R.id.inputbox);
        AppCompatButton startbtn = findViewById(R.id.startbtn);

        if(startbtn.getText().equals("START")) {
            inputbox.setText("Computing...");
            startbtn.setText("STOP");

            backgroundThread = new Thread(new Runnable() {
                int output = 0;

                @Override
                public void run() {
                    for(int i=0; i<Integer.MAX_VALUE; i++){
                        if(Thread.interrupted()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    inputbox.setText(String.valueOf(output));
                                    startbtn.setText("START");
                                }
                            });

                            return;
                        }
                        output += 1;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            inputbox.setText(String.valueOf(output));
                            startbtn.setText("START");
                        }
                    });
                }
            });

            backgroundThread.start();
        }
        else if(startbtn.getText().equals("STOP")){
            if(backgroundThread != null){
                backgroundThread.interrupt();
            }
        }
    }
}