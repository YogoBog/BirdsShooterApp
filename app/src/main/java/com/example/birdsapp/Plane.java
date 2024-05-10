package com.example.birdsapp;

import android.graphics.Bitmap;

// Plane class, represents a plane sprite in the game
public class Plane extends Sprite {

    // Maximum speed of the plane
    private static final double MAX_SPEED = 700.0 / 30;

    // Velocity components of the plane
    private double vX;
    private double vY;

    // Constructor for the Plane class
    public Plane(Bitmap bitmap, int width, int height) {
        super(bitmap, width, height);
        // Set the initial position of the plane to the center of the screen
        setX(width / 2 - 100);
        setY(height / 2);
    }

    // Method to move the plane upwards
    public void moveUp() {
        int newY = getY() - 20;
        if (newY < 0) {
            newY = getHeight();
        }
        setY(newY);
    }

    // Method to move the plane to the left
    public void moveLeft() {
        int newX = getX() - 20;
        if (newX < 0) {
            newX = getWidth();
        }
        setX(newX);
    }

    // Method to move the plane downwards
    public void moveDown() {
        int newY = getY() + 20;
        if (newY > getHeight()) {
            newY = 0;
        }
        setY(newY);
    }

    // Method to move the plane using a joystick input
    public void joystickMove(Joystick joystick) {
        // Get current position
        int newX = getX();
        int newY = getY();

        // Calculate velocity components based on joystick input and maximum speed
        vX = joystick.getMoveX() * MAX_SPEED;
        vY = joystick.getMoveY() * MAX_SPEED;

        // Update position based on velocity
        newX += vX;
        newY += vY;

        // Wrap around if the plane goes off the screen
        if (newX < 0) {
            newX = getWidth();
        }

        if (newX > getWidth()) {
            newX = 0;
        }

        if (newY > getHeight()) {
            newY = 0;
        }

        if (newY < 0) {
            newY = getHeight();
        }

        // Update the position of the plane
        setY(newY);
        setX(newX);
    }

    // Method to move the plane horizontally (override from abstract method in Sprite class)
    @Override
    public void move() {
        int newX = getX() + 20;
        if (newX > getWidth()) {
            newX = 0;
        }
        setX(newX);
    }
}
