package com.example.birdsapp;

import android.graphics.Bitmap;

public class Bullet extends Sprite {

    private Plane plane;



    public Bullet(Bitmap bitmap, int width, int height, Plane plane) {
        super(bitmap, width, height);
        setX(plane.getX());
        setY(plane.getY());
    }

    @Override
    public void move() {
        int newY = getY() - 30;
        if (newY < 0) {
            setY(-getHeight());
        }
        setY(newY);
    }


}
