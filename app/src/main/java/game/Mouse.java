package game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

import java.util.ArrayList;

import maze.Maze;


public abstract class Mouse {

    private Bitmap mouseImage;
    private Bitmap rotatedImage;

    //default mouse data
    protected final int COLOR = Color.GRAY;
    //protected final Bitmap MOUSE_IMAGE;


    //data for mouse
    private int posX;
    private int posY;
    private Point mazePos;
    private boolean finished;
    private long totalTime;
    private Point posAtLastRotate;

    private int mousePoints;
    private int movements;
    private int numMovementsTillRotate;

    private float angle;

    private ArrayList<PowerUp> powerUpList = new ArrayList();

    public void setMouseImage(Bitmap mouseImage) {
        this.mouseImage = mouseImage;
        numMovementsTillRotate = mouseImage.getWidth() / 250 + 1;
    }

    public void setMouseAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public Bitmap getMouseImage() {
        return mouseImage;
    }

    public void initMouse() {
        angle = 45;
        numMovementsTillRotate = 1;
        movements = 0;
        totalTime = 0;
        finished = false;
        mousePoints = 0;
        posX = 0;
        posY = 0;
        mazePos = new Point(0, 0);
        posAtLastRotate = getPos();
        rotatedImage = mouseImage;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Bitmap getImage() {
        return mouseImage;
    }

    public void setMazePos(Point pos) {
        mazePos = pos;
    }

    public Point getMazePos() {
        return mazePos;
    }

    public abstract void levelUp(Maze maze);


    public boolean moveMouse(int x, int y) {
        posX = x;
        posY = y;
        movements++;
        return true;
    }

    public boolean rotate() {
        if (movements > numMovementsTillRotate) {
            posAtLastRotate.x = posX;
            posAtLastRotate.y = posY;
            movements = 0;
            return true;
        } else {
            return false;
        }
    }

    public Point getPosAtLastRotate() {
        return new Point(posAtLastRotate);
    }

    public void setRotatedImage(Bitmap image) {
        rotatedImage = image;
    }

    public Bitmap getRotatedImage() {
        return rotatedImage;
    }

    public Point getPos() {
        return new Point(posX, posY);
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUpList.add(powerUp);
    }

    public int addPoints(int points) {
        mousePoints = mousePoints + points;
        return mousePoints;
    }

    public void setTotalTime(long time) {
        totalTime = time;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }


}
