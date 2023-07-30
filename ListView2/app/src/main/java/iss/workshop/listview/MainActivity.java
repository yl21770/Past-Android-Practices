package iss.workshop.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private final String[] images = {"honoka", "chika", "kanade", "kuroneko", "kobato", "nadeko", "rika", "sakura", "shiina", "keke"};
    private final String[] captions = {"Honoka", "Chika", "Kanade", "Kuroneko", "Kobato", "Nadeko", "Rika", "Sakura", "Shiina", "Keke"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdaptor adaptor = new MyAdaptor(this, images, captions);

        ListView listView = findViewById(R.id.listview);
        if(listView != null){
            listView.setAdapter(adaptor);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> av, View view, int pos, long id){
        TextView textView = view.findViewById(R.id.txtview);
        String str = textView.getText().toString();

        Toast msg = Toast.makeText(this, str, Toast.LENGTH_LONG);
        msg.show();
    }
}