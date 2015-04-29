package game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import edu.augustana.csc490.gamestarter.MainGameView;
import edu.augustana.csc490.gamestarter.R;

/**
 * Created by Matt on 4/15/2015.
 */
public class BreadPowerUp extends PowerUp {

    int xLocation;
    int yLocation;
    Bitmap breadImage = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.cheese_icon);

    //int xLocation = PowerUpMap.getRandomMazeXLocation();

    public Bitmap getBitmapImage() {
        return breadImage;
    }

    public void setMazeX(int x) {
        xLocation = x;
    }

    public void setMazeY(int y) {
        yLocation = y;
    }




    @Override
    public int getMazeX() {

        return xLocation;
    }

    @Override
    public int getMazeY() {

        return yLocation;
    }

}
