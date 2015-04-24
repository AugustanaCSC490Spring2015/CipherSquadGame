package game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Ethan Halsall on 3/30/2015.
 */


public abstract class Mouse {

    private Bitmap mouseImage;

    //default mouse data
    protected final int COLOR = Color.GRAY;
    //protected final Bitmap MOUSE_IMAGE;



    //data for mouse
    private int posX;
    private int posY;
    private boolean finished;
    private long totalTime;

    private int mousePoints;
    private int movements;
    private int numMovementsTillRotate;
    private int xAtLastRotate;
    private int yAtLastRotate;
    private float changeFromInitAngle;

    private Matrix angleMatrix;

    private ArrayList<PowerUp> powerUpList = new ArrayList();

    public void setMouseImage(Bitmap mouseImage) {
        this.mouseImage = mouseImage;
        numMovementsTillRotate = mouseImage.getHeight() / 20 + 1;
    }

    public Bitmap getMouseImage() {
        return mouseImage;
    }

    public void initMouse() {
        angleMatrix = new Matrix();
        numMovementsTillRotate = 1;
        movements = 0;
        totalTime = 0;
        changeFromInitAngle = 0;
        finished = false;
        mousePoints = 0;
        posX = 0;
        posY = 0;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public Bitmap getImage() {
        return mouseImage;
    }


    public boolean moveMouse(int x, int y){
        posX = x;
        posY = y;
        movements++;
        if (movements > numMovementsTillRotate) {
            //rotateImage(); //need to fix the rotate method
            Log.i("MoveMouseRotate", "Move Mouse Rotate method called " + movements + " num till rotate " + numMovementsTillRotate);
            movements = 0;
        }
        return true;
    }

    private void rotateImage() {
        int deltaX = posX - xAtLastRotate;
        int deltaY = posY - yAtLastRotate;
        float angle = (float) Math.toDegrees(Math.atan(deltaY / deltaX) * 180 / Math.PI);
        xAtLastRotate = posX;
        yAtLastRotate = posY;
        if (angle == 0) {
            return;
        }
        changeFromInitAngle = (changeFromInitAngle + angle) % 360;
        angleMatrix.postRotate(angle);
        mouseImage = Bitmap.createBitmap(mouseImage, 0, 0, mouseImage.getWidth(), mouseImage.getHeight(), angleMatrix, true);
        angleMatrix.reset();
        return;
    }


    public void addPoints(int points) {
        mousePoints = mousePoints + points;
    }

    public void setTotalTime(long time) {
        totalTime = time;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        angleMatrix.postRotate(changeFromInitAngle);
        mouseImage = Bitmap.createBitmap(mouseImage, 0, 0, mouseImage.getWidth(), mouseImage.getHeight(), angleMatrix, true);
        angleMatrix.reset();
    }



}
