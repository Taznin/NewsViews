package com.example.taznin.newsviews.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taznin.newsviews.R;

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.about_fragment,container,false);
        TextView txt=(TextView)view.findViewById(R.id.txt_about);
        String about="Welcome to NewsViews"+"\n"+"Its a News Reader App using NewsApi.org."
                +"This app fetches latest top news headlines also different news sources in Home section."+"\n"
                +"Also it use an API for interesting facts about numbers."
                +"Put any number or date(DD/MM) in search bar,it will fetch the TRIVIA of that number or the history of the date!.";
        txt.setText(about);
        return view;
    }


}
