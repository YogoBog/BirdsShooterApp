package com.example.birdsapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public abstract class Sprite {
    private int x;
    private int y;
    private Bitmap bitmap;
    private int width;
    private int height;

    public Sprite(Bitmap bitmap, int width, int height) {
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public boolean collide(Sprite other) {
        if (this.x <= other.x + other.bitmap.getWidth() &&
                other.x <= this.x + this.bitmap.getWidth() &&
                this.y <= other.y + other.bitmap.getHeight() &&
                other.y <= this.y + this.bitmap.getHeight()) {
            return true;
        }
        return false;
    }


    public abstract void move();

}
