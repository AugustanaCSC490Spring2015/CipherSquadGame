package game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import maze.Maze;

/**
 * Created by Ethan Halsall on 3/30/2015.
 */
public class PowerUpMap {

    public int height;
    public int width;

    Random rand = new Random(System.currentTimeMillis());
    int randX;
    int randY;

    public ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();

    // Constructor, which adds the powerups to the powerup list
    public PowerUpMap(Maze maze, int screenWidth, int screenHeight, int width, int height, int level) {
        this.height = maze.getHeight();
        this.width = maze.getWidth();

        for (int i = 0; i < width / 2 ; i++) {
            rand = new Random(System.currentTimeMillis());
            randX = rand.nextInt(width);
            // This is just so the cheese does not start at point 0,0 with the mouse
            if (randX == 0) {
                randY = 1 + rand.nextInt(height - 1);
            } else {
                randY = rand.nextInt(height);
            }

            PowerUp powerUp = new CheesePowerUp(screenWidth,screenHeight,width,height);
            powerUp.setMazeX(randX);
            powerUp.setMazeY(randY);
            powerUpList.add(powerUp);
        }
    }

    // Method that takes in the canvas, the width/height of the screen,
    // and the mouse, in order to add the power ups collected to the mouse.
    public void displayPowerUps(Canvas c, int screenWidth, int screenHeight, Mouse mouse) {
        for (int powerUpNumber = 0; powerUpNumber < powerUpList.size(); powerUpNumber++) {
            Point powerUp = new Point(powerUpList.get(powerUpNumber).getMazeX(), powerUpList.get(powerUpNumber).getMazeY());
            Point mouseMazeLocation = mouse.getMazePos();


            c.drawBitmap(powerUpList.get(powerUpNumber).getBitmapImage(), powerUp.x * screenWidth / width, powerUp.y * screenHeight / height, null);

        }
    }

    // adds the selected powerup to the mouse array of powerups
    // as well as removes it from the array of powerups that the powerupmap object has.
    public PowerUp addPowerUpToMouse(int locationInPowerUpArray) {
        return powerUpList.remove(locationInPowerUpArray);
    }

    public ArrayList getPowerUpList() {
        return powerUpList;
    }
}
