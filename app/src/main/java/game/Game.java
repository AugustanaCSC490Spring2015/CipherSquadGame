package game;


import edu.augustana.csc490.ratrace.MainGameView;
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
    private RecursiveBacktrackerMazeGenerator mazeGen;
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

    private int[] playerPoints;
    private int level;
    private long currentTime;
    private int levelPointRelationship;
    public Mouse playerMouse;
    private Mouse[] opponentMice;
    private PowerUpMap powerUps;

    private int numOpponents;
    private int aiDifficulty;
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
    public static final int HEIGHT = 5;
    public static final int WIDTH = 5;

    //player settings
    public static final int NUM_OPPONENTS = 3;

    //AI settings
    public static final int AI_DIFFICULTY = 50;

    //network settings
    public static final boolean IS_NETWORKED = false;


    //creates a new game with the standard game data defined above in the final fields
    public Game(Bitmap[] miceImageArray) {
        initializeGame(WIDTH, HEIGHT, miceImageArray, NUM_OPPONENTS, AI_DIFFICULTY, IS_NETWORKED);
    }

    public Game(int width, int height, Bitmap[] miceImageArray, int numOpponents) {
        initializeGame(width, height, miceImageArray, numOpponents, AI_DIFFICULTY, IS_NETWORKED);
    }

    private void initializeGame(int mazeWidth, int mazeHeight, Bitmap[] miceImageArray, int numOpponents, int AIDifficulty, boolean isNetworked) {
        height = mazeHeight;
        width = mazeWidth;
        this.mazeType = mazeType;
        maze = new Maze(mazeWidth, mazeHeight);
        mazeGen = new RecursiveBacktrackerMazeGenerator(maze);
        this.numOpponents = numOpponents;
        this.aiDifficulty = AIDifficulty;
        this.miceImageArray = miceImageArray;
        rand = new Random();

        // add the mouse images
        for (int i = 1; i <= miceImageArray.length; i++) {
            //opponentMiceImages[i - 1] = miceImageArray[i];
        }

        //Picture playerMouseImage = new Picture();
        //Bitmap playerMouseImage = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.simplemousedown);
        playerMouse = new PlayerMouse();
        level = 1;
        levelPointRelationship = 1000;

        //adds additional mice to represent other players
        this.isNetworked = isNetworked;

        if (isNetworked) {
            opponentMice = new NetworkedMouse[numOpponents];
        } else {
            opponentMice = new AIMouse[numOpponents];
        }

        for (int i = 0; i < numOpponents; i++) {
            if (isNetworked) {
                opponentMice[i] = new NetworkedMouse();
            } else {
                opponentMice[i] = new AIMouse(maze);
            }
        }

        // mazeWalls = new MazeLineArray(maze, screenWidth, screenHeight);
    }

    public void mouseFinished(Mouse mouse) {
        //keeps track of each player's points and adds points depending on the level they are on and the time they completed the maze
        int points = levelPointRelationship - (int) currentTime / 60000;
        mouse.setTotalTime(currentTime);
        if (points > 0) {
            mouse.addPoints(points);
        }
        mouse.setFinished(true);

    }

    public boolean levelUp() {
        //display player stats
        //reset the game with a larger maze
        if (!playerMouse.getFinished()) { //add for loop here for each mouse if we have multiple players
            return false;
        }
        for (Mouse m : opponentMice) { // TODO fix the AI mice
            if (!m.getFinished()){
                return false;
            }
        }
        MainGameView.setHighScore(); //save score before level change

        playerMouse.setMouseAngle(START_ANGLE);
        playerMouse.moveMouse(mouseStartPos.x, mouseStartPos.y);
        playerMouse.setFinished(false);
        height = height + 1;
        width = width + 1;
        maze = new Maze(width, height);
        mazeGen = new RecursiveBacktrackerMazeGenerator(maze);

        //reset playermouse location
        playerMouse.setMouseAngle(START_ANGLE);
        playerMouse.moveMouse(mouseStartPos.x, mouseStartPos.y);
        playerMouse.setFinished(false);

        //reset opponent mouse
        for (Mouse m : opponentMice) { // TODO fix the AI mice
            m.setMouseAngle(START_ANGLE);
            m.moveMouse(mouseStartPos.x, mouseStartPos.y);
            m.setFinished(false);
            if (!isNetworked) {
                m.levelUp(maze);
            }
        }

        level++;
        powerUps = new PowerUpMap(maze, screenWidth, screenHeight, width, height, level);
        levelPointRelationship *= 2;
        setTime(0);

        return true;
    }

    public void setTime(long t) {
        currentTime = t;
    }

    public boolean isNetworked() {
        return isNetworked;
    }

    public boolean movePlayerMouse(int newX, int newY) {
        if (playerMouse.getFinished()) {
            return false;
        }

        int prevMazeX = playerMouse.getPosX() / cellWidth;
        int prevMazeY = playerMouse.getPosY() / cellHeight;
        int newMazeX = newX / cellWidth;
        int newMazeY = newY / cellHeight;

        //will check if mouse has reached the end of the maze prior to moving
        if (maze.getEnd().x + 1 == newMazeX && maze.getEnd().y == prevMazeY) {
            mouseFinished(playerMouse);
            return true;
        }
        //moves the mouse on the screen if it has not moved into a new maze cell
        if (prevMazeX == newMazeX && prevMazeY == newMazeY) {
            playerMouse.moveMouse(newX, newY);
            return true;
        }
        //prevents the mouse from hopping walls and moving outside of the maze
        if (Math.abs(newMazeX - prevMazeX) + Math.abs(newMazeY - prevMazeY) != 1 || !(maze.checkLocation(new Point(newMazeX, newMazeY)))) {
            return false;
        }
        //moves the mouse into a new maze cell
        int direction = maze.getDirection(prevMazeX, prevMazeY, newMazeX, newMazeY);

        if (!maze.isWallPresent(new Point(prevMazeX, prevMazeY), direction)) {
            playerMouse.moveMouse(newX, newY);
            playerMouse.setMazePos(new Point(newMazeX, newMazeY));
            assignPowerUp(playerMouse,newMazeX,newMazeY);
            return true;
        }
        return false;
    }

    public void moveAIMice() {
        int randomMouse = rand.nextInt(numOpponents);
        Log.i("random mouse: ", "" + randomMouse);
        moveAIMouse((AIMouse) opponentMice[randomMouse]);
    }

    private boolean moveAIMouse(AIMouse mouse) {
        Point prevMazeCell = mouse.getMazePos();
        Point nextMazeCell = mouse.aiPath.peek(); //used for direction

        Point prevScreenPos = new Point(mouse.getPosX(), mouse.getPosY());
        Point newScreenPos;

        //moves the mouse on the screen if it has not moved into a new maze cell
        int direction = maze.getDirection(prevMazeCell.x, prevMazeCell.y, nextMazeCell.x, nextMazeCell.y);
        int length = rand.nextInt(aiDifficulty) + aiDifficulty / 2; //randomizes the length of movement
        switch (direction) {
            case Maze.N:
                newScreenPos = new Point(prevScreenPos.x, prevScreenPos.y - length % cellHeight);
                break;
            case Maze.E:
                newScreenPos = new Point(prevScreenPos.x + length % cellWidth, prevScreenPos.y);
                break;
            case Maze.S:
                newScreenPos = new Point(prevScreenPos.x, prevScreenPos.y + length % cellHeight);
                break;
            case Maze.W:
                newScreenPos = new Point(prevScreenPos.x - length % cellWidth, prevScreenPos.y);
                break;
            default:
                newScreenPos = prevScreenPos;
                break;
        }
        Point newMazeCell = new Point(newScreenPos.x / cellWidth, newScreenPos.y / cellHeight);

        //will check if mouse has reached the end of the maze prior to moving
        if (maze.getEnd().x + 1 == newMazeCell.x && maze.getEnd().y == newMazeCell.y) {
            mouseFinished(mouse);
            return true;
        }

        //moves the mouse on the screen if the mouse has not traversed a maze cell
        if (prevMazeCell.x == newMazeCell.y && prevMazeCell.y == newMazeCell.y) {
            mouse.moveMouse(newScreenPos.x, newScreenPos.y);
            return true;
        }


        //prevents the mouse from hopping walls and moving outside of the maze
        if (Math.abs(nextMazeCell.x - prevMazeCell.x) + Math.abs(nextMazeCell.x - prevMazeCell.y) != 1 || !(maze.checkLocation(nextMazeCell))) {
            mouse.aiPath.pop();
            return false;
        }


        //moves the mouse to the new maze cell
            mouse.moveMouse(newScreenPos.x, newScreenPos.y);
        mouse.setMazePos(mouse.aiPath.pop());
            return true;

    }

    private Bitmap generateMazeLineArrayBitmap(Paint p, int screenW, int screenH) {
        mazeLineArray = new MazeLineArray(maze, screenW, screenH);
        screenWidth = mazeLineArray.getScreenWidth();
        screenHeight = mazeLineArray.getScreenHeight();
        cellWidth = mazeLineArray.getWSpacing();
        cellHeight = mazeLineArray.getHSpacing();
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
        } else if (maze != mazeLineArray.getMaze()) {
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
        for (Mouse m : opponentMice) {
            rotatedMouse = rotateMouseImage(m);
            c.drawBitmap(rotatedMouse, m.getPosX() - (rotatedMouse.getWidth() / 2), m.getPosY() - (rotatedMouse.getHeight() / 2), null);
        }
    }

    private void createMiceBitmaps() {
        int scaleWidth = (cellWidth) / 4;
        int scaleHeight = (cellWidth) / 4;
        //make bitmap for the playermouse
        mouseStartPos = new Point(cellWidth / 2, cellHeight / 2);
        playerMouse.moveMouse(mouseStartPos.x, mouseStartPos.y);
        playerMouse.setMouseImage(miceImageArray[0].createScaledBitmap(miceImageArray[0], cellWidth + scaleWidth, cellWidth + scaleWidth, false));
        playerMouse.setMouseAngle(START_ANGLE);

        //make bitmaps for all the opponents
        int opponent = 1;
        for (Mouse m : opponentMice) {
            m.setMouseImage(miceImageArray[opponent].createScaledBitmap(miceImageArray[opponent], cellWidth + scaleWidth, cellWidth + scaleWidth, false));
            m.setMouseAngle(START_ANGLE);
            opponent++;
        }
        oldCellWidth = mazeLineArray.getWSpacing();
        oldCellHeight = mazeLineArray.getHSpacing();
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

    // draws the powerups, but first creates the powerup map if it has not been created yet
    public void drawPowerUps(Canvas c, int screenWidth, int screenHeight) {
        if (powerUps == null) {
            powerUps = new PowerUpMap(maze, screenWidth, screenHeight, width, height, level);
        }
        powerUps.displayPowerUps(c, screenWidth, screenHeight, playerMouse);
    }

    public void assignPowerUp(Mouse mouse, int mazeX, int mazeY) {

        // This array list I get does not seem to work properly. Fix when can. -Matt
        //ArrayList powerUpList = powerUps.getPowerUpList();

        for (int i = 0; i < powerUps.powerUpList.size(); i++) {
            if(powerUps.powerUpList.get(i).getMazeX() == mazeX && powerUps.powerUpList.get(i).getMazeY() == mazeY) {
                mouse.addPoints(1000);
                mouse.addPowerUp(powerUps.addPowerUpToMouse(i));
            }
        }
    }

}
