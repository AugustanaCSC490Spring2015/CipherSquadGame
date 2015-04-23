package game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;


/**
 * Created by Ethan Halsall on 3/30/2015.
 */


public abstract class Mouse {

    private Bitmap mouseImage;

    public void setMouseImage(Bitmap mouseImage) {
        this.mouseImage = mouseImage;
    }


    public Bitmap getMouseImage() {
        return mouseImage;
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

    public void initMouse(Bitmap mouseImage) {
        this.mouseImage = mouseImage;
        totalTime = 0;
        finished = false;
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
