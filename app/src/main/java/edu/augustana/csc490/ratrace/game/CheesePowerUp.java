package edu.augustana.csc490.ratrace.game;

import android.graphics.Bitmap;

/**
 * Created by Matt on 4/15/2015.
 * CheesePowerUp is a particular PowerUp
 * Gets its location from PowerUpMap
 */
public class CheesePowerUp extends PowerUp {

    CheesePowerUp(Bitmap image, int x, int y) {
        this.image = image;
        mazeX = x;
        mazeY = y;
    }

}
