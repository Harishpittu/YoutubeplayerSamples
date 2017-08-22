/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.admin.youtubeplayersample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.admin.youtubeplayersample.adapter.DemoArrayAdapter;
import com.example.admin.youtubeplayersample.adapter.DemoListViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Main activity from which the user can select one of the other demo activities.
 */
public class YouTubeAPIDemoActivity extends Activity implements OnItemClickListener {

    private List<DemoListViewItem> activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_home);

        final TextView textView = (TextView) findViewById(R.id.textview);


//        window();


        PackageInfo packageInfo = null;
        PackageManager pm = getPackageManager();
        try {
            packageInfo = pm.getPackageInfo(
                    getPackageName(), PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA);

        } catch (NameNotFoundException e) {
            Log.e(getString(R.string.loggingTag), "Could not find package with name " + getPackageName());
            finish();
        }

        int appMinVersion = packageInfo.applicationInfo.targetSdkVersion;

        activities = new ArrayList<DemoListViewItem>();
        for (ActivityInfo activityInfo : packageInfo.activities) {
            String name = activityInfo.name;
            Bundle metaData = activityInfo.metaData;

            if (metaData != null
                    && metaData.getBoolean(getString(R.string.isLaunchableActivity), false)) {
                String label = getString(activityInfo.labelRes);
                int minVersion = metaData.getInt(getString(R.string.minVersion), appMinVersion);
                activities.add(new Demo(label, name, minVersion));
            }
        }

        ListView listView = (ListView) findViewById(R.id.demo_list);
        DemoArrayAdapter adapter = new DemoArrayAdapter(this, R.layout.list_item, activities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        TextView disabledText = (TextView) findViewById(R.id.some_demos_disabled_text);
        disabledText.setText(getString(R.string.some_demos_disabled, android.os.Build.VERSION.SDK_INT));

        if (adapter.anyDisabled()) {
            disabledText.setVisibility(View.VISIBLE);
        } else {
            disabledText.setVisibility(View.GONE);
        }
//        android.os.Handler handler = new android.os.Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                window();
//            }
//        }, 1000);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Demo clickedDemo = (Demo) activities.get(position);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(getPackageName(), clickedDemo.className));
        startActivity(intent);
    }

    private final class Demo implements DemoListViewItem {

        public final String title;
        public final int minVersion;
        public final String className;

        public Demo(String title, String className, int minVersion) {
            this.className = className;
            this.title = title;
            this.minVersion = minVersion;
        }

        @Override
        public boolean isEnabled() {
            return android.os.Build.VERSION.SDK_INT >= minVersion;
        }

        @Override
        public String getDisabledText() {
            String itemDisabledText = getString(R.string.list_item_disabled);
            return String.format(itemDisabledText, minVersion);
        }

        @Override
        public String getTitle() {
            return title;
        }

    }

    private void window() {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                // Shrink the window to wrap the content rather than filling the screen
//            WindowManager.LayoutParams.WRAP_CONTENT,
                100, 100,
//            WindowManager.LayoutParams.WRAP_CONTENT,
                // Display it on top of other application windows, but only for the current user
                WindowManager.LayoutParams.TYPE_TOAST,
                // Don't let it grab the input focus
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                // Make the underlying application window visible through any transparent parts
                PixelFormat.TRANSLUCENT);


        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);

// Define the position of the window within the screen
        p.gravity = Gravity.TOP | Gravity.RIGHT;
        p.x = 0;
        p.y = 0;
        TextView textView = new TextView(this);
        textView.setText("Harish Reddy asdfasd asdfasdf asdfasdf asdfasdf asdfasdfasdf asdf asdfadsfasdf");

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(popupView, p);
    }


    private void snackBar(View view) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        popupWindow.showAsDropDown(view, 50, -30);

//    final Snackbar tmp = null;
//    = new Snackbar(this,
//            "Do you want change color of this button to red?",
//            "yes",
//            new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
////                ButtonFlat btn = (ButtonFlat) findViewById(R.id.buttonSnackBar);
//                //btn.setTextColor(Color.RED);
//              }
//            });
//    tmp.setIndeterminate(true);
//    tmp.show();
    }

}
