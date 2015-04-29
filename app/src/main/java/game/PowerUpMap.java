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

    public PowerUpMap(Maze maze, int screenWidth, int screenHeight, int width, int height, int level) {
        this.height = maze.getHeight();
        this.width = maze.getWidth();

        rand = new Random();


        for (int i = 0; i < level; i++) {
            randX = (int)(rand.nextDouble() * width);
            randY = (int)(rand.nextDouble() * height);
            PowerUp powerUp = new CheesePowerUp(screenWidth,screenHeight,width,height);
            powerUp.setMazeX(randX);
            powerUp.setMazeY(randY);
            powerUpList.add(powerUp);
        }
    }

    public void displayPowerUps(Canvas c, int screenWidth, int screenHeight, int mouseX, int mouseY) {
        for (int powerUpNumber = 0; powerUpNumber < powerUpList.size(); powerUpNumber++) {
            randX = powerUpList.get(powerUpNumber).getMazeX();
            randY = powerUpList.get(powerUpNumber).getMazeY();
            //TODO Prints many images, I want one location for each and for it to stay!!
            if (((mouseX <= (randX * screenWidth / width) + ((screenWidth / width)/3) && (mouseX >= (randX * screenWidth / width) - ((screenWidth / width)/3)) && (mouseY <= (randY * screenHeight / height) + ((screenHeight / height)/3) && (mouseY >= (randY * screenHeight / height) - ((screenHeight / height)/3)))))) {
                //powerUpList.set(powerUpNumber, new GarbagePowerUp());
                powerUpList.remove(powerUpNumber);
            } else {
                //randX = powerUpList.get(powerUpNumber).getMazeX();
                //randY = powerUpList.get(powerUpNumber).getMazeY();
                c.drawBitmap(powerUpList.get(powerUpNumber).getBitmapImage(), randX * screenWidth / width, randY * screenHeight / height, null);
            }
        }
    }
}
