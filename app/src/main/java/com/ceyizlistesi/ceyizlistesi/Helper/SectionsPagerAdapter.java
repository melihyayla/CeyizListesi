package com.ceyizlistesi.ceyizlistesi.Helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ceyizlistesi.ceyizlistesi.MainActivity;
import com.ceyizlistesi.ceyizlistesi.R;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();


    public SectionsPagerAdapter(FragmentManager fm) {

        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }


}
