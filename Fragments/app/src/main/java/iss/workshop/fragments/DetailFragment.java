package iss.workshop.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {
    private int itemId;
    public void setItemId(int itemId){
        this.itemId = itemId;
    }

    public interface IDetailFragment{
        void itemClicked(String content);
    }
    private IDetailFragment iDetailFragment;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    //link fragment with an Activity
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        iDetailFragment = (IDetailFragment) context;
    }

    public void sendDataToActivity(String content){
        //ask Activity to generate a Toast msg with clicked content
        iDetailFragment.itemClicked(content);
    }

    @Override
    public void onStart(){
        super.onStart();

        //get Data Bundle in fragment
        Bundle bundle = getArguments();
        if(bundle != null){
            itemId = bundle.getInt("itemId");
        }

        //get View components in fragment
        View view = getView();
        if(view != null){
            //get data using itemId
            DataItem item = DataService.getItem(itemId);

            //display data on View
            TextView title = view.findViewById(R.id.textTitle);
            title.setText(item.getName());

            //set clickable View (for title only)
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView = (TextView) view;
                    sendDataToActivity(textView.getText().toString());
                }
            });
        }
    }
}