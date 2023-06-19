package com.example.decompras;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.Button;

public class quienesSomos extends AppCompatActivity {

    private static final String VIDEO_SAMPLE = "decompras.mp4";
    private VideoView mVideoView;
    private int mPosiciónActual = 0;

    private TextView mBufferingTextView;
    private static final String PLAYBACK_TIME = "play_time";
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quienes_somos);
        Button btnVolverMain = findViewById(R.id.btnVolverMain);
        btnVolverMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver al MainActivity
                onBackPressed();
            }
        });

        mBufferingTextView = findViewById(R.id.buffering_textview);
        mVideoView = findViewById(R.id.videoview);

        if (savedInstanceState != null) {
            mPosiciónActual = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        Log.d(LOG_TAG, "¡Botón presionado!" + mPosiciónActual);

        MediaController controlador = new MediaController(this);
        controlador.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controlador);
    }

    @Override
    protected void onStart() {
        super.onStart();
        inicializarPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(PLAYBACK_TIME, mPosiciónActual);
        super.onSaveInstanceState(outState);
    }

    protected void onPause() {
        super.onPause();
        mPosiciónActual = mVideoView.getCurrentPosition();
    }

    @SuppressLint("RestrictedApi")
    private void inicializarPlayer() {
        mBufferingTextView.setVisibility(VideoView.VISIBLE);
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);

        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mBufferingTextView.setVisibility(VideoView.INVISIBLE);
                        if (mPosiciónActual > 0) {
                            mVideoView.seekTo(mPosiciónActual);
                        } else {

                            mVideoView.seekTo(1);
                        }
                        mVideoView.start();
                    }
                });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(quienesSomos.this, "Reproducción completa", Toast.LENGTH_SHORT).show();
                mVideoView.seekTo(1);
            }
        });
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {

            return Uri.parse(mediaName);
        } else {

            return Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.decompras);
        }
    }


}
