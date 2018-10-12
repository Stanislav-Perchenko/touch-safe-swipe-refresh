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

    private String textData;

    private TextView vTxtTitle;
    private ViewPager vPager;

    public ListItemView(@NonNull Context context, int nPages) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_list_item, this, true);

        vTxtTitle = (TextView) findViewById(R.id.txt_title);
        (vPager = (ViewPager) findViewById(R.id.pager)).setAdapter(new MyItemAdapter(nPages));
        ((CirclePageIndicator) findViewById(R.id.page_indicator)).setViewPager(vPager, nPages / 2);
    }

    public void setTextData(String textData) {
        if (!TextUtils.equals(this.textData, textData)) {
            vTxtTitle.setText(this.textData = textData);
            ((MyItemAdapter) vPager.getAdapter()).setTextData(textData);
            vPager.getAdapter().notifyDataSetChanged();
        }
    }

}
