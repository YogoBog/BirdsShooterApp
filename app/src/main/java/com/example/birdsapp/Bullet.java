package com.example.birdsapp;

import android.graphics.Bitmap;

public class Bullet extends Sprite {

    // Reference to the plane that fired this bullet
    private Plane plane;

    // Constructor for the Bullet class
    public Bullet(Bitmap bitmap, int width, int height, Plane plane) {
        // Call the superclass constructor to initialize the bullet sprite
        super(bitmap, width, height);
        
        // Set the initial position of the bullet to the same position as the plane
        setX(plane.getX());
        setY(plane.getY());
    }

    // Method to move the bullet
    @Override
    public void move() {
        // Move the bullet upward
        int newY = getY() - 30;
        
        // If the bullet goes off the top of the screen, reset its position
        if (newY < 0) {
            setY(-getHeight());
        }
        
        // Update the Y-coordinate of the bullet
        setY(newY);
    }
}
