package com.mwaqaspervez.airtennis.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.mwaqaspervez.airtennis.R;
import com.mwaqaspervez.airtennis.fragment.FirstScreen;
import com.mwaqaspervez.airtennis.fragment.SecondScreen;
import com.mwaqaspervez.airtennis.fragment.ThirdScreen;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;


public class HowToPlay extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_how_to_play);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new DepthPageTransformer());

        CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.titles);
        titleIndicator.setViewPager(viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }


    public class MyAdapter extends FragmentPagerAdapter {


        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return new FirstScreen();
                case 1:
                    return new SecondScreen();
                case 2:
                    return new ThirdScreen();
            }

            return new FirstScreen();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
