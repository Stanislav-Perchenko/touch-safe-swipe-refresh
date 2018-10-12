package com.alperez.samples.safeswiperefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.alperez.samples.safeswiperefresh.widget.ListItemView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final boolean D = BuildConfig.DEBUG;
    public static final String LOG_TAG = "MY_ADAPTER";

    public static final int N_ITEMS = 13;
    public static final int N_PAGES_IN_ITEM = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ListView) findViewById(android.R.id.list)).setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return N_ITEMS;
            }

            @Override
            public String getItem(int i) {
                return "Item #"+i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            private final int itemHeightPx = getResources().getDimensionPixelSize(R.dimen.list_item_height);
            private final Map<Integer, Integer> savedPositions = new HashMap<>();

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (D) Log.e(LOG_TAG, "==========================   getView("+position+")   ===========================");
                ListItemView row;
                if (convertView == null) {
                    if (D) Log.e(LOG_TAG, "    Build new View item");
                    // Build new List item View. The internal ViewPager position will be set by default.
                    row = new ListItemView(MainActivity.this, N_PAGES_IN_ITEM);
                    row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeightPx));
                } else {
                    row = (ListItemView) convertView;
                    // Preserve previous item ViewPager position
                    savedPositions.put(row.getPositionInList(), row.getPagerPosition());
                    if (D) Log.d(LOG_TAG, String.format("    Got recycled View. oldListPosition=%d, oldPagerPosition=%d", row.getPositionInList(), row.getPagerPosition()));
                }
                row.setPositionInList(position);
                row.setTextData(getItem(position));
                Integer pagePos = savedPositions.get(position);
                if (pagePos != null) {
                    row.setPagerPosition(pagePos);
                    if (D) Log.d(LOG_TAG, "    --> Restore pager position - "+pagePos);
                } else {
                    row.setDefaultPagerPosition();
                    if (D) Log.d(LOG_TAG, "    ~~> Set default pager position");
                }
                return row;
            }
        });
    }
}
