package com.alperez.samples.safeswiperefresh.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alperez.samples.safeswiperefresh.MyItemAdapter;
import com.alperez.samples.safeswiperefresh.R;
import com.alperez.widget.CirclePageIndicator;

/**
 * Created by stanislav.perchenko on 10/11/2018
 */
public class ListItemView extends FrameLayout {

    private final int nPages;
    private String textData;
    private int positionInList;

    private TextView vTxtTitle;
    private ViewPager vPager;
    private CirclePageIndicator vPageIndicator;

    public ListItemView(@NonNull Context context, int nPages) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_list_item, this, true);

        vTxtTitle = (TextView) findViewById(R.id.txt_title);
        (vPager = (ViewPager) findViewById(R.id.pager)).setAdapter(new MyItemAdapter(this.nPages = nPages));
        (vPageIndicator = (CirclePageIndicator) findViewById(R.id.page_indicator)).setViewPager(vPager, nPages / 2);
    }

    public void setDefaultPagerPosition() {
        vPageIndicator.setCurrentItemNoAnimation(nPages / 2);
    }

    public void setPagerPosition(int position) {
        if (position < 0 || position >= nPages) {
            throw new IllegalArgumentException("Wrong position value - "+position+", N pages = "+nPages);
        } else {
            vPageIndicator.setCurrentItemNoAnimation(position);
        }
    }

    public int getPagerPosition() {
        return vPager.getCurrentItem();
    }



    public void setTextData(String textData) {
        if (!TextUtils.equals(this.textData, textData)) {
            vTxtTitle.setText(this.textData = textData);
            ((MyItemAdapter) vPager.getAdapter()).setTextData(textData);
            vPager.getAdapter().notifyDataSetChanged();
        }
    }

    public int getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(int positionInList) {
        this.positionInList = positionInList;
    }
}
