package edu.augustana.csc490.ratrace.game;

import android.graphics.Bitmap;

/**
 * Created by Ethan Halsall on 3/30/2015.
 */
public abstract class PowerUp {

    Bitmap image;
    int mazeX;
    int mazeY;

    public Bitmap getBitmapImage() {
        return image;
    }

    public int getMazeX() {
        return mazeX;
    }

    public int getMazeY() {
        return mazeY;
    }
}