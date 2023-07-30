package iss.workshop.listview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class MyAdaptor extends ArrayAdapter<Object> {
    private final Context context;

    protected String[] images, captions;

    public MyAdaptor(Context context, String[] images, String[] captions){
        super(context, R.layout.row);

        this.context = context;
        this.images = images;
        this.captions = captions;

        addAll(new Object[images.length]);
    }

    public View getView(int pos, View view, @NotNull ViewGroup parent){
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.imageview);

        int id = context.getResources().getIdentifier(images[pos], "drawable", context.getPackageName());
        imageView.setImageResource(id);

        TextView textView = view.findViewById(R.id.txtview);
        textView.setText(captions[pos]);

        return view;
    }
}
