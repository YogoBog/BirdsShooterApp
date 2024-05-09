package com.example.birdsapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private int outCircPosX;
    private int outCircPosY;
    private int inCircPosX;
    private int inCircPosY;
    private int inCircRadius;
    private int outCircRadius;
    private Paint outCircPaint;
    private Paint inCircPaint;
    private float centerToTouchDis;
    private boolean isPressed;
    private float moveX;
    private float moveY;


    public Joystick(int outCircRadius, int inCircRadius, int centerPosX, int centerPosY) {
        this.outCircPosX = centerPosX;
        this.outCircPosY = centerPosY;
        this.inCircPosX = centerPosX;
        this.inCircPosY = centerPosY;

        this.inCircRadius = inCircRadius;
        this.outCircRadius = outCircRadius;

        outCircPaint = new Paint();
        outCircPaint.setColor(Color.GRAY);
        outCircPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        inCircPaint = new Paint();
        inCircPaint.setColor(Color.BLACK);
        inCircPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(outCircPosX, outCircPosY, outCircRadius, outCircPaint);
        canvas.drawCircle(inCircPosX, inCircPosY, inCircRadius, inCircPaint);
    }

    public boolean isPressed(float x, float y) {
        centerToTouchDis = (float) Math.sqrt(
                Math.pow(outCircPosX - x, 2) +
                        Math.pow(outCircPosY - y, 2)
        );
        return centerToTouchDis < outCircRadius;
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getPressed() {
        return isPressed;
    }

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

    public void resetMovement() {
        moveY = 0;
        moveX = 0;
    }

    public float getMoveX() {
        return moveX;
    }

    public float getMoveY() {
        return moveY;
    }

    public void update() {
        inCircPosX = (int) (outCircPosX + moveX * outCircRadius);
        inCircPosY = (int) (outCircPosY + moveY * outCircRadius);
    }
}
