package game;


import edu.augustana.csc490.gamestarter.MainActivity;
import edu.augustana.csc490.gamestarter.MainGameView;
import maze.Line;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.Toast;

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
    private int time;
    public Mouse playerMouse;
    private Mouse[] opponentMice;
    private PowerUpMap powerUps;

    private int numOpponents;
    private int AIDifficulty;
    private boolean isNetworked;

    private Random rand;

    //mouse data
    public Picture playerMouseImage = new Picture();
    public Paint playerMousePaint = new Paint();
    public final Point MOUSE_START_POS= new Point(0, 0);
    public final Point MOUSE_END_POS= new Point(width, height);


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
    public static final int NUM_OPPONENTS = 3;
    public static final int TIME = 120;

    //AI settings
    public static final int AI_DIFFICULTY = 5;

    //network settings
    public static final boolean IS_NETWORKED = false;


    //creates a new game with the standard game data defined above in the final feilds
    public Game() {
        rand = new Random();
        initializeGame(WIDTH, HEIGHT, rand.nextInt(NUM_MAZE_TYPES), TIME, NUM_OPPONENTS, AI_DIFFICULTY, IS_NETWORKED);
    }

    public Game(int width, int height, int mazeType) {
        initializeGame(width, height, mazeType, TIME, NUM_OPPONENTS, AI_DIFFICULTY, IS_NETWORKED);
    }

    private void initializeGame(int mazeWidth, int mazeHeight, int mazeType, int time, int numOpponents, int AIDifficulty, boolean isNetworked) {
        height = mazeHeight;
        width = mazeWidth;
        this.mazeType = mazeType;
        maze = createMaze(mazeWidth, mazeHeight, mazeType);
        powerUps = new PowerUpMap(maze);
        this.time = time;
        this.numOpponents = numOpponents;
        this.AIDifficulty = AIDifficulty;
        playerMouse = new PlayerMouse(playerMousePaint, playerMouseImage);
        opponentMice = new Mouse[numOpponents];
        level = 1;

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

    private boolean levelUp() {
        playerMouse.moveMouse(MOUSE_START_POS.x, MOUSE_START_POS.y);
        height = height + 3;
        width = width + 3;
        maze = createMaze(width, height, mazeType);
        powerUps = new PowerUpMap(maze);
        level++;
        setTime(0);

        return true;
    }

    private void setTime(int t){
        time = t;
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

    public boolean movePlayerMouse(int prevX, int prevY, int newX, int newY) {
        int prevMazeX = prevX / cellWidth;
        int prevMazeY = prevY / cellHeight;
        int newMazeX = newX / cellWidth;
        int newMazeY = newY / cellHeight;

        //check if mouse has reached end of the maze before allowing to move
        if(maze.getEnd().equals(newMazeX,newMazeY)){
            Log.d("tag", "Leveling Up");
            levelUp();
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

    public Point getPlayerMousePos() {
        return new Point(playerMouse.getPosX(), playerMouse.getPosY());
    }

    private Bitmap generateMazeLineArrayBitmap(Paint p, int screenW, int screenH) {
        mazeLineArray = new MazeLineArray(maze, screenW, screenH);
        screenWidth = mazeLineArray.getScreenWidth();
        screenHeight = mazeLineArray.getScreenHeight();
        cellWidth = mazeLineArray.getWidthSpacing();
        cellHeight = mazeLineArray.getHeightSpacing();

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
        }/* else if (!maze.equals(mazeLineArray.getMaze())) {
            generateMazeLineArrayBitmap(p, screenWidth, screenHeight);
            Log.i("mazeLineArrayGenerator", "Equals method");
        }*/
        c.drawBitmap(mazeBitmap, 0, 0, p);



    }
}
