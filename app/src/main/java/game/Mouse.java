package game;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.media.Image;


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


    public void paintMouse() {

    }

    public void initMouse(Paint p, Picture mouseImage) {
        this.mousePaint = p;
        this.mouseImage = mouseImage;
        posX = 0;
        posY = 0;
        p.setColor(Color.BLACK);
        circleTestMouse(20);
    }

    private void circleTestMouse(int size) {
        circleSize = size;

    }

    public int getCircleSize() {
        return circleSize;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Paint getMousePaint() {
        return mousePaint;
    }

    public boolean moveMouse(int x, int y) {
        posX = x;
        posY = y;
        return true;
    }

    public boolean moveMouse(Point p) {
        posX = p.x;
        posY = p.y;
        return true;
    }

}
