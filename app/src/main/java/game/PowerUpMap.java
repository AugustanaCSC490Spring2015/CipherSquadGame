package game;

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

    Random rand = new Random(System.currentTimeMillis());
    int randX;
    int randY;

    public PowerUp allPowerUps[] = new PowerUp[3];


    // The list that contains all the powerups on the map
    public ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();

    // Constructor, which adds the powerups to the powerup list
    public PowerUpMap(Maze maze, int screenWidth, int screenHeight, int width, int height, int level) {
        this.height = maze.getHeight();
        this.width = maze.getWidth();

        allPowerUps[0] = new CheesePowerUp(screenWidth,screenHeight,width,height);
        allPowerUps[1] = new BreadPowerUp(screenWidth,screenHeight,width,height);
        allPowerUps[2] = new GarbagePowerUp(screenWidth,screenHeight,width,height);

        for (int i = 0; i < width / 2 ; i++) {
            rand = new Random();
            randX = rand.nextInt(width);
            // This is just so the cheese does not start at point 0,0 with the mouse
            if (randX == 0) {
                randY = 1 + rand.nextInt(height - 1);
            } else {
                randY = rand.nextInt(height);
            }

            // This adds a powerup, and each powerup is a different powerup type than the last
            PowerUp powerUp = allPowerUps[i % allPowerUps.length];
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
