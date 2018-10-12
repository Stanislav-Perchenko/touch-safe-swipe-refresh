package com.alperez.samples.safeswiperefresh;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alperez.samples.safeswiperefresh.widget.ListItemView;
import com.alperez.widget.TouchSafeSwipeRefreshLayout;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final boolean D = BuildConfig.DEBUG;
    public static final String LOG_TAG = "MY_ADAPTER";

    public static final String ARG_PAGE_LAYOUT = "page_layout";
    public static final String ARG_PAGE_TITLE = "page_title";
    public static final String ARG_PAGE_SUBTITLE = "page_subtitle";

    public static final int N_ITEMS = 24;
    public static final int N_PAGES_IN_ITEM = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResId = getIntent().getIntExtra(ARG_PAGE_LAYOUT, -1);
        if (layoutResId <= 0) {
            Toast.makeText(this, String.format("No '%s' launch argument", ARG_PAGE_LAYOUT), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setContentView(layoutResId);
        setupToolbar();
        setupRefreshCanceler();
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


    protected void setupToolbar() {
        ActionBar ab = getSupportActionBar();
        if (ab == null) {
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            ab = getSupportActionBar();
        }
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(getTextArg(ARG_PAGE_TITLE));
            ab.setSubtitle(getTextArg(ARG_PAGE_SUBTITLE));
        }

        //--- This is the ugly hack to support setting title multiple times when the Toolbar
        //--- is in the CollapsingToolbarLayout
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        if (tb != null) {
            ViewParent vp = tb.getParent();
            if ((vp != null) && (vp instanceof CollapsingToolbarLayout)) {
                ((CollapsingToolbarLayout) vp).setTitle(getTextArg(ARG_PAGE_TITLE));
            }
        }
    }

    private String getTextArg(String argName) {
        String txt = getIntent().getStringExtra(argName);
        return (txt == null) ? "" : txt;
    }



    private void setupRefreshCanceler() {
        View refresher = findViewById(R.id.refresher);
        if (refresher == null) return;
        if (refresher instanceof SwipeRefreshLayout) {
            ((SwipeRefreshLayout) refresher).setOnRefreshListener(this::onRefreshStarted);
        } else if (refresher instanceof TouchSafeSwipeRefreshLayout) {
            ((TouchSafeSwipeRefreshLayout) refresher).setOnRefreshListener(this::onRefreshStarted);
        }
    }

    private void onRefreshStarted() {
        getWindow().getDecorView().postDelayed(this::dismissRefreshing, 1200);
    }

    private void dismissRefreshing() {
        View refresher = findViewById(R.id.refresher);
        if (refresher == null) return;
        if (refresher instanceof SwipeRefreshLayout) {
            ((SwipeRefreshLayout) refresher).setRefreshing(false);
        } else if (refresher instanceof TouchSafeSwipeRefreshLayout) {
            ((TouchSafeSwipeRefreshLayout) refresher).setRefreshing(false);
        }
    }
}
