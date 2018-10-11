package com.alperez.samples.safeswiperefresh.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alperez.samples.safeswiperefresh.ItemPageFragment;
import com.alperez.samples.safeswiperefresh.R;
import com.alperez.widget.CirclePageIndicator;

/**
 * Created by stanislav.perchenko on 10/11/2018
 */
public class ListItemView extends FrameLayout {


    private String textData;

    private ViewPager vPager;

    public ListItemView(@NonNull AppCompatActivity context, @Nullable ViewGroup parent, int nPages) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_list_item, parent, true);

        (vPager = (ViewPager) findViewById(R.id.pager)).setAdapter(new MyAdapter(context.getSupportFragmentManager(), nPages));
        ((CirclePageIndicator) findViewById(R.id.page_indicator)).setViewPager(vPager, nPages / 2);
    }

    public void setTextData(String textData) {
        if (!TextUtils.equals(this.textData, textData)) {
            this.textData = textData;
            vPager.getAdapter().notifyDataSetChanged();
        }
    }


    /**********************************************************************************************/
    /****************************  Adapter implementation  ****************************************/
    /**********************************************************************************************/

    private class MyAdapter extends FragmentPagerAdapter {

        private final int count;

        public MyAdapter(FragmentManager fm, int nPages) {
            super(fm);
            count = nPages;
        }

        @Override
        public Fragment getItem(int position) {
            return ItemPageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}
