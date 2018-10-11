package com.alperez.samples.safeswiperefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.alperez.samples.safeswiperefresh.widget.ListItemView;

public class MainActivity extends AppCompatActivity {

    public static final int N_ITEMS = 12;

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
                return ""+i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new ListItemView(parent.getContext(), parent);
                }
                ((ListItemView) convertView).setTextData(getItem(position));
                return convertView;
            }
        });
    }
}
