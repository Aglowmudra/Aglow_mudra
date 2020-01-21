package com.example.aglowmudra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
//  Created By Ajay Murari

public class LoanFragment extends Fragment {
    Button LoanApply;

    public static LoanFragment newInstance() {
        LoanFragment fragment = new LoanFragment();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view =inflater.inflate(R.layout.fragment_item_one, container, false);
       LoanApply=view.findViewById(R.id.btnApply);
       LoanApply.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
       //        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getActivity(), Login.class);
               ((MainActivity) getActivity()).startActivity(intent);




           }
       });


        return view;
    }

}
