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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple YouTube Android API demo application which shows how to create a simple application that
 * shows a YouTube Video in a {@link YouTubePlayerFragment}.
 * <p>
 * Note, this sample app extends from {@link YouTubeFailureRecoveryActivity} to handle errors, which
 * itself extends {@link YouTubeBaseActivity}. However, you are not required to extend
 * {@link YouTubeBaseActivity} if using {@link YouTubePlayerFragment}s.
 */
public class FragmentDemoActivity extends YouTubeFailureRecoveryActivity implements
        YouTubePlayer.OnFullscreenListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.fragments_demo);

    YouTubePlayerFragment youTubePlayerFragment =
        (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    youTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);
  }

  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
      boolean wasRestored) {
    if (!wasRestored) {
      player.cueVideo("nCgQDjiotG0");
    }
    player.setOnFullscreenListener(this);
  }

  @Override
  protected YouTubePlayer.Provider getYouTubePlayerProvider() {
    return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
  }

  @Override
  public void onFullscreen(boolean b) {
//    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//    alertDialog.setTitle("Alert Dialog");
//    alertDialog.setMessage("Welcome to dear user.");
//
//    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialog, int which) {
//        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
//      }
//    });
//
//    alertDialog.show();
    window();
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
            WindowManager.LayoutParams.WRAP_CONTENT,
//            100, 100,
            WindowManager.LayoutParams.WRAP_CONTENT,
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

  @Override
  protected void onPause() {
    windowManager.removeViewImmediate(popupView);
    super.onPause();
  }
}
