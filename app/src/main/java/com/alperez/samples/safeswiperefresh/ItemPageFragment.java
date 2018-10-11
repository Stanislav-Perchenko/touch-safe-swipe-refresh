package com.alperez.samples.safeswiperefresh;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stanislav.perchenko on 10/11/2018
 */
public class ItemPageFragment extends Fragment {
    public static final String ARG_PAGE_INDEX = "page_index";

    public static ItemPageFragment newInstance(int pageIndex) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_INDEX, pageIndex);
        ItemPageFragment f = new ItemPageFragment();
        f.setArguments(args);
        return f;
    }


    private int pageIndex;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        pageIndex = (args == null) ? -1 : args.getInt(ARG_PAGE_INDEX, -1);
    }

    private View vContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (vContent == null) {
            vContent = inflater.inflate(R.layout.fragment_item_page, container, false);
            ((TextView) vContent.findViewById(android.R.id.text1)).setText(getResources().getString(R.string.item_page_content, pageIndex));
            vContent.setBackground(new ColorDrawable(getColorByIndex(pageIndex)));
        } else {
            container.removeView(vContent);
        }
        return vContent;
    }

    private static int getColorByIndex(int index) {
        int nColors = PAGE_COLORS.size();
        return PAGE_COLORS.get(index % nColors);
    }

    private static final Map<Integer, Integer> PAGE_COLORS = new HashMap<>();


    //TODO Init colors from XML Resources in the onCreate()
    static {
        PAGE_COLORS.put(0, Color.parseColor("#4286f4"));
        PAGE_COLORS.put(0, Color.parseColor("#24d6c4"));
        PAGE_COLORS.put(0, Color.parseColor("#24d682"));
        PAGE_COLORS.put(0, Color.parseColor("#45b260"));
        PAGE_COLORS.put(0, Color.parseColor("#7ec956"));
        PAGE_COLORS.put(0, Color.parseColor("#9dc431"));
        PAGE_COLORS.put(0, Color.parseColor("#c4a431"));
        PAGE_COLORS.put(0, Color.parseColor("#db693f"));
        PAGE_COLORS.put(0, Color.parseColor("#cc479d"));
    }
}
