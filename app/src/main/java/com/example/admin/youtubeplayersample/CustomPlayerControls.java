package com.example.admin.youtubeplayersample;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by harishpittu on 21/08/17.
 */

class CustomPlayerControls {

    public static final String TAG = "CustomPlayerControls : ";

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;
    private Context context;
    private View anchorView;
    private ImageView playPauseButton;
    private TextView durationTextView;
    private PopupWindow popupWindow;
    private String totalDuration = "";
    private boolean isFullScreen = false;


    private Handler handler;

    public CustomPlayerControls(Context context, YouTubePlayerView youTubePlayerView, YouTubePlayer youTubePlayer, View anchorView, boolean isFullScreen) {
        this.youTubePlayerView = youTubePlayerView;
        this.youTubePlayer = youTubePlayer;
        this.context = context;
        this.anchorView = anchorView;
        this.isFullScreen = isFullScreen;
    }

    /**
     * initialize custom controls window
     */
    public void initialize() {


        handler = new Handler();
        handler.postDelayed(timer, 1000);

        LayoutInflater layoutInflater
                = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        int height;
        if(!isFullScreen) {
            height = youTubePlayerView.getHeight();
        }else {
           height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        View popupView = layoutInflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                100,100
        );
        popupWindow.showAsDropDown(anchorView, 0, 0);
        playPauseButton = (ImageView) popupView.findViewById(R.id.play_pause_button);
        durationTextView = (TextView) popupView.findViewById(R.id.duration);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (youTubePlayer.isPlaying()) {
                    youTubePlayer.pause();
                    playPauseButton.setImageResource(R.drawable.ic_play_circle_outline_blue_400_24dp);
                } else {
                    youTubePlayer.play();
                    playPauseButton.setImageResource(R.drawable.ic_pause_circle_outline_blue_400_24dp);
                }
            }
        });

        this.popupWindow = popupWindow;

    }


    public void dismissPopup() {
        handler.removeCallbacks(timer);
        popupWindow.dismiss();
    }

    Runnable timer = new Runnable() {
        @Override
        public void run() {
//            Log.d(TAG, "run: " + youTubePlayer.getDurationMillis() + "##" + youTubePlayer.getCurrentTimeMillis());
            durationTextView.setText(getFormattedDuration(youTubePlayer.getCurrentTimeMillis(), youTubePlayer.getDurationMillis()));
            handler.postDelayed(timer, 1000);
        }
    };


    private String getFormattedDuration(long currentDuration, long totalDuration) {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d / %02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(currentDuration), TimeUnit.MILLISECONDS.toMinutes(currentDuration), TimeUnit.MILLISECONDS.toSeconds(currentDuration), TimeUnit.MILLISECONDS.toHours(totalDuration), TimeUnit.MILLISECONDS.toMinutes(totalDuration), TimeUnit.MILLISECONDS.toSeconds(totalDuration)
        );
    }

}
