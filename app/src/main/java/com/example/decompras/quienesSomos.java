package com.example.decompras;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class quienesSomos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quienes_somos);


        Button backButton = findViewById(R.id.backButton);


        backButton.setOnClickListener(v -> {

            Intent intent = new Intent(quienesSomos.this, MainActivity.class);
            startActivity(intent);
        });


        VideoView videoView = findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videopruebas;


        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
    }
}

