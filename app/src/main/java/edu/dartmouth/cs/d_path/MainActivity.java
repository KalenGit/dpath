package edu.dartmouth.cs.d_path;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private NavViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //set up bottom navigation and add fragments
        BottomNavigationView navigation = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.viewpager);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new CoursesFragment());
        fragments.add(new SavedFragment());
        fragments.add(new ProfileFragment());

        NavViewPagerAdapter viewPageAdapter = new NavViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPageAdapter);


        //listener for bottom nav
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    //start fragment
                    case R.id.navigation_courses:
                        viewPager.setCurrentItem(0);
                        break;
                    //history fragment
                    case R.id.navigation_saved:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_Profile:
                        viewPager.setCurrentItem(2);
                        break;
                }


                return true;
            }
        });
    }
}
