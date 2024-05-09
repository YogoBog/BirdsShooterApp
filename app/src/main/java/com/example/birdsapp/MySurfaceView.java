package com.example.birdsapp;

import static com.example.birdsapp.MainActivity.isPaused;
import static com.example.birdsapp.MainActivity.isReset;
import static com.example.birdsapp.MainActivity.isShooting;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MySurfaceView extends SurfaceView implements Runnable {

    private SurfaceHolder holder = getHolder();
    private Canvas canvas;
    private List<Bullet> bullets;
    private List<Bird> birds;
    private List<Bitmap> wingBitmaps;
    private Random rand;
    private Plane plane;
    private Thread thread;
    private final int interval = 50;
    private Bitmap mBitmap;
    private Bitmap mBitmapBullet;
    private int mWidth;
    private int mHeight;
    private Context mContext;
    private Paint mPaint;
    private Joystick joystick;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);


        thread = new Thread(this);
        thread.start();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.air_plan);
        mBitmapBullet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bullet), 40, 100, false);

        joystick = new Joystick(150, 90, 275, mHeight - 250);

        plane = new Plane(mBitmap, mWidth, mHeight);

        birds = new ArrayList<>();

        bullets = new ArrayList<>();

        wingBitmaps = new ArrayList<>();

        rand = new Random();


        wingBitmaps.add(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bb2)
                , 150, 150, false));
        wingBitmaps.add(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bb3)
                , 150, 150, false));
        wingBitmaps.add(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bb4)
                , 150, 150, false));
        wingBitmaps.add(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bb5)
                , 150, 150, false));


        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));





    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed(x, y)) {
                    joystick.setPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getPressed()) {
                    joystick.setMovement(x, y);
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setPressed(false);
                joystick.resetMovement();
                return true;

        }

        return super.onTouchEvent(event);
    }


    @Override
    public void run() {
        while (true) {
            synchronized (holder) {
                if (isPaused) {
                    continue;
                }

                if (plane != null) {
                    plane.joystickMove(joystick);

                    if (isShooting) {
                        Bullet newBullet = new Bullet(mBitmapBullet, mWidth, mHeight, plane);
                        bullets.add(newBullet);
                        isShooting = false;
                    }

                    List<Bullet> bulletsToRemove = new ArrayList<>();
                    List<Bird> birdsToRemove = new ArrayList<>();

                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet != null) {
                            bullet.move();

                            if (bullet.getY() + bullet.getHeight() < 0) {
                                bulletsToRemove.add(bullet);
                            } else {
                                bullet.draw(canvas);
                            }

                            if (birds != null) {
                                for (Bird bird : birds) {
                                    if (bird != null && bullet != null && bird.collide(bullet)) {
                                        birdsToRemove.add(bird);
                                        bulletsToRemove.add(bullet);
                                    }
                                }
                            }

                        }
                    }
                    birds.removeAll(birdsToRemove);
                    bullets.removeAll(bulletsToRemove);

                    if (isReset) {
                        isReset = false;
                        birds.clear();
                        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
                        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
                        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
                        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
                        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
                    }

                }
                if (birds != null) {
                    for (Bird bird : birds) {
                        if (bird != null) {
                            bird.move();
                        }
                    }
                }


                drawSurface();
                SystemClock.sleep(interval);
            }
        }
    }


    private void drawSurface() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawPaint(mPaint);
            plane.draw(canvas);
            joystick.draw(canvas);
            joystick.update();

            for (Bird bird : birds) {
                bird.draw(canvas);
            }


            for (Bullet bullet : bullets) {
                bullet.draw(canvas);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }


}