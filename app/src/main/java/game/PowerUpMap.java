package game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import maze.*;

/**
 * Created by Ethan Halsall on 3/30/2015.
 */
public class PowerUpMap {

    public int height;
    public int width;

    Random rand;
    int randX;
    int randY;

    public ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();

    public PowerUpMap(Maze maze, int screenWidth, int screenHeight, int width, int height) {
        this.height = maze.getHeight();
        this.width = maze.getWidth();

        rand = new Random();
        randX = (int)(rand.nextDouble() * width);
        rand = new Random();
        randY = (int)(rand.nextDouble() * height);

        powerUpList.add(new CheesePowerUp(screenWidth,screenHeight,width,height));
    }

    public void displayPowerUps(Canvas c, int screenWidth, int screenHeight) {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == randX && j == randY && i != 0 && j != 0) {
                    c.drawBitmap(powerUpList.get(0).getBitmapImage(), i * screenWidth/width, j * screenHeight/height, null);
                }
            }
        }
    }
}
