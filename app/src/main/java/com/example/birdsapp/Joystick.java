package com.example.birdsapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    // Position of the outer circle
    private int outCircPosX;
    private int outCircPosY;
    
    // Position of the inner circle
    private int inCircPosX;
    private int inCircPosY;
    
    // Radius of the inner and outer circles
    private int inCircRadius;
    private int outCircRadius;
    
    // Paint objects for drawing the circles
    private Paint outCircPaint;
    private Paint inCircPaint;
    
    // Distance from center of outer circle to touch point
    private float centerToTouchDis;
    
    // Flag to indicate if the joystick is pressed
    private boolean isPressed;
    
    // Movement direction
    private float moveX;
    private float moveY;

    // Constructor for the Joystick class
    public Joystick(int outCircRadius, int inCircRadius, int centerPosX, int centerPosY) {
        // Initialize positions and radii
        this.outCircPosX = centerPosX;
        this.outCircPosY = centerPosY;
        this.inCircPosX = centerPosX;
        this.inCircPosY = centerPosY;
        this.inCircRadius = inCircRadius;
        this.outCircRadius = outCircRadius;

        // Initialize paint objects
        outCircPaint = new Paint();
        outCircPaint.setColor(Color.GRAY);
        outCircPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        inCircPaint = new Paint();
        inCircPaint.setColor(Color.BLACK);
        inCircPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    // Method to draw the joystick on the canvas
    public void draw(Canvas canvas) {
        canvas.drawCircle(outCircPosX, outCircPosY, outCircRadius, outCircPaint);
        canvas.drawCircle(inCircPosX, inCircPosY, inCircRadius, inCircPaint);
    }

    // Method to check if the joystick is pressed
    public boolean isPressed(float x, float y) {
        centerToTouchDis = (float) Math.sqrt(
                Math.pow(outCircPosX - x, 2) +
                        Math.pow(outCircPosY - y, 2)
        );
        return centerToTouchDis < outCircRadius;
    }

    // Method to set the pressed state of the joystick
    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    // Method to get the pressed state of the joystick
    public boolean getPressed() {
        return isPressed;
    }

    // Method to set the movement of the joystick
    public void setMovement(float x, float y) {
        float deltaX = x - outCircPosX;
        float deltaY = y - outCircPosY;
        float deltaDis = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if (deltaDis < outCircRadius) {
            moveX = deltaX / outCircRadius;
            moveY = deltaY / outCircRadius;
        } else {
            moveX = deltaX / deltaDis;
            moveY = deltaY / deltaDis;
        }
    }

    // Method to reset the movement of the joystick
    public void resetMovement() {
        moveY = 0;
        moveX = 0;
    }

    // Method to get the X-axis movement of the joystick
    public float getMoveX() {
        return moveX;
    }

    // Method to get the Y-axis movement of the joystick
    public float getMoveY() {
        return moveY;
    }

    // Method to update the position of the inner circle based on movement
    public void update() {
        inCircPosX = (int) (outCircPosX + moveX * outCircRadius);
        inCircPosY = (int) (outCircPosY + moveY * outCircRadius);
    }
}
