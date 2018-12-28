package com.example.taznin.newsviews.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taznin.newsviews.R;

public class NumberFragment extends Fragment {
    private TextView txt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_number,container,false);
        txt=(TextView)view.findViewById(R.id.txtNumber);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String myInt = bundle.getString("NUMBER_KEY");
            txt.setText(myInt);
        }
        return view;
    }


}
