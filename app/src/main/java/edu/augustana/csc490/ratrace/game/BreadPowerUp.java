package edu.augustana.csc490.ratrace.game;

import android.graphics.Bitmap;


/**
 * Created by Matt on 4/15/2015.
 * BreadPowerUp is a particular PowerUp
 * Gets its location from PowerUpMap
 */
public class BreadPowerUp extends PowerUp {

    /**
     *
     * @param image is the bitmap for the BreadPowerUp
     * @param x is the x maze location
     * @param y is the y maze location
     */
    BreadPowerUp(Bitmap image, int x, int y) {
        this.image = image;
        mazeX = x;
        mazeY = y;
    }

}
