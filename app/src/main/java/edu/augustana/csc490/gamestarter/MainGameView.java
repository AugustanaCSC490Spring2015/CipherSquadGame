package edu.augustana.csc490.gamestarter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import game.*;

public class MainGameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GameStarter"; // for Log.w(TAG, ...)

    private GameThread gameThread; // runs the main game loop
    private Activity mainActivity; // keep a reference to the main Activity

    private boolean isGameOver = true;

    private int x; //blue circle x
    private int y; //blue circle y
    private int screenWidth;
    private int screenHeight;

    private Paint myPaint;
    private Paint mazePaint;
    private Paint backgroundPaint;

    private int height;
    private int width;
    private int algorithm;

    long startTime;
    long millis;

    //runs without a timer by reposting this handler at the end of the runnable
    //Adapted from http://stackoverflow.com/questions/4597690/android-timer-how
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            ActionBar actionBar = mainActivity.getActionBar();
            actionBar.setTitle(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };


    private Game game;

    public MainGameView(Context context, AttributeSet atts) {
        super(context, atts);
        mainActivity = (Activity) context;

        getHolder().addCallback(this);

        myPaint = new Paint();
        myPaint.setColor(Color.CYAN);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        mazePaint = new Paint();
        mazePaint.setColor(Color.BLUE);
        mazePaint.setStrokeWidth(3);

        Intent i = mainActivity.getIntent();
        height = i.getIntExtra("size", 20);
        width = i.getIntExtra("size", 20);
        algorithm = i.getIntExtra("algorithm", 1);


    }

    // called when the size changes (and first time, when view is created)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w;
        screenHeight = h;

        startNewGame();
    }

    public void startNewGame() {

        game = new Game(width, height, algorithm);
        startTime = System.currentTimeMillis();
        millis = 0;
        Point initPosPlayerMouse = game.getPlayerMousePos();

        this.x = initPosPlayerMouse.x;
        this.y = initPosPlayerMouse.y;

        //This statement was taken from http://stackoverflow.com/questions/4597690/android-timer-how
        timerHandler.postDelayed(timerRunnable, 0);

        if (isGameOver) {
            isGameOver = false;
            gameThread = new GameThread(getHolder());
            gameThread.start(); // start the main game loop going
        }
    }


    private void gameStep() {

    }

    public long getCurrentTime(){
        return millis;
    }

    public void updateView(Canvas canvas) {
        if (canvas != null) {
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
            game.paintMaze(canvas, mazePaint, screenWidth, screenHeight);

            //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.raw.simplemousedown), 10, 10, null);

            canvas.drawCircle(game.getPlayerMousePos().x, game.getPlayerMousePos().y, game.playerMouse.getCircleSize(), game.playerMouse.getMousePaint());
        }
    }

    // stop the game; may be called by the MainGameFragment onPause
    public void stopGame() {
        if (gameThread != null)
            gameThread.setRunning(false);
            timerHandler.removeCallbacks(timerRunnable);
    }

    // release resources; may be called by MainGameFragment onDestroy
    public void releaseResources() {
        // release any resources (e.g. SoundPool stuff)
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // called when the surface is destroyed
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // ensure that thread terminates properly
        boolean retry = true;
        gameThread.setRunning(false); // terminate gameThread

        while (retry) {
            try {
                gameThread.join(); // wait for gameThread to finish
                retry = false;
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread interrupted", e);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            int actionX = (int) e.getX();
            int actionY = (int) e.getY();
            game.movePlayerMouse(game.getPlayerMousePos().x, game.getPlayerMousePos().y, actionX, actionY);
        }

        return true;
    }

    // Thread subclass to run the main game loop
    private class GameThread extends Thread {
        private SurfaceHolder surfaceHolder; // for manipulating canvas
        private boolean threadIsRunning = true; // running by default

        // initializes the surface holder
        public GameThread(SurfaceHolder holder) {
            surfaceHolder = holder;
            setName("GameThread");
        }

        // changes running state
        public void setRunning(boolean running) {
            threadIsRunning = running;
        }

        @Override
        public void run() {
            Canvas canvas = null;

            while (threadIsRunning) {
                try {
                    // get Canvas for exclusive drawing from this thread
                    canvas = surfaceHolder.lockCanvas(null);

                    // lock the surfaceHolder for drawing
                    synchronized (surfaceHolder) {
                        gameStep();         // update game state
                        updateView(canvas); // draw using the canvas
                    }
                    Thread.sleep(10); // if you want to slow down the action...
                } catch (InterruptedException ex) {
                    Log.e(TAG, ex.toString());
                } finally  // regardless if any errors happen...
                {
                    // make sure we unlock canvas so other threads can use it
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}