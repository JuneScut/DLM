package com.example.a76952.login2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.a76952.login2.Fragments.ActivityFragment;
import com.example.a76952.login2.Fragments.BaseFragment;
import com.example.a76952.login2.Fragments.CourseTableFragment;
import com.example.a76952.login2.Fragments.SignFragment;
import com.example.a76952.login2.adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private TextView mTextMessage;
    SignFragment signFragment;
    ActivityFragment activityFragment;
    CourseTableFragment courseTableFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_friends:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_news:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_lib:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.item_find:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.item_more:
                                viewPager.setCurrentItem(4);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(2).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(adapter.getCount()>0){
            adapter.removeAllFragments();
        }
        signFragment=new SignFragment();
        activityFragment=new ActivityFragment();
        courseTableFragment=new CourseTableFragment();

        adapter.addFragment(BaseFragment.newInstance("通讯录"));
        adapter.addFragment(courseTableFragment);
        adapter.addFragment(signFragment);
        adapter.addFragment(activityFragment);
        adapter.addFragment(BaseFragment.newInstance("我的"));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
    }
}