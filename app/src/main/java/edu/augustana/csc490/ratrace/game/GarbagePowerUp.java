package edu.augustana.csc490.ratrace.game;


import android.graphics.Bitmap;

/**
 * Created by Matt on 4/15/2015.
 * GarbagePowerUp is a particular PowerUp
 * Gets its location from PowerUpMap.
 */
public class GarbagePowerUp extends PowerUp {


    /**
     * @param image is the image to display of the GarbagePowerUp
     * @param x     is the x maze location
     * @param y     is the maze y location
     */
    GarbagePowerUp(Bitmap image, int x, int y) {
        this.image = image;
        mazeX = x;
        mazeY = y;
    }

}
