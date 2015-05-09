package edu.augustana.csc490.ratrace;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.augustana.csc490.gamestarter.R;
import game.*;

public class MainGameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "RatRace"; // for Log.w(TAG, ...)
    public static MainGameView currentGameView=null;

    private GameThread gameThread; // runs the main game loop
    private Activity mainActivity; // keep a reference to the main Activity

    private boolean isGameOver = true;

    private int screenWidth;
    private int screenHeight;

    private Paint myPaint;
    private Paint mazePaint;
    private Paint backgroundPaint;

    private int height;
    private int width;

    static String initials = "";
    static String sessionID = "";


    long startTime;
    long millis;
    static int points = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    //Adapted from http://stackoverflow.com/questions/4597690/android-timer-how
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            millis = System.currentTimeMillis() - startTime;
            int centiSeconds = (int) (millis / 10);
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            centiSeconds = centiSeconds % 100;

            ActionBar actionBar = mainActivity.getActionBar();
            actionBar.setTitle("Time: " + String.format("%d:%02d:%02d", minutes, seconds, centiSeconds) + "   Score: " + points);
            timerHandler.postDelayed(this, 5);
        }
    };


    private Game game;

    public MainGameView(Context context, AttributeSet atts) {
        super(context, atts);
        mainActivity = (Activity) context;
        currentGameView = this;

        getHolder().addCallback(this);

        myPaint = new Paint();
        myPaint.setColor(Color.CYAN);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        mazePaint = new Paint();
        mazePaint.setColor(Color.BLUE);
        mazePaint.setStyle(Paint.Style.STROKE);
        mazePaint.setStrokeJoin(Paint.Join.ROUND);
        mazePaint.setStrokeCap(Paint.Cap.ROUND);
       

        /* set width and height based on value entered by user and the
        algorithm based on the option chosen by the user in IntroActivity */
        Intent i = mainActivity.getIntent();
        height = i.getIntExtra("size", 20);
        width = i.getIntExtra("size", 20);

        //Grab the initials and a UUID for the high scores board
        initials = i.getStringExtra("initials");
        sessionID = UUID.randomUUID().toString().replace("-","");




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
        int numOpponents = 0; //maximum value is 3
        int numPowerUpTypes = 3;
        Bitmap miceImageArray[] = new Bitmap[4];
        // Mouse taken from https://openclipart.org/ under their Unlimited Commercial Use:
        // https://openclipart.org/unlimited-commercial-use-clipart
        miceImageArray[0] = BitmapFactory.decodeResource(getResources(), R.raw.simplemouseright);
        miceImageArray[1] = BitmapFactory.decodeResource(getResources(), R.raw.simplemousewhite);
        miceImageArray[2] = BitmapFactory.decodeResource(getResources(), R.raw.simplemousebrown);
        miceImageArray[3] = BitmapFactory.decodeResource(getResources(), R.raw.simplemousepink);

        Bitmap powerUpImageArray[] = new Bitmap[numPowerUpTypes];
        powerUpImageArray[0] = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.small_cheese_swiss);
        powerUpImageArray[1] = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.sandwich);
        powerUpImageArray[2] = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.applecore);

        game = new Game(width, height, miceImageArray, powerUpImageArray, numOpponents);

        startTime = System.currentTimeMillis();
        millis = 0;

        //This statement was taken from http://stackoverflow.com/questions/4597690/android-timer-how
        timerHandler.postDelayed(timerRunnable, 0);

        if (isGameOver) {
            isGameOver = false;
            gameThread = new GameThread(getHolder());
            gameThread.start(); // start the main game loop going
        }
    }

    private void gameStep() {
        if (game.levelUp()) { //levels up if all the mice are finished
            startTime = System.currentTimeMillis(); //resets the timer if there is a level up
        }

        game.setTime(millis); //sets the timer value in the game class to equal the value in this view
        points = game.playerMouse.addPoints(0); //populates points to activityBar
        if (!game.isNetworked()) {
            if (start) {
                game.moveAIMice();
            }
        } //TODO fix the AI problems
    }

    //Adapted from http://code.tutsplus.com/tutorials/android-sdk-create-an-arithmetic-game-high-scores-and-state-data--mobile-18825
    public static void setHighScore(){
        int thisScore = points;


        if(thisScore > 0){
            SharedPreferences.Editor scoreEdit = MainActivity.gameScores.edit();
            String scores = MainActivity.gameScores.getString("highScores", "");

            if(scores.length() >0){
                List<Score> scoreStrings = new ArrayList<Score>();
                String[] exScores = scores.split("\\|"); //to split the string passed into gameScores
                for(String eSc : exScores){
                    String[] parts = eSc.split(" - ");
                    scoreStrings.add(new Score(parts[0],parts[1],Integer.parseInt(parts[2])));
                }

                Score newScore = new Score(sessionID, initials,thisScore);
                //this for loop eliminates more than one score per session
                for(Score score:scoreStrings){
                    if(score.getUuid().equals(newScore.getUuid())){
                        scoreStrings.remove(score);
                    }
                }
                scoreStrings.add(newScore);

                Collections.sort(scoreStrings);

                StringBuilder scoreBuild = new StringBuilder("");
                for(int s=0; s<scoreStrings.size(); s++){
                    if(s>=10) break;//only want ten
                    if(s>0) scoreBuild.append("|");//pipe separate the score strings
                    Score topScore =scoreStrings.get(s);
                    scoreBuild.append(topScore.getUuid()+" - "+topScore.getInitials()+" - "+topScore.getScoreText());
                }
                //write to prefs
                scoreEdit.putString("highScores", scoreBuild.toString());
                scoreEdit.commit();
            } else {
                scoreEdit.putString("highScores", sessionID+" - "+initials+" - "+thisScore);
                scoreEdit.commit();
            }

        }
    }

    public void updateView(Canvas canvas) {
        if (canvas != null) {
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
            game.drawPowerUps(canvas, screenWidth, screenHeight);
            game.paintMaze(canvas, mazePaint, screenWidth, screenHeight);
            game.drawMice(canvas, screenWidth, screenHeight);
            start = true;
        }
    }

    // stop the game; may be called by the MainGameFragment onPause
    public void stopGame() {
        setHighScore(); //save score
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
        //setHighScore(); //save score

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

    boolean start = false;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            int actionX = (int) e.getX();
            int actionY = (int) e.getY();
            game.movePlayerMouse(actionX, actionY);
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
                        updateView(canvas); // draw using the canvas

                    }
                    Thread.sleep(10); // if you want to slow down the action...
                } catch (InterruptedException ex) {
                    Log.e(TAG, ex.toString());
                } finally  // regardless if any errors happen...
                {
                    // make sure we unlock canvas so other threads can use it
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    gameStep();         // update game state
                }
            }
        }
    }
}