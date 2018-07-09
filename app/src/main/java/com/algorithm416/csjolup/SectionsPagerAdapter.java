package com.algorithm416.csjolup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentList;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
    }

    public void insertItem(Fragment fragment) {
        fragmentList.add(fragment);
    }

    public void insertItem(Fragment fragment, int i) {
        fragmentList.add(i, fragment);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        // Show Adapter's size
        return fragmentList.size();
    }

}