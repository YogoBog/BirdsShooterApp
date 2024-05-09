package com.example.birdsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.List;
import java.util.Random;

public class Bird extends Sprite{

    private List<Bitmap> wingBitmaps;
    private int currentWingPosition;
    public Bird(List<Bitmap> wingBitmaps, int width, int height, int x, int y) {
        super(wingBitmaps.get(0), width, height);
        this.wingBitmaps = wingBitmaps;
        this.currentWingPosition = 0;
        setX(x);
        setY(y);
    }



    @Override
    public void move() {

        Random rand = new Random();


        int newX = getX() + rand.nextInt(50);
        int newY = getY() + rand.nextInt(50);
        if (newX > getWidth()) {
            newX = 0;
        }
        if (newY > getHeight()){
            newY = 0;
        }
        setBitmap(getNextWingBitmap());
        setY(newY);
        setX(newX);
    }

    private Bitmap getNextWingBitmap() {
        currentWingPosition = (currentWingPosition + 1) % 4;
        return wingBitmaps.get(currentWingPosition);
    }
}
