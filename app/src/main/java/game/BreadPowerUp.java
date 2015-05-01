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

    // sandwich.png was found on http://www.pdclipart.org/displayimage.php?album=search&cat=0&pos=18
    // under Public Domain
    Bitmap breadImage;

    BreadPowerUp(int screenWidth, int screenHeight, int width, int height) {
        // swiss_cheese taken from http://simple.wikipedia.org/wiki/Swiss_cheese under Public Domain
        // http://simple.wikipedia.org/wiki/Swiss_cheese#/media/File:NCI_swiss_cheese.jpg
        breadImage = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.sandwich);
        breadImage = Bitmap.createScaledBitmap(breadImage,screenWidth / width,screenHeight/height,true);
    }

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
