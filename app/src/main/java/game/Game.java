package game;


import maze.Line;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import maze.*;

import java.util.Random;

/**
 * Created by Ethan Halsall on 3/30/2015.
 */
public class Game {

    //game data
    private Maze maze;
    private MazeLineArray mazeLineArray;

    //maze line array data
    private int screenWidth;
    private int screenHeight;

    //graphics
    public Bitmap mazeBitmap;


    //maze data
    private int cellWidth;
    private int cellHeight;
    private int width;
    private int height;
    private int mazeType;


    private Path playerPath;
    private Path[] AISolutions;
    private Path[] AIPaths;

    private int[] playerPoints;
    private int level;
    private long currentTime;
    private int levelPointRelationship;
    public Mouse playerMouse;
    private Mouse[] opponentMice;
    private PowerUpMap powerUps;

    private int numOpponents;
    private int AIDifficulty;
    private boolean isNetworked;

    private Random rand;

    //mouse data
    private Bitmap[] miceImageArray;
    private Bitmap playerMouseImage;
    private Bitmap[] opponentMiceImages;
    private int oldCellWidth;
    private int oldCellHeight;
    //public Paint playerMousePaint = new Paint();

    private Point mouseStartPos;
    private final float START_ANGLE = 45;


    //default game settings

    //maze dimensions
    public static final int HEIGHT = 10;
    public static final int WIDTH = 10;
    //maze types
    public static final int NUM_MAZE_TYPES = 3;
    public static final int RECURSIVE_BACKTRACKER_MAZE = 1;
    public static final int HUNT_AND_KILL_MAZE = 2;
    public static final int PRIM_MAZE = 3;

    //player settings
    public static final int NUM_OPPONENTS = 0;

    //AI settings
    public static final int AI_DIFFICULTY = 5;

    //network settings
    public static final boolean IS_NETWORKED = false;


    //creates a new game with the standard game data defined above in the final fields
    public Game(Bitmap[] miceImageArray) {
        rand = new Random();
        initializeGame(WIDTH, HEIGHT, rand.nextInt(NUM_MAZE_TYPES), miceImageArray, NUM_OPPONENTS, AI_DIFFICULTY, IS_NETWORKED);
    }

    public Game(int width, int height, int mazeType, Bitmap[] miceImageArray) {
        initializeGame(width, height, mazeType, miceImageArray, NUM_OPPONENTS, AI_DIFFICULTY, IS_NETWORKED);
    }

    private void initializeGame(int mazeWidth, int mazeHeight, int mazeType, Bitmap[] miceImageArray, int numOpponents, int AIDifficulty, boolean isNetworked) {
        height = mazeHeight;
        width = mazeWidth;
        this.mazeType = mazeType;
        maze = createMaze(mazeWidth, mazeHeight, mazeType);
        powerUps = new PowerUpMap(maze);
        this.numOpponents = numOpponents;
        this.AIDifficulty = AIDifficulty;
        this.miceImageArray = miceImageArray;
        for (int i = 1; i <= miceImageArray.length; i++) {
            //opponentMiceImages[i - 1] = miceImageArray[i];
        }

        //Picture playerMouseImage = new Picture();
        //Bitmap playerMouseImage = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.simplemousedown);
        playerMouse = new PlayerMouse();
        opponentMice = new Mouse[numOpponents];
        level = 1;
        levelPointRelationship = 1000;

        this.isNetworked = isNetworked;
        for (int i = 0; i < numOpponents; i++) {
            if (isNetworked) {
                opponentMice[i] = new NetworkedMouse();
            } else {
                opponentMice[i] = new AIMouse();
            }
        }
        // mazeWalls = new MazeLineArray(maze, screenWidth, screenHeight);
    }

    public void mouseFinished(Mouse mouse) {
        //keeps track of each player's points and adds points depending on the level they are on and the time they completed the maze
        int points = levelPointRelationship - (int) currentTime / 60000;
        mouse.setTotalTime(currentTime);
        if (points > 0){ mouse.addPoints(points);}
        mouse.setFinished(true);

    }

    public boolean levelUp() {
        //display player stats
        //reset the game with a larger maze
        if (!playerMouse.getFinished()) { //add for loop here for each mouse if we have multiple players
            return false;
        }
        playerMouse.setMouseAngle(START_ANGLE);
        playerMouse.moveMouse(mouseStartPos.x, mouseStartPos.y);
        playerMouse.setFinished(false);
        height = height + 3;
        width = width + 3;
        maze = createMaze(width, height, mazeType);

        level++;
        levelPointRelationship *= 2;
        setTime(0);

        return true;
    }

    public void setTime(long t) {
        currentTime = t;
    }

    private Maze createMaze(int width, int height, int mazeType) {
        MazeGenerator mazeGen;
        switch (mazeType) {
            case PRIM_MAZE:
                mazeGen = new PrimMaze(width, height);
                break;
            case RECURSIVE_BACKTRACKER_MAZE:
                mazeGen = new RecursiveBacktrackerMaze(width, height);
                break;
            case HUNT_AND_KILL_MAZE:
                mazeGen = new HuntAndKillMaze(width, height);
                break;
            default:
                mazeGen = new RecursiveBacktrackerMaze(width, height);
        }
        return mazeGen.getMaze();
    }

    public boolean movePlayerMouse(int newX, int newY) {
        int prevMazeX = playerMouse.getPosX() / cellWidth;
        int prevMazeY = playerMouse.getPosY() / cellHeight;
        int newMazeX = newX / cellWidth;
        int newMazeY = newY / cellHeight;

        //will check if mouse has reached the end of the maze prior to moving
        if(maze.getEnd().equals(prevMazeX,prevMazeY)){
            mouseFinished(playerMouse);
        }

        if (prevMazeX == newMazeX && prevMazeY == newMazeY) {
            playerMouse.moveMouse(newX, newY);
            return true;
        }

        if (Math.abs(newMazeX - prevMazeX) + Math.abs(newMazeY - prevMazeY) != 1){
            return false;
        }

        int direction = maze.getDirection(prevMazeX, prevMazeY, newMazeX, newMazeY);

        if (!maze.isWallPresent(prevMazeX, prevMazeY, direction)) {
            playerMouse.moveMouse(newX, newY);
            return true;
        }
        return false;
    }

    private Bitmap generateMazeLineArrayBitmap(Paint p, int screenW, int screenH) {
        mazeLineArray = new MazeLineArray(maze, screenW, screenH);
        screenWidth = mazeLineArray.getScreenWidth();
        screenHeight = mazeLineArray.getScreenHeight();
        cellWidth = mazeLineArray.getWidthSpacing();
        cellHeight = mazeLineArray.getHeightSpacing();
        p.setStrokeWidth(cellWidth / 6);

        mazeBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mazeBitmap);

        for (int i = 0; i < mazeLineArray.getSize(); i++) {
            Line line = mazeLineArray.getLineAtIndex(i);
            c.drawLine(line.startX, line.startY, line.endX, line.endY, p);
            //Log.i("line", line.startX + " " + line.startY + " " + line.endX + " " +line.endY);
        }
        return mazeBitmap;
    }

    public void paintMaze(Canvas c, Paint p, int screenWidth, int screenHeight) {
        //Cell start = maze.getStart();
        //Cell end = maze.getEnd();

        //check to see if the maze line array needs to be generated or regenerated.
        if (mazeLineArray == null) {
            generateMazeLineArrayBitmap(p, screenWidth, screenHeight);
            //Log.i("mazeLineArrayGenerator", "Null");
        } else if (screenHeight != mazeLineArray.getScreenHeight() || screenWidth != mazeLineArray.getScreenWidth()) {
            generateMazeLineArrayBitmap(p, screenWidth, screenHeight);
            //Log.i("mazeLineArrayGenerator", "Screen size change");
        } else if (!maze.equals(mazeLineArray.getMaze())) {
            generateMazeLineArrayBitmap(p, screenWidth, screenHeight);
            Log.i("mazeLineArrayGenerator", "Equals method");
        }
        c.drawBitmap(mazeBitmap, 0, 0, p);
    }

    public void drawMice(Canvas c, int screenWidth, int screenHeight) {
        //check to see if the mice images need to be generated or regenerated
        if (playerMouse.getImage() == null) {
            //scale and add the mouse images
            createMiceBitmaps();
        } else if (cellWidth != oldCellWidth || cellHeight != oldCellHeight) {
            createMiceBitmaps();
        }
        Bitmap rotatedMouse = rotateMouseImage(playerMouse);
        c.drawBitmap(rotatedMouse, playerMouse.getPosX() - (rotatedMouse.getWidth() / 2), playerMouse.getPosY() - (rotatedMouse.getHeight() / 2), null);
    }

    private void createMiceBitmaps() {
        int scaleWidth = (cellWidth) / 4;
        //int scaleHeight = (cellWidth) / 4;
        mouseStartPos = new Point(cellWidth / 2, cellHeight / 2);
        playerMouse.moveMouse(mouseStartPos.x, mouseStartPos.y);
        playerMouseImage = miceImageArray[0].createScaledBitmap(miceImageArray[0], cellWidth + scaleWidth, cellWidth + scaleWidth, false);
        playerMouse.setMouseImage(playerMouseImage);
        playerMouse.setMouseAngle(START_ANGLE);
        oldCellWidth = mazeLineArray.getWidthSpacing();
        oldCellHeight = mazeLineArray.getHeightSpacing();
    }


    private int xAtLastRotate;
    private int yAtLastRotate;

    private Bitmap rotateMouseImage(Mouse mouse) {
        double deltaX = mouse.getPosX() - xAtLastRotate;
        double deltaY = mouse.getPosY() - yAtLastRotate;
        float angle = mouse.getAngle();
        if (deltaX != 0 && deltaY != 0 && mouse.rotate()) {
            angle = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
            mouse.setMouseAngle(angle);
            xAtLastRotate = mouse.getPosX();
            yAtLastRotate = mouse.getPosY();
        }
        //Log.i("Angle", "Angle for rotation " + angle);
        Matrix matrix = new Matrix();
        //matrix.setTranslate(mouse.getPosX() - (mouse.getImage().getWidth() / 2), mouse.getPosY() - (mouse.getImage().getHeight() / 2));
        matrix.setRotate(angle);
        Bitmap targetBitmap = Bitmap.createBitmap(mouse.getImage().getWidth(), mouse.getImage().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        matrix.setRotate(angle, mouse.getImage().getWidth() / 2, mouse.getImage().getHeight() / 2);
        canvas.drawBitmap(mouse.getImage(), matrix, new Paint());
        return targetBitmap;
    }
}
