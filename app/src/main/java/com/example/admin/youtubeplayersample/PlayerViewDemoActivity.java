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

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple YouTube Android API demo application which shows how to create a simple application that
 * displays a YouTube Video in a {@link YouTubePlayerView}.
 * <p>
 * Note, to use a {@link YouTubePlayerView}, your activity must extend {@link YouTubeBaseActivity}.
 */
public class PlayerViewDemoActivity extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnFullscreenListener {
    View view;

    public static final String TAG = "PlayerView : ";
    YouTubePlayerView youTubeView;
    YouTubePlayer youTubePlayer;
    CustomPlayerControls customPlayerControls;
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerview_demo);

        textView = (TextView) findViewById(R.id.textview);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("wKJ9KzGQq0w");
        }
//        player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        youTubePlayer = player;
        youTubePlayer.setOnFullscreenListener(this);
        player.setFullscreen(true);
//        customPlayerControls = new CustomPlayerControls(this,youTubeView,youTubePlayer,textView,false);
//        customPlayerControls.initialize();
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        return youTubePlayerView;
    }

    @Override
    protected void onPause() {
        customPlayerControls.dismissPopup();
        super.onPause();

    }

    @Override
    public void onFullscreen(boolean b) {
        if (b) {
            window();
        } else {
            windowManager.removeViewImmediate(popupView);
        }
    }


    private void window() {
        //here is all the science of params
//    final WindowManager.LayoutParams p = new WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//            PixelFormat.TRANSLUCENT
//    );
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                // Shrink the window to wrap the content rather than filling the screen
//                WindowManager.LayoutParams.WRAP_CONTENT,
            100, 100,
//                WindowManager.LayoutParams.WRAP_CONTENT,
                // Display it on top of other application windows, but only for the current user
                WindowManager.LayoutParams.TYPE_TOAST,
                // Don't let it grab the input focus
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                // Make the underlying application window visible through any transparent parts
                PixelFormat.TRANSLUCENT);


        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.popup, null);

// Define the position of the window within the screen
        p.gravity = Gravity.TOP | Gravity.RIGHT;
        p.x = 0;
        p.y = 0;
        TextView textView = new TextView(this);
        textView.setText("Harish Reddy asdfasd asdfasdf asdfasdf asdfasdf asdfasdfasdf asdf asdfadsfasdf");

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(popupView, p);
    }

    WindowManager windowManager;
    View popupView;
}
