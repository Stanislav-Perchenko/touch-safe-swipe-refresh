package com.alperez.samples.safeswiperefresh.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by stanislav.perchenko on 10/11/2018
 */
public class ListItemView extends FrameLayout {

    private String textData;

    public ListItemView(@NonNull Context context, @Nullable ViewGroup parent) {
        super(context);
        //TODO Inflate content
    }

    public void setTextData(String textData) {
        this.textData = textData;
        //TODO Invalidate
    }
}
