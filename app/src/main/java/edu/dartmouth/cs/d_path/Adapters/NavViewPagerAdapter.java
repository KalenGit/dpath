package edu.dartmouth.cs.d_path.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jameslee on 5/24/18.
 */

public class NavViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = NavViewPagerAdapter.class.getSimpleName();;
    private ArrayList<Fragment> fragments;

    private static final int ALLCOURSES = 0;
    private static final int COURSES = 1;
    private static final int SAVED= 2;
    private static final int PROFILE = 3;
    private static final String TAB_ALLCOURSES = "ALLCOURSES";
    private static final String TAB_COURSES= "COURSES";
    private static final String TAB_SAVED= "SAVED";
    private static final String TAB_PROFILE = "PROFILE";

    public NavViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }
    public Fragment getItem(int pos){
        Log.d(TAG, "getItem " + "position" + pos);
        return fragments.get(pos);
    }
    // Return the number of views available
    public int getCount(){
        Log.d(TAG, "getCount " + "size " + fragments.size());
        return fragments.size();
    }


    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "getPageTitle " + "position " + position);
        switch (position) {
            case ALLCOURSES:
                return TAB_ALLCOURSES;
            case COURSES:
                return TAB_COURSES;
            case SAVED:
                return TAB_SAVED;
            case PROFILE:
                return TAB_PROFILE;
            default:
                break;

        }
        return null;
    }

}
