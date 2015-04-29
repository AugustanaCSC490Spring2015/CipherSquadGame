package game;

import android.graphics.Bitmap;

import maze.*;

/**
 * Created by Ethan Halsall on 3/30/2015.
 */
public abstract class PowerUp {

    PowerUp() {
    }

    public abstract Bitmap getBitmapImage();

    public abstract void setMazeX(int x);

    public abstract void setMazeY(int y);

    public abstract int getMazeX();

    public abstract int getMazeY();
}