package iss.workshop.savedinstancestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnStart;
    Button btnScore;

    TextView txtScore;

    int mSeconds;
    int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScore = findViewById(R.id.clickbtn);
        btnStart = findViewById(R.id.startbtn);

        txtScore = findViewById(R.id.scoretxt);
        txtScore.setText(String.valueOf(mScore));

        mSeconds = 0;
        mScore = 0;

        txtScore.setText("Your score: " + mScore);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeconds = 30;
                mScore = 0;

                txtScore.setText("Your score: " + mScore);
                btnScore.setEnabled(true);
            }
        });

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScore++;
                txtScore.setText("Your score: " + mScore);
            }
        });

        runTimer();
    }

    private void runTimer() {
        final TextView txtTime = findViewById(R.id.timetxt);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                txtTime.setText("Time left: " + mSeconds);

                if (mSeconds > 0) {
                    btnScore.setEnabled(true);
                    mSeconds--;
                } else {
                    btnScore.setEnabled(false);
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("time", mSeconds);
        outState.putInt("score", mScore);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState){
        super.onRestoreInstanceState(savedState);

        mSeconds = savedState.getInt("time");
        mScore = savedState.getInt("score");

        txtScore.setText(mScore);
    }
}