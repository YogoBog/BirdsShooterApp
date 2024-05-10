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

    // SurfaceHolder to manage the surface's state
    private SurfaceHolder holder = getHolder();
    private Canvas canvas;
    private List<Bullet> bullets;
    private List<Bird> birds;
    private List<Bitmap> wingBitmaps;
    private Random rand;
    private Plane plane;
    private Thread thread;
    private final int interval = 50; // Game loop interval in milliseconds
    private Bitmap mBitmap;
    private Bitmap mBitmapBullet;
    private int mWidth;
    private int mHeight;
    private Context mContext;
    private Paint mPaint;
    private Joystick joystick;

    // Constructor
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        // Initialize paint for drawing
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);

        // Start the game loop thread
        thread = new Thread(this);
        thread.start();
    }


    // Called when the size of the view changes
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        // Load plane bitmap
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.air_plan);
        // Load bullet bitmap and scale it
        mBitmapBullet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bullet), 40, 100, false);

        // Initialize joystick
        joystick = new Joystick(150, 90, 275, mHeight - 250);

        // Initialize plane
        plane = new Plane(mBitmap, mWidth, mHeight);

        birds = new ArrayList<>();

        bullets = new ArrayList<>();

        wingBitmaps = new ArrayList<>();

        rand = new Random();

        // Load bird wing bitmaps and add them to the list
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

        // Add initial birds to the list
        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
        birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
    }


    // Handle touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Check if the joystick is pressed
                if (joystick.isPressed(x, y)) {
                    joystick.setPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                // If the joystick is pressed, update its position
                if (joystick.getPressed()) {
                    joystick.setMovement(x, y);
                }
                return true;
            case MotionEvent.ACTION_UP:
                // Reset joystick movement when touch is released
                joystick.setPressed(false);
                joystick.resetMovement();
                return true;
        }

        return super.onTouchEvent(event);
    }


    // Game loop
    @Override
    public void run() {
        while (true) {
            synchronized (holder) {
                if (isPaused) {
                    continue;
                }

                if (plane != null) {
                    // Move the plane based on joystick input
                    plane.joystickMove(joystick);

                    if (isShooting) {
                        // Create a new bullet when shooting
                        Bullet newBullet = new Bullet(mBitmapBullet, mWidth, mHeight, plane);
                        bullets.add(newBullet);
                        isShooting = false;
                    }

                    List<Bullet> bulletsToRemove = new ArrayList<>();
                    List<Bird> birdsToRemove = new ArrayList<>();

                    // Iterate through bullets and handle their movement and collisions
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet != null) {
                            bullet.move();

                            // Remove bullets that go off-screen
                            if (bullet.getY() + bullet.getHeight() < 0) {
                                bulletsToRemove.add(bullet);
                            } else {
                                bullet.draw(canvas);
                            }

                            // Check for collisions between birds and bullets
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
                    // Remove collided birds and bullets
                    birds.removeAll(birdsToRemove);
                    bullets.removeAll(bulletsToRemove);

                    // Reset birds if needed
                    if (isReset) {
                        isReset = false;
                        birds.clear();
                        // Add new birds
                        for (int i = 0; i < 5; i++) {
                            birds.add(new Bird(wingBitmaps, mWidth, mHeight, rand.nextInt(mWidth), rand.nextInt(mHeight)));
                        }
                    }

                }
                // Move birds
                if (birds != null) {
                    for (Bird bird : birds) {
                        if (bird != null) {
                            bird.move();
                        }
                    }
                }

                // Draw the game objects on the surface
                drawSurface();

                // Pause the game loop for the specified interval
                SystemClock.sleep(interval);
            }
        }
    }


    // Draw game objects on the surface
    private void drawSurface() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawPaint(mPaint);
            plane.draw(canvas);
            joystick.draw(canvas);
            joystick.update();

            // Draw birds
            for (Bird bird : birds) {
                bird.draw(canvas);
            }

            // Draw bullets
            for (Bullet bullet : bullets) {
                bullet.draw(canvas);
            }

            // Release the canvas
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
