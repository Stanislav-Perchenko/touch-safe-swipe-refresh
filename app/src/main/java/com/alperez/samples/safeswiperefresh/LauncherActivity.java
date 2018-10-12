package com.alperez.samples.safeswiperefresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by stanislav.perchenko on 10/12/2018
 */
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        findViewById(R.id.opt_original_swipe).setOnClickListener(this::goMainActivity);
        findViewById(R.id.opt_safe_swipe_simple).setOnClickListener(this::goMainActivity);
        findViewById(R.id.opt_safe_swipe_cool).setOnClickListener(this::goMainActivity);
    }

    private void goMainActivity(View v) {
        Bundle args = new Bundle();
        switch (v.getId()) {
            case R.id.opt_original_swipe:
                args.putInt(MainActivity.ARG_PAGE_LAYOUT, R.layout.activity_main_orig_swipe);
                args.putString(MainActivity.ARG_PAGE_TITLE, getString(R.string.screen_title_orig_swipe));
                break;
            case R.id.opt_safe_swipe_simple:
                args.putInt(MainActivity.ARG_PAGE_LAYOUT, R.layout.activity_main_safe_swipe_simple);
                args.putString(MainActivity.ARG_PAGE_TITLE, getString(R.string.screen_title_safe_swipe_simple));
                break;
            case R.id.opt_safe_swipe_cool:
                args.putInt(MainActivity.ARG_PAGE_LAYOUT, R.layout.activity_main_safe_swipe_cool);
                args.putString(MainActivity.ARG_PAGE_TITLE, getString(R.string.screen_title_safe_swipe_cool));
                break;
        }
        startActivity(new Intent(this, MainActivity.class).putExtras(args));
    }
}
