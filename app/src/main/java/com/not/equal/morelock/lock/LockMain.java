package com.not.equal.morelock.lock;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.not.equal.morelock.R;
import com.not.equal.morelock.service.PreferencesManager;

import java.util.ArrayList;

/**
 * Created by admin on 2016-05-30.
 */

//잠금 상세 화면
public class LockMain extends AppCompatActivity {
    private ViewPager mViewPager;
    private LockPageAdapter adapter;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    public static int gif[] = {R.drawable.gif_return, R.drawable.gif_star, R.drawable.gif_toggle, R.drawable.gif_return, R.drawable.gif_tree, R.drawable.gif_return, R.drawable.gif_return};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_main);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new LockPageAdapter(getSupportFragmentManager(), 9, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(PreferencesManager.getInstance(getApplicationContext()).getCurType() + 1);
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mViewPager.getCurrentItem() == 0)
                        mViewPager.setCurrentItem(adapter.getCount() - 2, false);
                    else if (mViewPager.getCurrentItem() == 8)
                        mViewPager.setCurrentItem(1, false);
                }
            }
        };
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }
}

class LockPageAdapter extends FragmentStatePagerAdapter {
    private int tabSize;
    private Context context;
    private ArrayList<LockFragment> fragments;

    public LockPageAdapter(FragmentManager fm, int tabSize, Context context) {
        super(fm);
        this.tabSize = tabSize;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments == null) {
            fragments = new ArrayList<>();
            createFragment(6);
            for (int i = 0; i < 7; i++) {
                createFragment(i);
            }
            createFragment(0);
        }
        return fragments.get(position);
    }

    public void createFragment(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        LockFragment lockFragment = new LockFragment();
        lockFragment.setArguments(bundle);
        fragments.add(lockFragment);
    }

    @Override
    public int getCount() {
        return tabSize;
    }
}

