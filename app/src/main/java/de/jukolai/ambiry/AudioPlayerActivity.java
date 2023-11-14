package de.jukolai.ambiry;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioPlayerActivity extends AppCompatActivity {

    //updated every 50 millisecons the seekBar
    private final Handler seekbarUpdateHandler = new Handler();
    //updated every 50 millisecons the textviews
    private final Handler textViewUpdateHandler = new Handler();
    ImageButton playButton, skippreviousButton, skipnextButton, descriptionButton, thumbupButton, commentsButton, sleepButton, castButton, arrowDownButton, forwardButton, rewindButtom;
    TextView textViewTitle, textViewremainingTime, textViewcurrentTime, textViewAudioName;
    SeekBar seekBarDuration;
    MediaPlayer mediaPlayer;
    //update all component
    private final Runnable UpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            seekBarDuration.setMax(mediaPlayer.getDuration());
            seekBarDuration.setProgress(mediaPlayer.getCurrentPosition());
            seekbarUpdateHandler.postDelayed(this, 50);
        }
    };
    private final Runnable UpdateTextview = new Runnable() {
        @Override
        public void run() {
            textViewcurrentTime.setText(getTimeString(mediaPlayer.getCurrentPosition()));
            textViewremainingTime.setText(getTimeString(mediaPlayer.getDuration()));
            seekbarUpdateHandler.postDelayed(this, 50);
        }
    };

    // convert from millisecond to hours,minutes and seconds
    public static String getTimeString(long duration) {

        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(duration) % TimeUnit.MINUTES.toSeconds(1));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        playButton = findViewById(R.id.playbutton);
        skipnextButton = findViewById(R.id.skipnextbutton);
        skippreviousButton = findViewById(R.id.skippreviousbutton);
        descriptionButton = findViewById(R.id.shareButton);
        thumbupButton = findViewById(R.id.thumbupButton);
        commentsButton = findViewById(R.id.commentsButton);
        sleepButton = findViewById(R.id.sleepButton);
        castButton = findViewById(R.id.castbutton);
        textViewTitle = findViewById(R.id.textviewTitle);
        textViewAudioName = findViewById(R.id.textviewArtist);
        textViewremainingTime = findViewById(R.id.textviewremainingtime);
        textViewcurrentTime = findViewById(R.id.textviewcurrentime);
        seekBarDuration = findViewById(R.id.seekbar);
        arrowDownButton = findViewById(R.id.arrowButton);
        forwardButton = findViewById(R.id.forwardButton);
        rewindButtom = findViewById(R.id.rewindButton);


        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://wdrmedien-a.akamaihd.net/medp/podcast/weltweit/fsk0/264/2646032/quarkssciencecops_2022-02-12_abkassierenmitbelebtemwasserderfallgrander_wdronline.mp3");
            mediaPlayer.prepare();
            playPause();
        } catch (IOException e) {
            e.printStackTrace();
        }


        playButton.setOnClickListener(v -> {
            try {
                playPause();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        skippreviousButton.setOnClickListener(v -> skipPrevious());

        skipnextButton.setOnClickListener(v -> skipNext());

        forwardButton.setOnClickListener(v -> setForward());

        rewindButtom.setOnClickListener(v -> setRewind());


        seekBarDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void playPause() throws IOException {
        if (!mediaPlayer.isPlaying()) {
            playButton.setBackgroundResource(R.drawable.pauseicon);
            mediaPlayer.start();
            // mediaPlayer.prepareAsync();
            seekbarUpdateHandler.postDelayed(UpdateSeekbar, 0);
            textViewUpdateHandler.postDelayed(UpdateTextview, 0);
        } else {
            playButton.setBackgroundResource(R.drawable.playicon);
            mediaPlayer.pause();
            seekbarUpdateHandler.removeCallbacks(UpdateSeekbar);
            textViewUpdateHandler.removeCallbacks(UpdateTextview);

        }

    }

    private void skipNext() {


    }

    private void skipPrevious() {


    }

    private void setForward() {
        if (mediaPlayer != null) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            // default 30 second
            int seekForwardTime = 30 * 1000;
            mediaPlayer.seekTo(Math.min(currentPosition + seekForwardTime, mediaPlayer.getDuration()));
        }
    }

    private void setRewind() {

        if (mediaPlayer != null) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            // default 30 second
            int seekBackwardTime = 30 * 1000;
            mediaPlayer.seekTo(Math.max(currentPosition - seekBackwardTime, 0));
        }


    }
}