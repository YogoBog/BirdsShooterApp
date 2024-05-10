package com.example.birdsapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

// Abstract class representing a sprite in a game
public abstract class Sprite {
    // Position variables
    private int x;  // x-coordinate of the sprite
    private int y;  // y-coordinate of the sprite

    // Bitmap representing the sprite's image
    private Bitmap bitmap;

    // Width and height of the sprite
    private int width;
    private int height;

    // Constructor for the Sprite class
    public Sprite(Bitmap bitmap, int width, int height) {
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
    }

    // Getter method for the x-coordinate
    public int getX() {
        return x;
    }

    // Setter method for the x-coordinate
    public void setX(int x) {
        this.x = x;
    }

    // Getter method for the y-coordinate
    public int getY() {
        return y;
    }

    // Setter method for the y-coordinate
    public void setY(int y) {
        this.y = y;
    }

    // Setter method for the bitmap
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    // Getter method for the width of the sprite
    public int getWidth() {
        return width;
    }

    // Getter method for the height of the sprite
    public int getHeight() {
        return height;
    }

    // Method to draw the sprite on a Canvas
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    // Method to check collision between two sprites
    public boolean collide(Sprite other) {
        // Check if the bounding rectangles of the two sprites overlap
        if (this.x <= other.x + other.bitmap.getWidth() &&
                other.x <= this.x + this.bitmap.getWidth() &&
                this.y <= other.y + other.bitmap.getHeight() &&
                other.y <= this.y + this.bitmap.getHeight()) {
            return true; // Collision detected
        }
        return false; // No collision
    }

    // Abstract method to move the sprite (to be implemented by subclasses)
    public abstract void move();
}
