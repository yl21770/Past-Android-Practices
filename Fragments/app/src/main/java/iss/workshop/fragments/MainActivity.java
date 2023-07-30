package iss.workshop.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DetailFragment.IDetailFragment{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void itemClicked(String content){
        Intent intent = new Intent(this, DetailActivity.class);

        int id = Integer.parseInt(content);
        intent.putExtra("itemId", id);

        startActivity(intent);
    }
}