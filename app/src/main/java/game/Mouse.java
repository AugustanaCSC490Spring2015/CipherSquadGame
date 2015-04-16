package game;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;

import java.util.ArrayList;


/**
 * Created by Ethan Halsall on 3/30/2015.
 */


public abstract class Mouse {

    private Paint mousePaint;
    private Picture mouseImage;

    //default mouse data
    protected final int COLOR = Color.GRAY;
    protected final Picture MOUSE_IMAGE = new Picture();

    //data for the circle test mouse
    private int circleSize;


    //data for mouse
    private int posX;
    private int posY;

    private ArrayList<PowerUp> powerUpArrayList = new ArrayList<PowerUp>();


    public void paintMouse(){

    }

    public void initMouse(Paint p, Picture mouseImage){
        this.mousePaint = p;
        this.mouseImage = mouseImage;
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

    // Get the points, which is now a base number,
    // minus the amount of time it took to get to the end,
    // adding 50 points for each powerup that has been gotten.
    public int getPoints(int time, ArrayList<PowerUp> powerUps) {
        int powerUpTotal = 0;
        for (PowerUp i:powerUps){
            powerUpTotal = powerUpTotal + 50;
        }
        return (100 - time + powerUpTotal);
    }

    // TODO Matt: We will need some way for the maze to tell us when we get a powerup, and what that powerup is. A listener and type checker possibly.
    public void addPowerUp(String type) {
        if (type.equals("Cheese")) {powerUpArrayList.add(new CheesePowerUp()); }
        if (type.equals("Bread")) {powerUpArrayList.add(new BreadPowerUp()); }
        if (type.equals("Garbage")) {powerUpArrayList.add(new GarbagePowerUp()); }


    }

    public boolean moveMouse(int x, int y){
        posX = x;
        posY = y;
        return true;
    }

}
