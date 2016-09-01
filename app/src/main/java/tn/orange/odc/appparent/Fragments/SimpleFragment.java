package tn.orange.odc.appparent.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import tn.orange.odc.appparent.AppelActivity;
import tn.orange.odc.appparent.CalenderActivity;
import tn.orange.odc.appparent.MainActivity;
import tn.orange.odc.appparent.R;
import tn.orange.odc.appparent.RTCActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class SimpleFragment extends android.support.v4.app.Fragment {


    String title="";
    private  ImageView imgCentral;
    private  Button btnCentral;

    public static android.support.v4.app.Fragment newInstance(String message) {
        SimpleFragment fragment = new SimpleFragment(message);
        return fragment;
    }
    public SimpleFragment() {
        // Required empty public constructor
    }
    public SimpleFragment(String title) {
        this.title = title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        //img central et button
        imgCentral = (ImageView) rootView.findViewById(R.id.imageCenter);
        btnCentral = (Button) rootView.findViewById(R.id.btCentral);
        imgCentral.setContentDescription(title);
        btnCentral.setText(title);
        if(title.equalsIgnoreCase("Piloter")){
            imgCentral.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.piloter));
        }else if(title.equalsIgnoreCase("Appeler")){
            imgCentral.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.appeler_menu));
        }else if(title.equalsIgnoreCase("Rappeler")){
            imgCentral.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.rappeler));
        }else if(title.equalsIgnoreCase("Commander")){
            imgCentral.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.commander));
        }
        btnCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnCentral.getText().toString().equalsIgnoreCase("Piloter")){
                    Toast.makeText(getActivity(), "Piloter", Toast.LENGTH_SHORT).show();
                   // Intent i = new Intent(getActivity(),RTCActivity.class);
                 //  getActivity().startActivity(i);
                } else if(btnCentral.getText().toString().equalsIgnoreCase("Commander")){
                    Toast.makeText(getActivity(), "Commander", Toast.LENGTH_SHORT).show();
                   // Intent i = new Intent(getActivity(),RTCActivity.class);
                  //  getActivity().startActivity(i);
                }else if(btnCentral.getText().toString().equalsIgnoreCase("Appeler")){
                    Toast.makeText(getActivity(), "Appeler", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(getActivity(),AppelActivity.class);
                   // getActivity().startActivity(i);
                }else if(btnCentral.getText().toString().equalsIgnoreCase("Rappeler")){
                    Toast.makeText(getActivity(), "Rappeler", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),CalenderActivity.class);
                    getActivity().startActivity(i);
                }
            }
        });
        return rootView;
    }

}
