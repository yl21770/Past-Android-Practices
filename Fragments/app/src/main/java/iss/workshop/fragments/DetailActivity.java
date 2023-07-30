package iss.workshop.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity implements DetailFragment.IDetailFragment{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int itemId = intent.getIntExtra("itemId", 0);

        FragmentManager fm = getSupportFragmentManager();
        DetailFragment fragment = (DetailFragment) fm.findFragmentById(R.id.fragment_detail);
        //set data id for fragment (inside fragment, it will use onStart using its data id to retrieve & set the data)
        fragment.setItemId(itemId);

        Button btn = findViewById(R.id.returnbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void itemClicked(String content){
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }
}