package com.example.birdsapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Plane extends Sprite {

    private static final double MAX_SPEED = 700.0/30;
    private double vX;
    private double vY;
    public Plane(Bitmap bitmap, int width, int height) {
        super(bitmap, width, height);
        setX(width / 2 - 100);
        setY(height / 2);
    }

    public void moveUp() {
        int newY = getY() - 20;
        if (newY < 0) {
            newY = getHeight();
        }
        setY(newY);
    }

    public void moveLeft() {
        int newX = getX() - 20;
        if (newX < 0) {
            newX = getWidth();
        }
        setX(newX);
    }

    public void moveDown() {
        int newY = getY() + 20;
        if (newY > getHeight()) {
            newY = 0;
        }
        setY(newY);
    }

    public void joystickMove(Joystick joystick) {
        int newX = getX();
        int newY = getY();

        vX = joystick.getMoveX()*MAX_SPEED;
        vY = joystick.getMoveY()*MAX_SPEED;
        newX += vX;
        newY += vY;

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

        setY(newY);
        setX(newX);


    }


    @Override
    public void move() {
        int newX = getX() + 20;
        if (newX > getWidth()) {
            newX = 0;
        }
        setX(newX);
    }
}
