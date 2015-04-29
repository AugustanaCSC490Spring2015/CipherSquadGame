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

    public PowerUpMap(Maze maze, int screenWidth, int screenHeight, int width, int height, int level) {
        this.height = maze.getHeight();
        this.width = maze.getWidth();

        for (int i = 0; i < level; i++) {
            rand = new Random(System.currentTimeMillis());
            randX = rand.nextInt(width);
            randY = rand.nextInt(height);
            PowerUp powerUp = new CheesePowerUp(screenWidth,screenHeight,width,height);
            powerUp.setMazeX(randX);
            powerUp.setMazeY(randY);
            powerUpList.add(powerUp);
        }
    }

    public void displayPowerUps(Canvas c, int screenWidth, int screenHeight, Mouse mouse) {
        for (int powerUpNumber = 0; powerUpNumber < powerUpList.size(); powerUpNumber++) {
            Point powerUp = new Point(powerUpList.get(powerUpNumber).getMazeX(), powerUpList.get(powerUpNumber).getMazeY());
            Point mouseMazeLocation = new Point(mouse.getPosX()/(screenWidth/width), mouse.getPosY() / (screenHeight/height));

            if ((mouseMazeLocation.x == powerUp.x) && (mouseMazeLocation.y == powerUp.y)) {
                mouse.addPoints(1000);
                powerUpList.remove(powerUpNumber);
                powerUpNumber--;
            } else {
                c.drawBitmap(powerUpList.get(powerUpNumber).getBitmapImage(), powerUp.x * screenWidth / width, powerUp.y * screenHeight / height, null);
            }
        }
    }
}
