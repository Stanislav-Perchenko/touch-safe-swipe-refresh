package com.alperez.samples.safeswiperefresh;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by stanislav.perchenko on 10/12/2018
 */
public class MyItemAdapter extends PagerAdapter {

    public static final String LOG_TAG = "G_P_A";
    private static final boolean D = BuildConfig.DEBUG;


    private final int count;
    private int numMaxSavedRecycledPaes = 6;
    private String mTextData;
    private AdapterObject primaryAdapterObject;
    private final Map<Integer, AdapterObject> mapAttachedAdapterObjects = new HashMap<>();
    private final Queue<AdapterObject> recycledAdapterObjects = new LinkedList<>();

    public MyItemAdapter(int nPages) {
        count = nPages;
    }

    public int getNumMaxSavedRecycledPaes() {
        return numMaxSavedRecycledPaes;
    }

    public void setNumMaxSavedRecycledPaes(int numMaxSavedRecycledPaes) {
        this.numMaxSavedRecycledPaes = numMaxSavedRecycledPaes;
    }

    public void setTextData(String mTextData) {
        if (!TextUtils.equals(this.mTextData, mTextData)) {
            this.mTextData = mTextData;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getItemPosition(Object object) {
        // For the notifyDataSetChanged() to cause immediate ViewPager update.
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (object instanceof AdapterObject) ? (((AdapterObject) object).view == view) : false;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        primaryAdapterObject = (AdapterObject) object;
    }

    @NonNull
    @Override
    public AdapterObject instantiateItem(@NonNull ViewGroup container, int position) {
        if (D) Log.d(LOG_TAG, "===>  Instantiate item - "+position);

        AdapterObject itemObj = mapAttachedAdapterObjects.get(position);
        if (itemObj == null) {
            if (recycledAdapterObjects.size() > 0) {
                if (D) Log.d(LOG_TAG, String.format("Got item for position %d from SCRAP", position));
                itemObj = recycledAdapterObjects.remove();
                itemObj.setIndex(position);
            } else {
                if (D) Log.d(LOG_TAG, String.format("Build new item for position %d", position));
                itemObj = new AdapterObject(container, mTextData, position);
            }
            mapAttachedAdapterObjects.put(position, itemObj);
        } else {
            if (D) Log.d(LOG_TAG, String.format("Got item for position %d from HEAP", position));
        }

        itemObj.setPrefix(mTextData);
        container.addView(itemObj.view);

        return itemObj;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (D) Log.e(LOG_TAG, "<~~~~  Destroy item - "+position);

        AdapterObject itemObj = mapAttachedAdapterObjects.remove(position);
        if (itemObj != object) throw new RuntimeException("Something went wrong. Adapter user returns unknown object to be destroyed");
        container.removeView(itemObj.view);

        if (recycledAdapterObjects.size() < numMaxSavedRecycledPaes) {
            recycledAdapterObjects.add(itemObj);
        }
    }









    private class AdapterObject {
        private final TextView view;
        private String prefix;
        private int index;

        AdapterObject(@NonNull ViewGroup container, String prefix, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            view = (TextView) inflater.inflate(R.layout.view_page_item, container, false);
            this.prefix = prefix;
            this.index = position;
            bindData();
        }

        public void setPrefix(String prefix) {
            if (!TextUtils.equals(this.prefix, prefix)) {
                this.prefix = prefix;
                bindData();
            }
        }

        public void setIndex(int index) {
            if (this.index != index) {
                this.index = index;
                bindData();
            }
        }

        private void bindData() {
            view.setText(TextUtils.isEmpty(prefix) ? ("Page #"+index) : (prefix+" - Page #"+index));
            view.setBackground(new ColorDrawable(getColorByIndex(index)));
        }

        public void clear() {
            view.setText("");
            view.setBackground(null);
        }
    }




    public static int getColorByIndex(int index) {
        int nColors = PAGE_COLORS.size();
        return PAGE_COLORS.get(index % nColors);
    }


    private static final Map<Integer, Integer> PAGE_COLORS = new HashMap<>();


    //TODO Init colors from XML Resources in the onCreate()
    static {
        PAGE_COLORS.put(0, Color.parseColor("#4286f4"));
        PAGE_COLORS.put(1, Color.parseColor("#24d6c4"));
        PAGE_COLORS.put(2, Color.parseColor("#24d682"));
        PAGE_COLORS.put(3, Color.parseColor("#45b260"));
        PAGE_COLORS.put(4, Color.parseColor("#7ec956"));
        PAGE_COLORS.put(5, Color.parseColor("#9dc431"));
        PAGE_COLORS.put(6, Color.parseColor("#c4a431"));
        PAGE_COLORS.put(7, Color.parseColor("#db693f"));
        PAGE_COLORS.put(8, Color.parseColor("#cc479d"));
    }
}
