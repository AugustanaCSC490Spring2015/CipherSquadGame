package game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import edu.augustana.csc490.gamestarter.MainGameView;
import edu.augustana.csc490.gamestarter.R;

/**
 * Created by Matt on 4/15/2015.
 */
public class CheesePowerUp extends PowerUp {
    int xLocation;
    int yLocation;
    Bitmap cheeseImage;
    CheesePowerUp(int screenWidth, int screenHeight, int width, int height) {
        // swiss_cheese taken from http://simple.wikipedia.org/wiki/Swiss_cheese under Public Domain
        // http://simple.wikipedia.org/wiki/Swiss_cheese#/media/File:NCI_swiss_cheese.jpg
        cheeseImage = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.small_cheese_swiss);
        cheeseImage = Bitmap.createScaledBitmap(cheeseImage,screenWidth / width,screenHeight/height,true);
    }


    public Bitmap getBitmapImage() {
        return cheeseImage;
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
