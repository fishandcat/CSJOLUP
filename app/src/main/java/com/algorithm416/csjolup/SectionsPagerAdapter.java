package com.algorithm416.csjolup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    String mParam1;
    String mParam2;
    private ArrayList<String> fragmentList;

    public SectionsPagerAdapter(FragmentManager fm, String param1, String param2) {
        super(fm);
        fragmentList = new ArrayList<>();
        mParam1 = param1;
        mParam2 = param2;
    }

    public void insertItem(String fragment_name) {
        fragmentList.add(fragment_name);
    }

    public void insertItem(String fragment_name, int i) {
        fragmentList.add(i, fragment_name);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fr = null;

        switch (fragmentList.get(position))
        {
            case "Major":
                fr = Major.newInstance(mParam1, mParam2);
                break;
            case "Liberal_arts":
                fr = Liberal_arts.newInstance(mParam1, mParam2);
                break;
            case "JolupRequirements":
                fr = JolupRequirements.newInstance(mParam1, mParam2);
                break;
        }

        return fr;
    }

    @Override
    public int getCount() {
        // Show Adapter's size
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
//        return super.getItemPosition(object);
    }
}