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

    private MySurfaceView mySurfaceView; // SurfaceView for rendering graphics

    private ImageView pause; // ImageView for pause/play functionality
    private Button shoot; // Button for shooting action
    private Button reset; // Button for resetting game state
    public static boolean isReset; // Flag indicating whether game should reset
    public static boolean isPaused; // Flag indicating whether game is paused
    public static boolean isShooting; // Flag indicating whether shooting action is occurring

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout for the activity

        // Initialize views
        mySurfaceView = findViewById(R.id.mySurfaceView);
        pause = findViewById(R.id.pauseImageView);
        shoot = findViewById(R.id.shootButton);
        reset = findViewById(R.id.resetButton);

        // Initialize flags
        isReset = false;
        isShooting = false;
        isPaused = false;

        // Pause/play functionality
        pause.setOnClickListener(view -> {
            if (!isPaused) {
                pause.setImageResource(R.drawable.play); // Change image to play icon
                isPaused = true; // Game is now paused
            } else {
                pause.setImageResource(R.drawable.pause); // Change image to pause icon
                isPaused = false; // Game is now resumed
            }
        });

        // Shooting action
        shoot.setOnClickListener(view -> {
            if (isPaused) return; // If game is paused, do nothing
            isShooting = true; // Set shooting flag to true
        });

        // Reset game state
        reset.setOnClickListener(view -> {
            if (isPaused) return; // If game is paused, do nothing
            isReset = true; // Set reset flag to true
        });
    }
}
