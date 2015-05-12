package game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

import maze.Maze;

/**
 * Created by Ethan Halsall on 3/30/2015.
 */
public class PowerUpMap {

    public int height;
    public int width;

    Random rand;
    int randX;
    int randY;

    Bitmap[] images;

    Maze maze;


    // The list that contains all the powerups on the map
    public ArrayList<PowerUp> powerUpList;

    public final int NUM_POWER_UP_TYPES = 3;

    /**
     * Constructor, which adds the powerups to the powerup list
     * @param maze
     * @param scaledPowerUpImages
     */
    public PowerUpMap(Maze maze, Bitmap[] scaledPowerUpImages) {
        this.maze = maze;
        this.height = maze.getHeight();
        this.width = maze.getWidth();
        rand = new Random(System.currentTimeMillis());
        powerUpList = new ArrayList<PowerUp>();
        images = scaledPowerUpImages;




        /*allPowerUps[0] = new CheesePowerUp(screenWidth,screenHeight,width,height);
        allPowerUps[1] = new BreadPowerUp(screenWidth,screenHeight,width,height);
        allPowerUps[2] = new GarbagePowerUp(screenWidth,screenHeight,width,height);*/

        for (int i = 0; i < (width / 2) + 1 ; i++) {
            randX = rand.nextInt(width);
            randY = rand.nextInt(height);
            // This is just so the cheese does not start at point 0,0 with the mouse

            if (randX == 0 && randY == 0) {
                randX++;
            }
            if (randX == maze.getEnd().x && randY == maze.getEnd().y) {
                randX--;
            }

            switch (rand.nextInt(NUM_POWER_UP_TYPES)) {
                case 0:
                    powerUpList.add(new CheesePowerUp(scaledPowerUpImages[0], randX, randY));
                    break;
                case 1:
                    powerUpList.add(new BreadPowerUp(scaledPowerUpImages[1], randX, randY));
                    break;
                case 2:
                    powerUpList.add(new GarbagePowerUp(scaledPowerUpImages[2], randX, randY));
                    break;
            }

            // This adds a powerup, and each powerup is a different powerup type than the last
            /*PowerUp powerUp = allPowerUps[i % allPowerUps.length];
            powerUp.setMazeX(randX);
            powerUp.setMazeY(randY);
            powerUpList.add(powerUp);*/
        }
    }


    /**
     * Method that takes in the canvas, the width/height of the screen,
     * and the mouse, in order to add the power ups collected to the mouse.
     */
    public void displayPowerUps(Canvas c, int screenWidth, int screenHeight) {
        for (int i = 0; i < powerUpList.size(); i++) {
            c.drawBitmap(powerUpList.get(i).getBitmapImage(), powerUpList.get(i).getMazeX() * screenWidth / width, powerUpList.get(i).getMazeY() * screenHeight / height, null);
        }
    }

    /**
     * adds the selected powerup to the mouse array of powerups
     * as well as removes it from the array of powerups that the powerupmap object has.
     * @param locationInPowerUpArray
     * @return
     */
    public PowerUp addPowerUpToMouse(int locationInPowerUpArray) {
        return powerUpList.remove(locationInPowerUpArray);
    }

    public Maze getMaze() {
        return maze;
    }
}
