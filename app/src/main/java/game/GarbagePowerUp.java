package game;

import android.graphics.Bitmap;

/**
 * Created by Matt on 4/15/2015.
 */
public class GarbagePowerUp extends PowerUp {
    int xLocation;
    int yLocation;

    GarbagePowerUp() {

    }

    public Bitmap getBitmapImage() {
        return null;
    }
    public void setMazeX(int x) {
        xLocation = x;
    }

    public void setMazeY(int y) {
        yLocation = y;
    }




    public int getMazeX() {
        return xLocation;
    }

    public int getMazeY() {
        return yLocation;
    }

}
