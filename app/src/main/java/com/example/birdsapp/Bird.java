package com.example.birdsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.List;
import java.util.Random;

public class Bird extends Sprite{

    // List to hold wing bitmaps for animation
    private List<Bitmap> wingBitmaps;
    
    // Index to keep track of the current wing position
    private int currentWingPosition;
    
    // Constructor for the Bird class
    public Bird(List<Bitmap> wingBitmaps, int width, int height, int x, int y) {
        // Call the superclass constructor to initialize the bird sprite with the first wing bitmap
        super(wingBitmaps.get(0), width, height);
        
        // Initialize wing bitmaps list and current wing position
        this.wingBitmaps = wingBitmaps;
        this.currentWingPosition = 0;
        
        // Set initial position of the bird
        setX(x);
        setY(y);
    }

    // Method to move the bird
    @Override
    public void move() {
        // Create a random number generator
        Random rand = new Random();

        // Generate new coordinates for the bird
        int newX = getX() + rand.nextInt(50);
        int newY = getY() + rand.nextInt(50);
        
        // Wrap around the screen if the bird goes beyond the screen boundaries
        if (newX > getWidth()) {
            newX = 0;
        }
        if (newY > getHeight()){
            newY = 0;
        }
        
        // Set the next wing bitmap for animation
        setBitmap(getNextWingBitmap());
        
        // Update the position of the bird
        setY(newY);
        setX(newX);
    }

    // Method to get the next wing bitmap for animation
    private Bitmap getNextWingBitmap() {
        currentWingPosition = (currentWingPosition + 1) % 4; // Assuming there are 4 wing bitmaps
        return wingBitmaps.get(currentWingPosition);
    }
}
