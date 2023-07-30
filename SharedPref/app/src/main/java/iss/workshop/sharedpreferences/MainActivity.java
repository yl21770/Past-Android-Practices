package iss.workshop.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText usertxt, pswdtxt;
    Button loginbtn;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usertxt = findViewById(R.id.usertxt);
        pswdtxt = findViewById(R.id.pswdtxt);
        loginbtn = findViewById(R.id.loginbtn);

        String username;
        String password;

        pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if(pref.contains("username") && pref.contains("password")){
            username = pref.getString("username", null);
            password = pref.getString("password", null);

            usertxt.setText(username);
            pswdtxt.setText(password);
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usertxt.getText().toString();
                String password = pswdtxt.getText().toString();

                if(loginCheck(username, password)){
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();

                    startProtectedActivity();
                }
            }
        });
    }

    private boolean loginCheck(String username, String password){
        if(username.equals("DipSA") && password.equals("DipSA")){
            return true;
        }
        else{
            TextView usererror = findViewById(R.id.usererror);

            if(!username.equals("DipSA")){
                usererror.setText("Incorrect username.");
            }
            else{
                usererror.setText("");
            }

            if(!password.equals("DipSA")){
                TextView pswderror = findViewById(R.id.pswderror);
                pswderror.setText("Incorrect password.");
            }
            return false;
        }
    }

    private void startProtectedActivity(){
        Intent intent = new Intent(this, ProtectedActivity.class);
        startActivity(intent);
        finish();
    }
}