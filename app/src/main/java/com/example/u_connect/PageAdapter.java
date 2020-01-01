package com.example.u_connect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int tabs;

    public PageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0 :
                return new ChatFragment();
            case 1 :
                return new CallFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 0;
    }
}
