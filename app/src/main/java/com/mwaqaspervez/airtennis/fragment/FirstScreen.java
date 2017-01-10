package com.mwaqaspervez.airtennis.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mwaqaspervez.airtennis.R;
import com.mwaqaspervez.airtennis.activity.HowToPlay;

public class FirstScreen extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_first_screen, container, false);

        return v;
    }
}
