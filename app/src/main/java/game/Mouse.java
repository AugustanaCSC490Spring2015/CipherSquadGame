package game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;


/**
 * Created by Ethan Halsall on 3/30/2015.
 */


public abstract class Mouse {

    private Paint mousePaint;
    private Bitmap mouseImage;

    public void setMouseImage(Bitmap mouseImage) {
        this.mouseImage = mouseImage;
    }

    //default mouse data
    protected final int COLOR = Color.GRAY;
    //protected final Bitmap MOUSE_IMAGE;

    //data for the circle test mouse
    private int circleSize;


    //data for mouse
    private int posX;
    private int posY;
    private boolean finished;
    private long totalTime;

    private int mousePoints = 0;
    private ArrayList<PowerUp> powerUpList = new ArrayList();


    public void paintMouse(){

    }

    public void initMouse(Paint p, Bitmap mouseImage){
        this.mousePaint = p;
        this.mouseImage = mouseImage;
        totalTime = 0;
        finished = false;
        posX = 0;
        posY = 0;
        p.setColor(Color.BLACK);
        circleTestMouse(20);
    }

    private void circleTestMouse(int size){
        circleSize = size;

    }

    public int getCircleSize(){
        return circleSize;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public Paint getMousePaint(){
        return mousePaint;
    }

    public boolean moveMouse(int x, int y){
        posX = x;
        posY = y;
        return true;
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
    }



}
