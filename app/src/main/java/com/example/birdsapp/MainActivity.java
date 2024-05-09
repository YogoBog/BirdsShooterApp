package com.example.birdsapp;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MySurfaceView mySurfaceView;

    private ImageView pause;
    private Button shoot;
    private Button reset;
    public static boolean isReset;
    public static boolean isPaused;
    public static boolean isShooting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySurfaceView = findViewById(R.id.mySurfaceView);
        pause = findViewById(R.id.pauseImageView);
        shoot = findViewById(R.id.shootButton);
        reset = findViewById(R.id.resetButton);


        isReset = false;
        isShooting = false;
        isPaused = false;

        pause.setOnClickListener(view -> {
            if (!isPaused) {
                pause.setImageResource(R.drawable.play);
                isPaused = true;
            } else {
                pause.setImageResource(R.drawable.pause);
                isPaused = false;
            }
        });

        shoot.setOnClickListener(view -> {
            if (isPaused) return;
            isShooting = true;
        });

        reset.setOnClickListener(view -> {
            if (isPaused) return;
            isReset = true;

        });


    }
}