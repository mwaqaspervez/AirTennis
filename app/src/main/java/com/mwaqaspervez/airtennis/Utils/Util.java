package com.mwaqaspervez.airtennis.Utils;


import android.support.v7.app.ActionBar;

public class Util {

    public static ActionBar getBackButton(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        return actionBar;
    }
}
