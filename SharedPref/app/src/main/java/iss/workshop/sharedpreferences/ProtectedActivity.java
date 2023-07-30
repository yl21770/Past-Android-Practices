package iss.workshop.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProtectedActivity extends AppCompatActivity {
    TextView rndtxt;
    Button logoutbtn;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected);

        rndtxt = findViewById(R.id.rndtxt);
        logoutbtn = findViewById(R.id.logoutbtn);

        pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();

                finish();
            }
        });
    }
}