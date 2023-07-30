package iss.workshop.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ListFragment extends Fragment {
    private DetailFragment.IDetailFragment iDetailFragment;

    public ListFragment() {
        // Required empty public constructor
    }

    //link fragment with an Activity
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        iDetailFragment = (DetailFragment.IDetailFragment) context;
    }

    public void sendDataToActivity(String content){
        //ask Activity to generate a Toast msg with clicked content
        iDetailFragment.itemClicked(content);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        DataItem[] items = DataService.dataItems;

        View view = getView();

        for(int i = 1; i < 4; i++){
            int id = view.getResources().getIdentifier("detail" +i+ "Btn", "id", getContext().getPackageName());
            Button btn = (Button) view.findViewById(id);

            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendDataToActivity(String.valueOf(finalI));
                }
            });
        }
    }
}